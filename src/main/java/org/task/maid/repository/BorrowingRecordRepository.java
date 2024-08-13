package org.task.maid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.task.maid.entity.BorrowingRecord;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Integer> {

    @Query("SELECT COUNT(br) > 0 FROM BorrowingRecord br WHERE br.book.id = :bookId AND br.returned = FALSE ")
    Boolean existsByBookIdAndNotReturned(@Param("bookId") Integer bookId);

    @Query("SELECT br FROM BorrowingRecord br WHERE br.book.id = :bookId  AND br.patron.id = :patronId AND br.returned = FALSE ")
    Optional<BorrowingRecord> findByBookIdAndPatronIdAndNotReturned(@Param("bookId") Integer bookId, @Param("patronId") Integer patronId);
}