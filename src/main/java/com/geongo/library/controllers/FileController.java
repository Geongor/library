package com.geongo.library.controllers;

import com.geongo.library.services.AmazonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class FileController {

    private AmazonClient amazonClient;

    @Autowired
    FileController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }


    @PostMapping("/add_book")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file) {
        this.amazonClient.uploadFile(file);
        return "add_book";
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }

    @GetMapping("/add_book")
    public String addBookPage(Model model){
        return "add_book";
    }
}
