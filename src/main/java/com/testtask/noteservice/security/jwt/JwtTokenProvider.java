package com.testtask.noteservice.security.jwt;

import com.testtask.noteservice.exception.NoteApiException;
import com.testtask.noteservice.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {
    @Value("${app.security.jwt.token.secret-key:secretByApp}")
    private String jwtSecret;
    @Value("${app.security.jwt.token.life-time-milliseconds:43200000}")
    private long validityJwtInMilliseconds;

    public String generateToken(User user) {
        String username = user.getUserName();
        String password = user.getPassword();
        Date currentDate = new Date();
        Date validityDate = new Date(currentDate.getTime() + validityJwtInMilliseconds);
        return Jwts.builder()
                .setSubject(password)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(validityDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUsernameFromJwT(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (SignatureException ex) {
            throw new NoteApiException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new NoteApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new NoteApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new NoteApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new NoteApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
    }

    public String getUserNameFromRequest(HttpServletRequest request) {
        if (resolveToken(request) != null & validateToken(resolveToken(request))) {
            return getUsernameFromJwT(resolveToken(request));
        }
        return null;
    }
}
