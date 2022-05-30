package com.example.diningreview.review;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.review.model.FoodRating;
import com.example.diningreview.review.model.Review;
import com.example.diningreview.review.model.ReviewStatus;
import com.example.diningreview.review.repository.FoodRatingRepository;
import com.example.diningreview.review.repository.ReviewRepository;
import com.example.diningreview.user.User;
import com.example.diningreview.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepo;
    private final FoodRepository foodRepo;
    private final RestaurantRepository restaurantRepo;
    private final UserRepository userRepo;
    private final FoodRatingRepository foodRatingRepo;

    public ReviewService(ReviewRepository reviewRepo, FoodRepository foodRepo,
                         RestaurantRepository restaurantRepo,
                         UserRepository userRepo, FoodRatingRepository foodRatingRepo) {
        this.reviewRepo = reviewRepo;
        this.foodRepo = foodRepo;
        this.restaurantRepo = restaurantRepo;
        this.userRepo = userRepo;
        this.foodRatingRepo = foodRatingRepo;
    }

    public Review saveReview(ReviewForm form, String displayName) {

        Optional<Restaurant> optionalRestaurant = restaurantRepo
                .findById(form.getRestaurantId());

        if(optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.findByDisplayName(displayName).get();
        Restaurant restaurant = optionalRestaurant.get();
        Review review = new Review(restaurant, user, form.getCommentary());

        addReviewRating(form, restaurant, review);

        return reviewRepo.save(review);
    }


    public void updateReview(ReviewForm form,
                             String updaterDisplayName) {

        Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(form.getRestaurantId());
        User user = userRepo.findByDisplayName(updaterDisplayName).get();


        Restaurant restaurant = optionalRestaurant.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Restaurant doesn't exist"));

        Optional<Review> optionalReview = reviewRepo.findByRestaurantAndUser(restaurant, user);

        Review review = optionalReview.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Review does not exist!"));

        review.setCommentary(form.getCommentary());
        foodRatingRepo.deleteAllByReview(review);

        addReviewRating(form,restaurant,review);

        reviewRepo.save(review);
    }

    private void addReviewRating(ReviewForm form, Restaurant restaurant, Review review) {
        form.getFoodRatings().forEach(ratingForm -> {
            boolean foodAvailableAtRestaurant = restaurantRepo.restaurantHasFood(ratingForm.getFoodId()
                    , restaurant.getId());
            if(!foodAvailableAtRestaurant) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Restaurant doesn't have food with id %d", ratingForm.getFoodId()));
            }
            Food food = foodRepo.findById(ratingForm.getFoodId()).get();
            review.addFoodRating(new FoodRating(food, ratingForm.getRate()));
        });
    }

    public void changeReviewStatus(ReviewStatusUpdateForm form) {

        User user = userRepo.findByDisplayName(form.getUserDisplayName()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));
        Restaurant restaurant = restaurantRepo.findById(form.getRestaurantId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurant does not exist"));
        Review review = reviewRepo.findByRestaurantAndUser(restaurant, user).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Review does not exist"));

        if(form.getStatus() < 0 || form.getStatus() > ReviewStatus.values().length) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        review.setReviewStatus(ReviewStatus.values()[form.getStatus()]);

        reviewRepo.save(review);
    }
}
