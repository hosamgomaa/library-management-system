package org.task.maid.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.task.maid.entity.common.BaseEntity;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "book")
public class Book extends BaseEntity {

    private String title;
    private String author;
    private LocalDate publicationYear;
    private String isbn;

}
