package com.testtask.noteservice.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;

@Data
public class Like {
    @NonNull
    private String userLikeId;
    private LocalDateTime timeStamp;
}
