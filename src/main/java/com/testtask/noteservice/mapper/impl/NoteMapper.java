package com.testtask.noteservice.mapper.impl;

import com.testtask.noteservice.dto.NoteRequestDto;
import com.testtask.noteservice.dto.NoteResponseDto;
import com.testtask.noteservice.mapper.GenericMapper;
import com.testtask.noteservice.model.Note;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper implements GenericMapper<NoteResponseDto, Note, NoteRequestDto> {

    @Override
    public NoteResponseDto toDto(Note note) {
        NoteResponseDto noteResponseDto = new NoteResponseDto();
        noteResponseDto.setNoteId(note.getNoteId());
        noteResponseDto.setUserId(note.getUserId());
        noteResponseDto.setContent(note.getContent());
        noteResponseDto.setLikes(note.getLikes());
        noteResponseDto.setTimeStamp(note.getTimestamp());
        return noteResponseDto;
    }

    @Override
    public Note toModel(NoteRequestDto noteRequestDto) {
        Note note = new Note();
        if (noteRequestDto.getUserId() != null) {
            note.setUserId(noteRequestDto.getUserId());
        }
        note.setContent(noteRequestDto.getContent());
        if (noteRequestDto.getLikes() != null) {
            note.setLikes(noteRequestDto.getLikes());
        }
        note.setTimestamp(LocalDateTime.now());
        return note;
    }
}
