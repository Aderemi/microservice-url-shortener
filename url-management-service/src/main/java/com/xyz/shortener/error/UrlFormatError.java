package com.xyz.shortener.error;

import lombok.Data;

@Data
public class UrlFormatError {
    private final String field;
    private final String value;
    private final String message;
}
