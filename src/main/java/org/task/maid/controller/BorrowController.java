package org.task.maid.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.task.maid.dto.BorrowingRecordDto;
import org.task.maid.service.BorrowingRecordService;

@RestController
@RequestMapping("/")
@AllArgsConstructor
public class BorrowController {

    private BorrowingRecordService borrowingRecordService;

    @PostMapping("/borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> borrowBook(@PathVariable("bookId") Integer bookId, @PathVariable("patronId") Integer patronId, @Valid @RequestBody BorrowingRecordDto dto) {
        dto = borrowingRecordService.borrowBook(bookId, patronId, dto);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecordDto> returnBook(@PathVariable("bookId") Integer bookId, @PathVariable("patronId") Integer patronId, @RequestBody BorrowingRecordDto dto) {
        dto = borrowingRecordService.returnBook(bookId, patronId, dto);
        return ResponseEntity.ok(dto);
    }
}
