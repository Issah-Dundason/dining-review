package com.example.diningreview.review.model;

import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.user.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Entity
@IdClass(ReviewId.class)
@Table(name = "reviews")
public class Review {
    @Column(name = "commentary")
    private String commentary;


    @Column(name = "status")
    @Enumerated
    private ReviewStatus reviewStatus = ReviewStatus.PENDING;

    @Id
    @JoinColumn(name = "restaurant_id")
    @ManyToOne(cascade = CascadeType.REMOVE)
    private Restaurant restaurant;

    @Id
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Transient
    @JsonProperty(access = WRITE_ONLY)
    private Long restaurantId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<FoodRating> foodRatings = new ArrayList<>();
}
