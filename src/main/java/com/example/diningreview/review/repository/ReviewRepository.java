package com.example.diningreview.review.repository;

import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.review.RestaurantFoodScore;
import com.example.diningreview.review.model.Review;
import com.example.diningreview.review.model.ReviewId;
import com.example.diningreview.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends CrudRepository<Review, ReviewId> {

    Optional<Review> findByRestaurantAndUser(Restaurant restaurant, User user);

    void deleteByRestaurantAndUser(Restaurant restaurant, User user);

    @Query("Select food.name as name, avg (rating.rate) as score, count(rating.rate) as count from Food food" +
            " join FoodRating  rating on food.id = rating.food.id where rating.review.restaurant.id = ?1 " +
            "group by food.name")
    List<RestaurantFoodScore> getFoodScoreForRestaurant(long restaurantId);

}
