package com.testtask.noteservice.dto;

import com.testtask.noteservice.model.Like;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class NoteResponseDto {
    private String noteId;
    private String userId;
    private String content;
    private Set<Like> likes;
    private LocalDateTime timeStamp;
}
