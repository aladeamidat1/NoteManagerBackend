package org.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateNoteRequest {
    private String title;
    private String content;
    private String userId;
    private String noteId;
}
