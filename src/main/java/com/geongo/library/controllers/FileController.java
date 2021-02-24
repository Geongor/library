package com.geongo.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileController {

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/add_book")
    public String addBook(Model model, @RequestParam("file")MultipartFile file) throws IOException {

        if (file != null){

            File dir = new File(uploadPath);
            if (!dir.exists()){
                dir.mkdir();
            }
            String fileUUID = UUID.randomUUID().toString();
            String fileName = fileUUID + "_" + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + fileName));

        }

        return "index";
    }

    @GetMapping("/add_book")
    public String addBookPage(Model model){
        return "add_book";
    }
}
