package com.example.diningreview.review.model;

import com.example.diningreview.food.Food;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "FOOD_RATINGS")
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FoodRating {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    private Food food;

    @JsonIgnore
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "RESTAURANT_ID"),
            @JoinColumn(name = "USER_ID")
    })
    private Review review;


    @Column(name = "RATING")
    private Integer rating;

    public FoodRating(Food food, Integer rating) {
        this.food = food;
        this.rating = rating;
    }
}
