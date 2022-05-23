package com.example.diningreview.review.repository;

import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.review.model.Review;
import com.example.diningreview.review.model.ReviewId;
import com.example.diningreview.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, ReviewId> {
    Optional<Review> findByRestaurantAndUser(Restaurant restaurant, User user);
    void deleteByRestaurantAndUser(Restaurant restaurant, User user);
}
