package org.task.maid.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.maid.dto.BookDto;
import org.task.maid.dto.pagination.PaginationDto;
import org.task.maid.service.BookService;

import java.net.URI;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping
    public ResponseEntity<PaginationDto<BookDto>> getAllBooks(@RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "size", defaultValue = "20") Integer size) {
        PaginationDto<BookDto> paginationDto = bookService.getAllBooksPaged(page, size);
        return ResponseEntity.ok(paginationDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable("id") Integer id) {
        BookDto bookDto = bookService.getBookDtoById(id);
        return ResponseEntity.ok(bookDto);
    }

    @PostMapping
    public ResponseEntity<BookDto> addBook(@Valid @RequestBody BookDto bookDto) {
        BookDto savedBook = bookService.addBook(bookDto);
        return ResponseEntity.created(URI.create("/books/" + savedBook.getId())).body(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable("id") Integer id, @RequestBody BookDto bookDto) {
        bookDto = bookService.updateBook(id, bookDto);
        return ResponseEntity.ok(bookDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Integer id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
