package org.task.maid.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.task.maid.entity.common.BaseEntity;

@Data
@Entity
@Table(name = "patron")
public class Patron extends BaseEntity {

    private String name;
    private String contactInformation;
}
