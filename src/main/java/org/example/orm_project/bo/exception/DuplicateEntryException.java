package org.example.orm_project.bo.exception;

public class DuplicateEntryException extends Exception {

    public DuplicateEntryException(String message) {
        super(message);
    }
}