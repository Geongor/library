package com.geongo.library.services;

import com.geongo.library.entity.Book;
import com.geongo.library.entity.Genre;
import com.geongo.library.repos.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

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

    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    public List<Book> getAllBooksByFilter(Book filter){
        return bookRepository.findAll(matchesFilter(filter));
    }

    public static Specification<Book> matchesFilter(Book filter){
        return new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> book, CriteriaQuery<?> cq, CriteriaBuilder cb) {

                Predicate finalPredicate = cb.disjunction();
                finalPredicate = finalPredicate.not();

                if (filter.getAuthor() != null){
                    finalPredicate = cb.and(finalPredicate, cb.equal(book.get("author"), filter.getAuthor()));
                }

                if (filter.getName() != null && filter.getName() != ""){
                    finalPredicate = cb.and(finalPredicate, cb.like(book.get("name"), "%" + filter.getName() + "%"));
                }

                /*
                  if (filter.getGenres() != null && !filter.getGenres().isEmpty()){
                    finalPredicate = cb.isTrue(book.get("genres").in(filter.getGenres()));
                  }
                  */

                return finalPredicate;
            }
        };
    }
}
