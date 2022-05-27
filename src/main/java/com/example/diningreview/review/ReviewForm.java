package com.example.diningreview.review;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Data
public class ReviewForm {

    private String commentary;

    @NotNull
    @Min(1)
    private Long restaurantId;

    @NotEmpty
    private List<FoodRatingForm> foodRatings = new ArrayList<>();
}
