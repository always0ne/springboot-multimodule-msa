package com.multimodule.msa.exception;

public class IdAlreadyExistsException extends RuntimeException {

    public IdAlreadyExistsException(String userId) {
        super("ID: " + userId + " 이미 사용중인 아이디입니다.");
    }
}
