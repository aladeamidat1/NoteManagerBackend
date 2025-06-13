package org.example.data.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document
@Getter
@Setter
public class User {
    @Id
    private String id;
    private String name;
    private String username;
    private String password;
    @DBRef
    private List<Note> notes = new ArrayList<>();
}
