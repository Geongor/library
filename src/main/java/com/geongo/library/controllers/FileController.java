package com.geongo.library.controllers;

import com.geongo.library.entity.Book;
import com.geongo.library.entity.Genre;
import com.geongo.library.services.AmazonClient;
import com.geongo.library.services.AuthorService;
import com.geongo.library.services.BookService;
import com.geongo.library.services.GenreService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@Slf4j
public class FileController {

    //private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    private final AmazonClient amazonClient;

    @Autowired
    BookService bookService;
    @Autowired
    AuthorService authorService;
    @Autowired
    GenreService genreService;

    @Autowired
    FileController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }



    @PostMapping("/book/add")
    public String uploadFile(@RequestPart(value = "Mfile") @NotNull MultipartFile Mfile,
                             @RequestPart(value = "Mimage") @NotNull MultipartFile Mimage,
                             @Valid @ModelAttribute Book book,
                             @RequestParam(value = "genres") @NotNull List<Genre> genres,
                             Model model) {

        book.setGenres(genres);

        bookService.saveBook(book, Mfile, Mimage);

        return "addBookPage(model)";
    }

    @GetMapping("/book/add")
    public Map<String, Object> addBookPage(Model model){

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("authors", authorService.getAuthors());
        resultMap.put("genres", genreService.getGenres());

        return resultMap;
    }

    @GetMapping("/book")
    public Map<String, Object> downloadBookPage(Model model,
                                @ModelAttribute Book filter,
                                @PageableDefault(size = 1) Pageable pageable) throws IOException {

        if (filter == null) filter = new Book();

        Page<Book> books = bookService.getAllBooksByFilter(filter, pageable);

        for (Book book:books) {

            book.setImage(amazonClient.getFIle(".png", amazonClient.getBucketName(), book.getImagePath()));
            book.setFile(amazonClient.getFIle(".doc", amazonClient.getBucketName(), book.getFilePath()));
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("authors", authorService.getAuthors());
        resultMap.put("genres", genreService.getGenres());
        resultMap.put("books", books);

        log.error("test msg");

        return resultMap;
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}
