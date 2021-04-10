package com.geongo.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "authors")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;

    public Author() {
    }

    public Author(String name) {
        this.name = name;
    }
}
