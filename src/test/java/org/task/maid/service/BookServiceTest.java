package org.task.maid.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private PaginationMapper paginationMapper;

    @InjectMocks
    private BookService bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooksPaged() {
        int page = 0;
        int size = 10;

        List<Book> books = List.of(new Book(), new Book());
        Page<Book> bookPage = new PageImpl<>(books, PageRequest.of(page, size), books.size());

        List<BookDto> bookDtoList = List.of(new BookDto(), new BookDto());
        PaginationDetails paginationDetails = new PaginationDetails(page, books.size(), size, 1);
        PaginationDto<BookDto> expectedPaginationDto = new PaginationDto<>(bookDtoList, paginationDetails);

        when(bookRepository.findAll(any(Pageable.class))).thenReturn(bookPage);
        when(bookMapper.toDto(anyList())).thenReturn(bookDtoList);
        when(paginationMapper.toPaginationDto(ArgumentMatchers.<BookDto>anyList(), any(PaginationDetails.class))).thenReturn(expectedPaginationDto);

        PaginationDto<BookDto> result = bookService.getAllBooksPaged(page, size);

        assertEquals(expectedPaginationDto, result);
        verify(bookRepository).findAll(any(Pageable.class));
        verify(bookMapper).toDto(anyList());
        verify(paginationMapper).toPaginationDto(anyList(), any(PaginationDetails.class));
    }

    @Test
    void testGetBookDtoById() {
        int bookId = 1;
        Book book = new Book();
        BookDto bookDto = new BookDto();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.getBookDtoById(bookId);

        assertEquals(bookDto, result);
        verify(bookRepository).findById(bookId);
        verify(bookMapper).toDto(book);
    }

    @Test
    void testGetBookDtoById_NotFound() {
        int bookId = 1;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SystemException.class, () -> bookService.getBookDtoById(bookId));
        assertEquals(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());
        verify(bookRepository).findById(bookId);
    }

    @Test
    void testAddBook() {
        BookDto bookDto = new BookDto();
        Book book = new Book();

        when(bookMapper.toEntity(bookDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.addBook(bookDto);

        assertEquals(bookDto, result);
        verify(bookMapper).toEntity(bookDto);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void testUpdateBook() {
        int bookId = 1;
        BookDto bookDto = new BookDto();
        Book book = new Book();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.updateEntityFromDto(bookDto, book)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(bookDto);

        BookDto result = bookService.updateBook(bookId, bookDto);

        assertEquals(bookDto, result);
        verify(bookRepository).findById(bookId);
        verify(bookMapper).updateEntityFromDto(bookDto, book);
        verify(bookRepository).save(book);
        verify(bookMapper).toDto(book);
    }

    @Test
    void testUpdateBook_NotFound() {
        int bookId = 1;
        BookDto bookDto = new BookDto();

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SystemException.class, () -> bookService.updateBook(bookId, bookDto));
        assertEquals(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());
        verify(bookRepository).findById(bookId);
    }

    @Test
    void testDeleteBook() {
        int bookId = 1;
        Book book = new Book();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        bookService.deleteBook(bookId);

        verify(bookRepository).findById(bookId);
        verify(bookRepository).delete(book);
    }

    @Test
    void testDeleteBook_NotFound() {
        int bookId = 1;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SystemException.class, () -> bookService.deleteBook(bookId));
        assertEquals(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());
        verify(bookRepository).findById(bookId);
    }

    @Test
    void testDeleteBook_ExceptionOnDelete() {
        int bookId = 1;
        Book book = new Book();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        doThrow(new RuntimeException()).when(bookRepository).delete(book);

        Exception exception = assertThrows(SystemException.class, () -> bookService.deleteBook(bookId));
        assertEquals(SystemErrorMessages.RESOURCE_IS_RELATED.getMessage(), exception.getMessage());
        verify(bookRepository).findById(bookId);
        verify(bookRepository).delete(book);
    }





}
