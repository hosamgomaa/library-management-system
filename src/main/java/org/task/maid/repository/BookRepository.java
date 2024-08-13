package org.task.maid.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.task.maid.entity.Book;
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

}