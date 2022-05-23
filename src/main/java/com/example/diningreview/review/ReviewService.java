package com.example.diningreview.review;

import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.review.model.Review;
import com.example.diningreview.review.repository.ReviewRepository;
import com.example.diningreview.user.User;
import com.example.diningreview.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepo;
    private final FoodRepository foodRepo;
    private final RestaurantRepository restaurantRepo;
    private final UserRepository userRepo;

    public ReviewService(ReviewRepository reviewRepo, FoodRepository foodRepo,
                         RestaurantRepository restaurantRepo, UserRepository userRepo) {
        this.reviewRepo = reviewRepo;
        this.foodRepo = foodRepo;
        this.restaurantRepo = restaurantRepo;
        this.userRepo = userRepo;
    }

    public Review saveReview(Review review, String displayName) {

        Optional<Restaurant> optionalRestaurant = restaurantRepo
                .findById(review.getRestaurantId());

        if(optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.findByDisplayName(displayName).get();

        review.setUser(user);
        review.setRestaurant(optionalRestaurant.get());

        review.getFoodRatings().forEach(rating -> {
            rating.setReview(review);
            rating.setFoodRepo(foodRepo);
        });

        return reviewRepo.save(review);
    }
}
