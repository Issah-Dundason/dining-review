package com.example.diningreview.review.repository;

import com.example.diningreview.review.model.FoodRating;
import com.example.diningreview.review.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface FoodRatingRepository extends CrudRepository<FoodRating, Long> {
    void deleteAllByReview(Review review);
}
