package com.example.diningreview.review.model;

import com.example.diningreview.food.Food;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "food_rating")
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class FoodRating {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Food food;

    @JsonIgnore
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "restaurant_id"),
            @JoinColumn(name = "user_id")
    })
    private Review review;


    @Column(name = "rate")
    private Integer rate;

    public FoodRating(Food food, Integer rate) {
        this.food = food;
        this.rate = rate;
    }
}
