package org.task.maid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.task.maid.entity.Patron;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Integer> {
}