package org.example.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddNoteRequest {

    private String title;
    private String content;
    private String userId;
}
