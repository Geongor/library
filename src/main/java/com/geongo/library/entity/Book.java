package com.geongo.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.List;

@Entity
@Table(name = "books")
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Book {

    @NotBlank
    private String name;
    @Id
    private String filePath;
    private String imagePath;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @NotNull
    private Author author;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Genre> genres;
    @Transient
    private File image;
    @Transient
    private File file;

    public Book() {
    }

    @Autowired
    public Book(String name, Author author, List<Genre> genres) {
        this.name = name;
        this.author = author;
        this.genres = genres;
    }
}
