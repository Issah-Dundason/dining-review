package com.example.diningreview.review;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class FoodRatingForm {

    private Long foodId;

    @Min(1)
    @Max(5)
    private Integer rating;

}
