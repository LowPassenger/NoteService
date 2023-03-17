package com.testtask.noteservice.service;

import com.testtask.noteservice.dto.NoteRequestDto;
import com.testtask.noteservice.dto.NoteResponseDto;
import java.util.List;

public interface NoteService {
    NoteResponseDto create(NoteRequestDto noteRequestDto);

    NoteResponseDto getBuId(String noteId);

    NoteResponseDto update(String noteId, NoteRequestDto noteRequestDto);

    boolean delete(String noteId);

    List<NoteResponseDto> findAll(String userId);
}
