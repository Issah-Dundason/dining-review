package com.example.diningreview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "user with display name exists")
public class UserExistsException extends RuntimeException {
}
