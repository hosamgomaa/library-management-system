package org.task.maid.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.task.maid.dto.BorrowingRecordDto;
import org.task.maid.entity.Book;
import org.task.maid.entity.BorrowingRecord;
import org.task.maid.entity.Patron;
import org.task.maid.exception.SystemErrorMessages;
import org.task.maid.exception.SystemException;
import org.task.maid.mapper.BorrowingRecordMapper;
import org.task.maid.repository.BookRepository;
import org.task.maid.repository.BorrowingRecordRepository;
import org.task.maid.repository.PatronRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class BorrowingRecordServiceTest {

    @Mock
    private BorrowingRecordRepository recordRepository;

    @Mock
    private PatronRepository patronRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BorrowingRecordMapper borrowingRecordMapper;

    @InjectMocks
    private BorrowingRecordService borrowingRecordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBorrowBook_Success() {
        Integer bookId = 1;
        Integer patronId = 1;
        BorrowingRecordDto borrowingRecordDto = new BorrowingRecordDto();
        borrowingRecordDto.setReturnDate(LocalDate.now().plusDays(7));
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        Patron patron = new Patron();
        Book book = new Book();

        when(recordRepository.existsByBookIdAndNotReturned(bookId)).thenReturn(false);
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(patron));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(recordRepository.save(any(BorrowingRecord.class))).thenReturn(borrowingRecord);
        when(borrowingRecordMapper.toDto(borrowingRecord)).thenReturn(borrowingRecordDto);

        BorrowingRecordDto result = borrowingRecordService.borrowBook(bookId, patronId, borrowingRecordDto);

        assertEquals(borrowingRecordDto, result);
        verify(recordRepository).existsByBookIdAndNotReturned(bookId);
        verify(patronRepository).findById(patronId);
        verify(bookRepository).findById(bookId);
        verify(recordRepository).save(any(BorrowingRecord.class));
        verify(borrowingRecordMapper).toDto(borrowingRecord);
    }

    @Test
    void testBorrowBook_BookAlreadyBorrowed() {
        Integer bookId = 1;
        Integer patronId = 1;
        BorrowingRecordDto borrowingRecordDto = new BorrowingRecordDto();

        when(recordRepository.existsByBookIdAndNotReturned(bookId)).thenReturn(true);

        Exception exception = assertThrows(SystemException.class, () -> borrowingRecordService.borrowBook(bookId, patronId, borrowingRecordDto));
        assertEquals(SystemErrorMessages.BOOK_ALREADY_BORROWED.getMessage(), exception.getMessage());

        verify(recordRepository).existsByBookIdAndNotReturned(bookId);
        verifyNoMoreInteractions(patronRepository, bookRepository, recordRepository, borrowingRecordMapper);
    }

    @Test
    void testBorrowBook_PatronNotFound() {
        Integer bookId = 1;
        Integer patronId = 1;
        BorrowingRecordDto borrowingRecordDto = new BorrowingRecordDto();

        when(recordRepository.existsByBookIdAndNotReturned(bookId)).thenReturn(false);
        when(patronRepository.findById(patronId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SystemException.class, () -> borrowingRecordService.borrowBook(bookId, patronId, borrowingRecordDto));
        assertEquals(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());

        verify(recordRepository).existsByBookIdAndNotReturned(bookId);
        verify(patronRepository).findById(patronId);
        verifyNoMoreInteractions(bookRepository, recordRepository, borrowingRecordMapper);
    }

    @Test
    void testBorrowBook_BookNotFound() {
        Integer bookId = 1;
        Integer patronId = 1;
        BorrowingRecordDto borrowingRecordDto = new BorrowingRecordDto();

        when(recordRepository.existsByBookIdAndNotReturned(bookId)).thenReturn(false);
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(new Patron()));
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SystemException.class, () -> borrowingRecordService.borrowBook(bookId, patronId, borrowingRecordDto));
        assertEquals(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());

        verify(recordRepository).existsByBookIdAndNotReturned(bookId);
        verify(patronRepository).findById(patronId);
        verify(bookRepository).findById(bookId);
        verifyNoMoreInteractions(recordRepository, borrowingRecordMapper);
    }

    @Test
    void testBorrowBook_InvalidReturnDate() {
        Integer bookId = 1;
        Integer patronId = 1;
        BorrowingRecordDto borrowingRecordDto = new BorrowingRecordDto();
        borrowingRecordDto.setReturnDate(LocalDate.now().minusDays(1));

        when(recordRepository.existsByBookIdAndNotReturned(bookId)).thenReturn(false);
        when(patronRepository.findById(patronId)).thenReturn(Optional.of(new Patron()));
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(new Book()));

        Exception exception = assertThrows(SystemException.class, () -> borrowingRecordService.borrowBook(bookId, patronId, borrowingRecordDto));
        assertEquals(SystemErrorMessages.DATE_NOT_VALID.getMessage(), exception.getMessage());

        verify(recordRepository).existsByBookIdAndNotReturned(bookId);
        verify(patronRepository).findById(patronId);
        verify(bookRepository).findById(bookId);
        verifyNoMoreInteractions(recordRepository, borrowingRecordMapper);
    }


    @Test
    void testReturnBook_Success() {
        Integer bookId = 1;
        Integer patronId = 1;
        BorrowingRecordDto borrowingRecordDto = new BorrowingRecordDto();
        borrowingRecordDto.setReturnedAt(LocalDate.now());
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBorrowDate(LocalDate.now());

        when(recordRepository.findByBookIdAndPatronIdAndNotReturned(bookId, patronId)).thenReturn(Optional.of(borrowingRecord));
        when(recordRepository.save(borrowingRecord)).thenReturn(borrowingRecord);
        when(borrowingRecordMapper.toDto(borrowingRecord)).thenReturn(borrowingRecordDto);

        BorrowingRecordDto result = borrowingRecordService.returnBook(bookId, patronId, borrowingRecordDto);

        assertEquals(borrowingRecordDto, result);
        verify(recordRepository).findByBookIdAndPatronIdAndNotReturned(bookId, patronId);
        verify(recordRepository).save(borrowingRecord);
        verify(borrowingRecordMapper).toDto(borrowingRecord);
    }

    @Test
    void testReturnBook_RecordNotFound() {
        Integer bookId = 1;
        Integer patronId = 1;
        BorrowingRecordDto borrowingRecordDto = new BorrowingRecordDto();

        when(recordRepository.findByBookIdAndPatronIdAndNotReturned(bookId, patronId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(SystemException.class, () -> borrowingRecordService.returnBook(bookId, patronId, borrowingRecordDto));
        assertEquals(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage(), exception.getMessage());

        verify(recordRepository).findByBookIdAndPatronIdAndNotReturned(bookId, patronId);
        verifyNoMoreInteractions(recordRepository, borrowingRecordMapper);
    }

    @Test
    void testReturnBook_InvalidReturnDate() {
        Integer bookId = 1;
        Integer patronId = 1;
        BorrowingRecordDto borrowingRecordDto = new BorrowingRecordDto();
        borrowingRecordDto.setReturnedAt(LocalDate.now().minusDays(1));
        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBorrowDate(LocalDate.now());

        when(recordRepository.findByBookIdAndPatronIdAndNotReturned(bookId, patronId)).thenReturn(Optional.of(borrowingRecord));

        Exception exception = assertThrows(SystemException.class, () -> borrowingRecordService.returnBook(bookId, patronId, borrowingRecordDto));
        assertEquals(SystemErrorMessages.DATE_NOT_VALID.getMessage(), exception.getMessage());

        verify(recordRepository).findByBookIdAndPatronIdAndNotReturned(bookId, patronId);
        verifyNoMoreInteractions(recordRepository, borrowingRecordMapper);
    }


}
