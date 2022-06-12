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
@Table(name = "REVIEWS")
@IdClass(ReviewId.class)
public class Review {

    @Id
    @ManyToOne
    @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID")
    private Restaurant restaurant;

    @Id
    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private User user;

    @Column(name = "COMMENTARY")
    private String commentary;

    @Column(name = "STATUS")
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
