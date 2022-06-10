package com.example.diningreview.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "restaurant not found")
public class RestaurantNotFoundException extends RuntimeException {
}
