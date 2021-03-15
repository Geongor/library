package com.geongo.library.controllers;

import com.geongo.library.entity.Author;
import com.geongo.library.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;

@Controller
@Validated
public class AuthorController {

    @Autowired
    AuthorService authorService;

    @GetMapping("/add_author")
    public String addAuthorPage(Model model){
        return "add_author";
    }

    @PostMapping("/add_author")
    public String addAuthor(Model model, @RequestParam(value = "name") @NotBlank String name){
        authorService.saveAuthor(new Author(name));
        return "add_author";
    }
}
