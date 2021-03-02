package com.geongo.library.controllers;

import com.geongo.library.entity.Book;
import com.geongo.library.services.AmazonClient;
import com.geongo.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
public class FileController {

    private AmazonClient amazonClient;

    @Autowired
    BookService bookService;

    @Autowired
    FileController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }



    @PostMapping("/add_book")
    public String uploadFile(@RequestPart(value = "file") MultipartFile file,
                             @RequestPart(value = "image") MultipartFile image,
                             @RequestParam(value = "name") String name,
                             @RequestParam(value = "author") String author) {

        Book book = new Book(name, author);
        bookService.saveBook(book, file, image);

        //this.amazonClient.uploadFile(file);
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

    @GetMapping("/download")
    public String downloadBookPage(Model model) throws IOException {

        List<Book> books = bookService.getAllBooks();

        for (Book book:books) {
            book.setImage(amazonClient.getFIle(".png", amazonClient.getBucketName(), book.getImagePath()));
            book.setFile(amazonClient.getFIle(".doc", amazonClient.getBucketName(), book.getFilePath()));
        }

        model.addAttribute("books", books);

        return "library";
    }
}
