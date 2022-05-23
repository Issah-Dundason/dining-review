package com.example.diningreview.review;


import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.*;
import com.example.diningreview.review.model.*;
import com.example.diningreview.review.repository.*;
import com.example.diningreview.user.User;
import com.example.diningreview.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final RestaurantFoodRepository restaurantFoodRepository;
    private final FoodRatingRepository foodScoreRepository;



    @Autowired
    public ReviewController(ReviewRepository categoryRepository,
                            RestaurantRepository restaurantRepository, FoodRepository foodRepository, UserRepository userRepository, RestaurantFoodRepository restaurantFoodRepository, FoodRatingRepository foodScoreRepository) {
        reviewRepository = categoryRepository;
        this.restaurantFoodRepository = restaurantFoodRepository;
        this.restaurantRepository = restaurantRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
        this.foodScoreRepository = foodScoreRepository;
    }

    @Transactional
    @PostMapping("/save")
    public Review save(@RequestBody Review review,
                       @AuthenticationPrincipal UserDetails details) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository
                .findById(review.getRestaurantId());

        User user = userRepository.findByDisplayName(details.getUsername()).get();

        if(optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        review.setUser(user);
        review.setRestaurant(optionalRestaurant.get());

        List<FoodRating> scores = getFoodScores(review, optionalRestaurant.get());

        review.setFoodScores(scores);
        return reviewRepository.save(review);
    }

    private List<FoodRating> getFoodScores(Review review, Restaurant restaurant) {
        List<FoodRating> scores = new ArrayList<>();

        for(FoodRating foodScore: review.getFoodScores()) {
            Optional<Food> optionalFood = foodRepository.findById(foodScore.getFoodId());
            if(optionalFood.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Food doesn't exist");
            }
            Food food = optionalFood.get();

            Optional<RestaurantFood> restaurantFood = restaurantFoodRepository
                    .findById(new RestaurantFoodId(food.getId(), restaurant.getId()));
            if(restaurantFood.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Restaurant doesn't make this food");
            }
            FoodRating score = new FoodRating();
            score.setFood(food);
            scores.add(score);
        }
        return scores;
    }


    @Transactional
    @PutMapping("/update")
    public void update(@RequestBody Review review,
                       @AuthenticationPrincipal UserDetails details) {

        Optional<Restaurant> optionalRestaurant = restaurantRepository
                .findById(review.getRestaurantId());

        User user = userRepository.findByDisplayName(details.getUsername()).get();

        if(optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Optional<Review> savedReview = reviewRepository
                .findByRestaurantAndUser(optionalRestaurant.get(), user);

        if(savedReview.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Can't update review of another user");
        }

        Review lastReview = savedReview.get();

        foodScoreRepository.deleteAllByReview(lastReview);
        lastReview.setCommentary(review.getCommentary());
        List<FoodRating> foodScores = getFoodScores(review, optionalRestaurant.get());
        lastReview.setFoodScores(foodScores);
        reviewRepository.save(lastReview);
    }

    /*@DeleteMapping("/{id}/delete")
    public void delete(@PathVariable Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        reviewRepository.delete(review.get());
    }*/
}
