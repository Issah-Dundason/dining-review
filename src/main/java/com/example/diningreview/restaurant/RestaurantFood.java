package com.example.diningreview.restaurant;

import com.example.diningreview.food.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor(force = true)
@IdClass(RestaurantFoodId.class)
@Table(name = "restaurant_foods")
public class RestaurantFood {
    @Id
    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Id
    @ManyToOne()
    @JoinColumn(name = "food_id")
    private Food food;
}