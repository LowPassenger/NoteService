package com.testtask.noteservice.controller;

import com.testtask.noteservice.dto.NoteRequestDto;
import com.testtask.noteservice.dto.NoteResponseDto;
import com.testtask.noteservice.model.Role;
import com.testtask.noteservice.model.User;
import com.testtask.noteservice.security.jwt.JwtTokenProvider;
import com.testtask.noteservice.service.NoteService;
import com.testtask.noteservice.service.UserService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;
    private final JwtTokenProvider provider;

    @Autowired
    public NoteController(NoteService noteService, UserService userService,
                          JwtTokenProvider provider) {
        this.noteService = noteService;
        this.userService = userService;
        this.provider = provider;
    }

    @PostMapping
    public ResponseEntity<NoteResponseDto> create(
            @Valid @RequestBody NoteRequestDto noteRequestDto) {
        return new ResponseEntity<>(noteService.create(noteRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("all")
    ResponseEntity<List<NoteResponseDto>> getAll() {
        return ResponseEntity.ok(noteService.findAll(null));
    }

    @GetMapping("all/{id}")
    ResponseEntity<List<NoteResponseDto>> getAll(@PathVariable(name = "id") String userId) {
        return ResponseEntity.ok(noteService.findAll(userId));
    }

    @GetMapping(value = "/{id}")
    ResponseEntity<NoteResponseDto> getById(@PathVariable(name = "id") String noteId) {
        return ResponseEntity.ok(noteService.getBuId(noteId));
    }

    @PutMapping("/{id}")
    ResponseEntity<?> update(@PathVariable(name = "id") String id,
                                           @Valid @RequestBody NoteRequestDto noteRequestDto,
                                           HttpServletRequest request) {
        if (validateUser(request, id)) {
            return new ResponseEntity<String>("Current user has no permission!",
                    HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(noteService.update(id, noteRequestDto), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") String id,
                                         HttpServletRequest request) {
        if (validateUser(request, id)) {
            return new ResponseEntity<String>("Current user has no permission!",
                    HttpStatus.FORBIDDEN);
        }
        if (noteService.delete(id)) {
            return new ResponseEntity<>("Note was deleted!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Note wasn't deleted!", HttpStatus.NO_CONTENT);
    }

    private boolean validateUser(HttpServletRequest request, String noteId) {
        String currentUserName = provider.getUserNameFromRequest(request);
        if (currentUserName == null) {
            return true;
        }
        User currentUser = userService.getByName(currentUserName);
        if (currentUser.getRoles().contains(Role.ADMIN)) {
            return false;
        }
        return !noteService.getBuId(noteId).getUserId().equals(currentUser.getId());
    }
}
