package com.testtask.noteservice.controller;

import com.testtask.noteservice.dto.NoteResponseDto;
import com.testtask.noteservice.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("notes/likes")
public class LikeController {
    private final LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping("/like")
    public ResponseEntity<NoteResponseDto> like(@RequestParam(name = "noteid") String noteId,
                                                @RequestParam(name = "userid") String userId) {
        return new ResponseEntity<>(likeService.createLike(noteId, userId), HttpStatus.CREATED);
    }

    @GetMapping("/unlike")
    public ResponseEntity<NoteResponseDto> unlike(@RequestParam(name = "noteid") String noteId,
                                                  @RequestParam(name = "userid") String userId) {
        return new ResponseEntity<>(likeService.deleteLike(noteId, userId), HttpStatus.ACCEPTED);
    }

}
