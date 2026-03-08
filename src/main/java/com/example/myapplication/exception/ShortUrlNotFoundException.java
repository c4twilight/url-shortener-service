package com.example.myapplication.exception;

public class ShortUrlNotFoundException extends RuntimeException {
    public ShortUrlNotFoundException(String shortUrl) {
        super("Short URL not found: " + shortUrl);
    }
}
