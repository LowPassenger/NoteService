package com.testtask.noteservice.service;

import com.testtask.noteservice.dto.NoteResponseDto;

public interface LikeService {
    NoteResponseDto createLike(String noteId, String userLikeId);

    NoteResponseDto deleteLike(String noteId, String userLikeId);
}
