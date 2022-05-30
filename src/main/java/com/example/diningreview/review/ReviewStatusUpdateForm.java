package com.example.diningreview.review;

import lombok.Data;

@Data
public class ReviewStatusUpdateForm {
    private String userDisplayName;
    private long restaurantId;
    private int status;
}
