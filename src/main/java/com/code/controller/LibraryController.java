package com.code.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.code.model.Book;
import com.code.model.Magazine;
import com.code.model.OrderRequest;
import com.code.model.ReturnRequest;
import com.code.model.User;
import com.code.service.BookService;
import com.code.service.MagazineService;
import com.code.service.UserService;
@RestController
@RequestMapping("/api")
public class LibraryController {
	@Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private MagazineService magazineService;

    @PostMapping("/order")
    public ResponseEntity<?> orderItem(@RequestBody OrderRequest request) {
        Optional<User> optionalUser = userService.getUserById(request.getUserId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found.");
        }
        User user = optionalUser.get();

        if (user.getTransactions () >= 10) {
            return ResponseEntity.badRequest().body("Transaction limit reached for the month.");
        }

        if ("book".equalsIgnoreCase(request.getItemType())) {
            Optional<Book> book = bookService.getBookByTitle(request.getTitle());
            if (book.isEmpty() || !book.get().isAvailable()) {
                return ResponseEntity.status(404).body("Book not found or not available.");
            }

            if ("Crime".equalsIgnoreCase(book.get().getGenre()) && user.getAge() < 18) {
                return ResponseEntity.status(403).body("Crime genre books are restricted to users 18+.");
            }

            if (user.getBorrowedBooks().size() >= userService.getPlanLimits(user.getPlan()).getBooks()) {
                return ResponseEntity.badRequest().body("Book borrowing limit reached for your plan.");
            }

            user.getBorrowedBooks().add(book.get());
            book.get().setAvailable(false);
            bookService.saveBook(book.get());
        } else if ("magazine".equalsIgnoreCase(request.getItemType())) {
            Optional<Magazine> magazine = magazineService.getMagazineByTitle(request.getTitle());
            if (magazine.isEmpty() || !magazine.get().isAvailable()) {
                return ResponseEntity.status(404).body("Magazine not found or not available.");
            }

            if (user.getBorrowedMagazines().size() >= userService.getPlanLimits(user.getPlan()).getMagazines()) {
                return ResponseEntity.badRequest().body("Magazine borrowing limit reached for your plan.");
            }

            user.getBorrowedMagazines().add(magazine.get());
            magazine.get().setAvailable(false);
            magazineService.saveMagazine(magazine.get());
        } else {
            return ResponseEntity.badRequest().body("Invalid item type.");
        }

        user.setTransactions(user.getTransactions() + 1);
        userService.saveUser(user);
        return ResponseEntity.ok("Item borrowed successfully.");
    }

    @PostMapping("/return")
    public ResponseEntity<?> returnItems(@RequestBody ReturnRequest request) {
        Optional<User> optionalUser = userService.getUserById(request.getUserId());
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(404).body("User not found.");
        }
        User user = optionalUser.get();

        for (String title : request.getTitles()) {
            Optional<Book> book = bookService.getBookByTitle(title);
            if (book.isPresent() && user.getBorrowedBooks().remove(book.get())) {
                book.get().setAvailable(true);
                bookService.saveBook(book.get());
                continue;
            }

            Optional<Magazine> magazine = magazineService.getMagazineByTitle(title);
            if (magazine.isPresent() && user.getBorrowedMagazines().remove(magazine.get())) {
                magazine.get().setAvailable(true);
                magazineService.saveMagazine(magazine.get());
            }
        }

        userService.saveUser(user);
        return ResponseEntity.ok("Items returned successfully.");
    }
    @PostMapping("/users")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return ResponseEntity.ok(savedBook);
    }

    @PostMapping("/magazines")
    public ResponseEntity<Magazine> createMagazine(@RequestBody Magazine magazine) {
        Magazine savedMagazine = magazineService.saveMagazine(magazine);
        return ResponseEntity.ok(savedMagazine);
    }
}
