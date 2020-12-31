package com.multimodule.msa.api.user.exception;

public class InvalidUserException extends RuntimeException {

    public InvalidUserException() {
        super("존재하지 않거나 제제당한 계정입니다.");
    }
}
