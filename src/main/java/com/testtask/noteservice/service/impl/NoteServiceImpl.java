package com.testtask.noteservice.service.impl;

import com.testtask.noteservice.dto.NoteRequestDto;
import com.testtask.noteservice.dto.NoteResponseDto;
import com.testtask.noteservice.exception.ResourceNotFoundException;
import com.testtask.noteservice.mapper.impl.NoteMapper;
import com.testtask.noteservice.model.Note;
import com.testtask.noteservice.repository.NoteRepository;
import com.testtask.noteservice.service.NoteService;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class NoteServiceImpl implements NoteService {
    private static final String DEFAULT_SORT_BY = "timeStamp";
    private static final String DEFAULT_SORT_DIRECTION = "asc";
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    @Autowired
    public NoteServiceImpl(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    @Override
    public NoteResponseDto create(NoteRequestDto noteRequestDto) {
        return noteMapper.toDto(noteRepository.save(noteMapper.toModel(noteRequestDto)));
    }

    @Override
    public NoteResponseDto getBuId(String noteId) {
        return noteMapper.toDto(noteRepository.findById(noteId).orElseThrow(
                () -> new ResourceNotFoundException("Note", "id", noteId)
        ));
    }

    @Override
    public NoteResponseDto update(String noteId, NoteRequestDto noteRequestDto) {
        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new ResourceNotFoundException("Note", "id", noteId)
        );
        note.setUserId(noteRequestDto.getUserId());
        note.setContent(noteRequestDto.getContent());
        note.setLikes(noteRequestDto.getLikes());
        Note updatedNote = noteRepository.save(note);
        log.info("Note {} update to new note {} successfully", note, updatedNote);
        return noteMapper.toDto(updatedNote);
    }

    @Override
    public boolean delete(String noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new ResourceNotFoundException("Note", "id", noteId)
        );
        noteRepository.delete(note);
        if (!noteRepository.existsById(noteId)) {
            log.info("Note {} successfully deleted", note);
            return true;
        }
        return false;
    }

    @Override
    public List<NoteResponseDto> findAll(String userId) {
        List<Note> sortedNotes = noteRepository.findAllByUserIdOrderByTimestampDesc(userId);
        return sortedNotes.stream().map(noteMapper::toDto).toList();
    }
}
