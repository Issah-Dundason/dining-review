package com.example.diningreview.review.model;

import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.user.User;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "reviews")
@IdClass(ReviewId.class)
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

    @OneToMany(cascade = CascadeType.ALL)
    private List<FoodRating> foodRatings = new ArrayList<>();
}
