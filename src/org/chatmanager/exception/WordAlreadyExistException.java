package org.chatmanager.exception;

public class WordAlreadyExistException extends Exception {

    public WordAlreadyExistException() {
    }

    public WordAlreadyExistException(String message) {
        super(message);
    }
}
