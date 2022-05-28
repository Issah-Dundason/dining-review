package com.example.diningreview.review.model;

import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(force = true)
@Table(name = "reviews")
@IdClass(ReviewId.class)
public class Review {

    @Id
    @ManyToOne
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name = "commentary")
    private String commentary;

    @Column(name = "status")
    @Enumerated
    private ReviewStatus reviewStatus = ReviewStatus.PENDING;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "review")
    @LazyCollection(value = LazyCollectionOption.FALSE)
    private List<FoodRating> foodRatings = new ArrayList<>();

    public void addFoodRating(FoodRating foodRating) {
        foodRating.setReview(this);
        foodRatings.add(foodRating);
    }

    public Review(Restaurant restaurant, User user, String commentary) {
        this.restaurant = restaurant;
        this.user = user;
        this.commentary = commentary;
    }
}
