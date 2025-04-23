package com.cryxie.models;

import java.time.LocalDateTime;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "movies")
public class Movie {
    private String id;
    private String nome;
    private String description;
    private String cover;
    private int totalVotos;
    private LocalDateTime creationDt;
}
