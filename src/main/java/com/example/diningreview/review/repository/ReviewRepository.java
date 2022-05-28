package com.example.diningreview.review.repository;

import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.review.IRestaurantFoodScore;
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

    @Query(
            value = "select foods.name as name, avg(food_rating.rate) as score, count(food_rating.rate) as count " +
                    "from foods join food_rating on foods.id =  food_rating.food_id where " +
                    "food_rating.restaurant_id = ?1 group by foods.name",
            nativeQuery = true
    )
    List<IRestaurantFoodScore> getScoreByRestaurant(long restaurantId);

    List<Review> getReviewsByRestaurantId(long id);

}
