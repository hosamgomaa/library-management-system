package org.task.maid.service;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.task.maid.dto.BookDto;
import org.task.maid.dto.pagination.PaginationDetails;
import org.task.maid.dto.pagination.PaginationDto;
import org.task.maid.entity.Book;
import org.task.maid.exception.SystemErrorMessages;
import org.task.maid.exception.SystemException;
import org.task.maid.mapper.BookMapper;
import org.task.maid.mapper.PaginationMapper;
import org.task.maid.repository.BookRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;
    private BookMapper bookMapper;
    private PaginationMapper paginationMapper;

    @Cacheable(value = "books", key = "'getAllBooksPaged' + #page + '-' + #size")
    public PaginationDto<BookDto> getAllBooksPaged(Integer page, Integer size) {
        Page<Book> booksPage = bookRepository.findAll(PageRequest.of(page, size));
        PaginationDetails paginationDetails = new PaginationDetails(booksPage.getNumber(), booksPage.getTotalElements(), booksPage.getSize(), booksPage.getTotalPages());
        List<BookDto> bookDtoList = bookMapper.toDto(booksPage.getContent());
        return paginationMapper.toPaginationDto(bookDtoList, paginationDetails);
    }

    @Cacheable(value = "books", key = "#id")
    public BookDto getBookDtoById(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new SystemException(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage()));
        return bookMapper.toDto(book);
    }

    @CacheEvict(value = "books", allEntries = true)
    public BookDto addBook(BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        book = bookRepository.save(book);
        return bookMapper.toDto(book);

    }

    @CachePut(value = "books", key = "#id")
    public BookDto updateBook(Integer id, BookDto bookDto) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new SystemException(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage()));
        book = bookMapper.updateEntityFromDto(bookDto, book);
        book = bookRepository.save(book);
        bookDto = bookMapper.toDto(book);
        return bookDto;
    }

    @CacheEvict(value = "books", allEntries = true)
    public void deleteBook(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new SystemException(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage()));
        try {
            bookRepository.delete(book);
        } catch (Exception e) {
            throw new SystemException(SystemErrorMessages.RESOURCE_IS_RELATED.getMessage());
        }
    }


}
