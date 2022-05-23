package com.example.diningreview.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RestaurantFoodId implements Serializable {
    private Long food;
    private Long restaurant;
}
