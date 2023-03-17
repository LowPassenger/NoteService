package com.testtask.noteservice.dto;

import com.testtask.noteservice.model.Like;
import java.util.Set;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class NoteRequestDto {
    private String userId;
    @Size(min = 1, max = 140, message = "Message should not be empty."
            + " Maximum message size is 140 characters")
    private String content;
    private Set<Like> likes;
}
