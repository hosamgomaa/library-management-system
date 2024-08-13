package org.task.maid.entity;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import org.task.maid.entity.common.BaseEntity;

@Data
@Entity
@Table(name = "library_user")
public class User extends BaseEntity {

    @Column(unique = true)
    @NonNull
    private String username;

    @NonNull
    private String password;
}
