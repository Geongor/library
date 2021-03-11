package com.geongo.library.entity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.File;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    private String name;
    @Id
    private String filePath;
    private String imagePath;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }
}
