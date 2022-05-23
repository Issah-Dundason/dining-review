package com.example.diningreview.review.model;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Optional;

@Data
@Entity
@Table(name = "food_rating")
@NoArgsConstructor(force = true)
public class FoodRating {
    @Transient
    @JsonIgnore
    private FoodRepository foodRepo;

    public FoodRating(FoodRepository foodRepo) {
        this.foodRepo = foodRepo;
    }

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "food_id")
    @ManyToOne
    private Food food;

    @ManyToOne
    private Review review;

    @Min(1)
    @Max(5)
    @Column(name = "rate")
    private Integer rate;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private Long foodId;

    @PrePersist
    private void setFood() {
        Optional<Food> optionalFood = foodRepo.findById(this.foodId);
        optionalFood.ifPresentOrElse(savedFood -> this.food = savedFood, () -> {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Food not created!");
        });
    }

}
