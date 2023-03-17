package com.testtask.noteservice.service.impl;

import com.testtask.noteservice.dto.NoteRequestDto;
import com.testtask.noteservice.dto.NoteResponseDto;
import com.testtask.noteservice.exception.ResourceNotFoundException;
import com.testtask.noteservice.mapper.impl.NoteMapper;
import com.testtask.noteservice.model.Like;
import com.testtask.noteservice.model.Note;
import com.testtask.noteservice.repository.NoteRepository;
import com.testtask.noteservice.service.LikeService;
import com.testtask.noteservice.service.NoteService;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class LikeServiceImpl implements LikeService {
    private final NoteRepository noteRepository;
    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @Autowired
    public LikeServiceImpl(NoteRepository noteRepository,
                           NoteService noteService,
                           NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteService = noteService;
        this.noteMapper = noteMapper;
    }

    @Override
    public NoteResponseDto createLike(String noteId, String userLikeId) {
        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new ResourceNotFoundException("Note", "id", noteId)
        );
        Like like = new Like(userLikeId);
        like.setTimeStamp(LocalDateTime.now());
        Set<Like> likes = (note.getLikes() == null) ? new HashSet<>() : note.getLikes();
        likes.add(like);
        NoteRequestDto requestDto = new NoteRequestDto();
        requestDto.setUserId(note.getUserId());
        requestDto.setContent(note.getContent());
        requestDto.setLikes(likes);
        log.info("Like has been created in note {} by user {}", note, userLikeId);
        return noteService.update(noteId, requestDto);
    }

    @Override
    public NoteResponseDto deleteLike(String noteId, String userLikeId) {
        Note note = noteRepository.findById(noteId).orElseThrow(
                () -> new ResourceNotFoundException("Note", "id", noteId)
        );
        NoteRequestDto requestDto = new NoteRequestDto();
        requestDto.setUserId(note.getUserId());
        requestDto.setContent(note.getContent());
        if (note.getLikes() != null || !note.getLikes().isEmpty()) {
            Set<Like> likes = note.getLikes().stream()
                    .dropWhile(like -> userLikeId.equals(like.getUserLikeId()))
                    .collect(Collectors.toSet());
            requestDto.setLikes(likes);
        }
        log.info("Like has been deleted in note {} by user {}", note, userLikeId);
        return noteService.update(noteId, requestDto);
    }
}
