package org.task.maid.exception;

import lombok.Getter;

@Getter
public class SystemException extends RuntimeException {
    private final String message;

    public SystemException(String message) {
        this.message = message;
    }
}