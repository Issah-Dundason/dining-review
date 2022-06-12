package com.example.diningreview.review.repository;

import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.review.IRestaurantFoodScore;
import com.example.diningreview.review.model.Review;
import com.example.diningreview.review.model.ReviewId;
import com.example.diningreview.review.model.ReviewStatus;
import com.example.diningreview.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, ReviewId> {

    Optional<Review> findByRestaurantAndUser(Restaurant restaurant, User user);

    void deleteByRestaurantAndUser(Restaurant restaurant, User user);

    @Query(
           "SELECT fd.name as name, AVG(fr.rating) as score, COUNT(fr.rating) as count " +
           "FROM FoodRating fr " +
           "JOIN fr.review rv JOIN fr.food fd " +
           "WHERE rv.restaurant = ?1 " +
           "AND rv.reviewStatus = ?2 GROUP BY fd.name"
    )
    List<IRestaurantFoodScore> getScoreByRestaurantAndStatus(Restaurant restaurant, ReviewStatus status);

    List<Review> getReviewsByRestaurantId(long id);
}
