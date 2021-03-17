package com.geongo.library.controllers;

import com.geongo.library.entity.Book;
import com.geongo.library.entity.Genre;
import com.geongo.library.services.AmazonClient;
import com.geongo.library.services.AuthorService;
import com.geongo.library.services.BookService;
import com.geongo.library.services.GenreService;
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

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Validated
@Controller
public class FileController {

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

        return addBookPage(model);
    }

    @GetMapping("/book/add")
    public String addBookPage(Model model){

        model.addAttribute("authors", authorService.getAuthors());
        model.addAttribute("genres", genreService.getGenres());

        return "add_book";
    }

    @GetMapping("/book")
    public String downloadBookPage(Model model,
                                   @ModelAttribute Book filter,
                                   @PageableDefault(size = 1) Pageable pageable) throws IOException {

        if (filter == null) filter = new Book();

        Page<Book> books = bookService.getAllBooksByFilter(filter, pageable);

        for (Book book:books) {

            book.setImage(amazonClient.getFIle(".png", amazonClient.getBucketName(), book.getImagePath()));
            book.setFile(amazonClient.getFIle(".doc", amazonClient.getBucketName(), book.getFilePath()));
        }

        model.addAttribute("authors", authorService.getAuthors());
        model.addAttribute("genres", genreService.getGenres());
        model.addAttribute("books", books);

        return "library";
    }

    @DeleteMapping("/deleteFile")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}
