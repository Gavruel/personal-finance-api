package com.gsantos.personalfinanceapi.service;

public interface PasswordService {
    String hash(String rawPassword);
    boolean verify(String rawPassword, String storedHash);
}
