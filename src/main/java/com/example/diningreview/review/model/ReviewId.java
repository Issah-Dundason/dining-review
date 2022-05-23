package com.example.diningreview.review.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ReviewId implements Serializable {
    private Long restaurant;
    private Long user;
}
