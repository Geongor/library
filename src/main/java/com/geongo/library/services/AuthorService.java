package com.geongo.library.services;

import com.geongo.library.entity.Author;
import com.geongo.library.repos.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service()
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    public boolean saveAuthor(Author author){
        authorRepository.save(author);
        return true;
    }

    public List<Author> getAuthors(){
        return authorRepository.findAll();
    }

    public Author getAuthorByName(String name){
        return authorRepository.findAllByName(name);
    }
}
