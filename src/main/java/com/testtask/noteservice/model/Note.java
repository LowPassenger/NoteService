package com.testtask.noteservice.model;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "noteService")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    private String noteId;
    private String userId;
    private String content;
    private Set<Like> likes;
    private LocalDateTime timestamp;
}
