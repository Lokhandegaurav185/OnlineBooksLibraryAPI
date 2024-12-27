package com.code.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.code.model.Book;
import com.code.repository.BookRepository;
@Service
public class BookService {
	@Autowired
    private BookRepository bookRepository;

    public Optional<Book> getBookByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public Book saveBook(Book book) {
       return bookRepository.save(book);
    }
}
