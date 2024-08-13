package org.task.maid.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SystemErrorMessages {

    RESOURCE_NOT_FOUND("Resource Not Found"),
    BOOK_ALREADY_BORROWED("Book Already Borrowed"),
    DATE_NOT_VALID("Date Not valid"),
    RESOURCE_IS_RELATED("Resource Is Related");


    private final String message;

}
