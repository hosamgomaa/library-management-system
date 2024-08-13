package org.task.maid.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.task.maid.entity.common.BaseEntity;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "borrowing_record")
public class BorrowingRecord extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patron_id")
    private Patron patron;

    private LocalDate borrowDate;

    private LocalDate returnDate;

    private LocalDate returnedAt;

    private Boolean returned;
}
