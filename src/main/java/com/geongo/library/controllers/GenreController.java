package com.geongo.library.controllers;

import com.geongo.library.entity.Genre;
import com.geongo.library.services.GenreService;
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
public class GenreController {
    @Autowired
    GenreService genreService;

    @GetMapping("/add_genre")
    public String addGenrePage(Model model){
        return "add_genre";
    }

    @PostMapping("/add_genre")
    public String addGenre(Model model,
                           @RequestParam(value = "name") @NotBlank String name,
                           @RequestParam(value = "description") String description){
        genreService.saveGenre(new Genre(name, description));
        return "add_genre";
    }
}
