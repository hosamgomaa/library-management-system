package org.task.maid.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

@Service
@AllArgsConstructor
public class BorrowingRecordService {

    private BorrowingRecordRepository recordRepository;
    private PatronRepository patronRepository;
    private BookRepository bookRepository;
    private BorrowingRecordMapper borrowingRecordMapper;

    public BorrowingRecordDto borrowBook(Integer bookId, Integer patronId, BorrowingRecordDto dto) {
        Boolean isBorrowed = recordRepository.existsByBookIdAndNotReturned(bookId);

        if (isBorrowed)
            throw new SystemException(SystemErrorMessages.BOOK_ALREADY_BORROWED.getMessage());

        Patron patron = patronRepository.findById(patronId).orElseThrow(() -> new SystemException(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage()));
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new SystemException(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage()));

        LocalDate borrowDate = dto.getBorrowDate() == null ? LocalDate.now() : dto.getBorrowDate();
        validateDate(dto.getReturnDate(), borrowDate);

        BorrowingRecord record = new BorrowingRecord();
        record.setBook(book);
        record.setPatron(patron);
        record.setReturnDate(dto.getReturnDate());
        record.setBorrowDate(borrowDate);
        record.setReturned(false);
        record = recordRepository.save(record);
        return borrowingRecordMapper.toDto(record);
    }

    public BorrowingRecordDto returnBook(Integer bookId, Integer patronId, BorrowingRecordDto dto) {
        BorrowingRecord record = recordRepository.findByBookIdAndPatronIdAndNotReturned(bookId, patronId).orElseThrow(() -> new SystemException(SystemErrorMessages.RESOURCE_NOT_FOUND.getMessage()));

        LocalDate returnedAt = dto.getReturnedAt() == null ? LocalDate.now() : dto.getReturnedAt();
        validateDate(returnedAt, record.getBorrowDate());

        record.setReturnedAt(returnedAt);
        record.setReturned(true);
        record = recordRepository.save(record);
        return borrowingRecordMapper.toDto(record);
    }

    private void validateDate(LocalDate date1, LocalDate date2) {
        if (date1.isBefore(date2))
            throw new SystemException(SystemErrorMessages.DATE_NOT_VALID.getMessage());
    }
}
