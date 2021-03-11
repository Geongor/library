package com.geongo.library.services;

import com.geongo.library.entity.Genre;
import com.geongo.library.repos.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class GenreService {

    @Autowired
    GenreRepository genreRepository;

    public boolean saveGenre(Genre genre){
        genreRepository.save(genre);
        return true;
    }

    public List<Genre> getGenres(){
        return genreRepository.findAll();
    }
}
