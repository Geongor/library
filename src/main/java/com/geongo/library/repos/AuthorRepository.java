package com.geongo.library.repos;

import com.geongo.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findAllByName(String name);
}
