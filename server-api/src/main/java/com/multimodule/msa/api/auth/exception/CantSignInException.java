package com.multimodule.msa.api.auth.exception;

public class CantSignInException extends RuntimeException {
    public CantSignInException(String userId) {
        super("ID: " + userId + " 회원가입이 되어있지 않거나 잠긴 계정입니다.");
    }
}
