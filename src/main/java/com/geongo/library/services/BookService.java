package com.geongo.library.services;

import com.geongo.library.entity.Book;
import com.geongo.library.repos.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service()
public class BookService {

    private AmazonClient amazonClient;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookService(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    public boolean saveBook(Book book, MultipartFile file, MultipartFile image){


        book.setFilePath(this.amazonClient.uploadFile(file));
        book.setImagePath(this.amazonClient.uploadFile(image));
        bookRepository.save(book);

        return true;
    }
}
