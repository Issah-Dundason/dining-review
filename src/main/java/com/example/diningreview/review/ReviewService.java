package com.example.diningreview.review;

import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.review.model.Review;
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

    public ReviewService(ReviewRepository reviewRepo, FoodRepository foodRepo,
                         RestaurantRepository restaurantRepo,
                         UserRepository userRepo) {
        this.reviewRepo = reviewRepo;
        this.foodRepo = foodRepo;
        this.restaurantRepo = restaurantRepo;
        this.userRepo = userRepo;
    }

    public Review saveReview(ReviewForm form, String displayName) {

        Optional<Restaurant> optionalRestaurant = restaurantRepo
                .findById(form.getRestaurantId());

        if(optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = userRepo.findByDisplayName(displayName).get();
        Review review = new Review(optionalRestaurant.get(), user, form.getCommentary());

        form.getFoodRatings().forEach(ratingForm -> {
           // boolean foodBelongsToRestaurant = restaurantFoodRepo.restaurantWithIdHasFoodWithId(restaurant.getId(),
           //        rating.getFoodId());
        });


//        form.getFoodRatings().forEach(rating -> {
//            boolean foodBelongsToRestaurant = restaurantFoodRepo.restaurantWithIdHasFoodWithId(restaurant.getId(),
//                    rating.getFoodId());
//            if(!foodBelongsToRestaurant) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                        String.format("Restaurant doesn't have food with id %d", rating.getFoodId()));
//            }
//            rating.setReview(review);
//            rating.setFoodRepo(foodRepo);
//        });

        ///review.setFoodRatings(form.getFoodRatings());

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



//        form.getFoodRatings().forEach(rating -> {
//            boolean foodBelongsToRestaurant = restaurantFoodRepo.restaurantWithIdHasFoodWithId(restaurant.getId(),
//                    rating.getFoodId());
//            if(!foodBelongsToRestaurant) {
//                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
//                        String.format("Restaurant doesn't have food with id %d", rating.getFoodId()));
//            }
//            rating.setReview(review);
//            rating.setFoodRepo(foodRepo);
//        });
//        review.setFoodRatings(form.getFoodRatings());

        reviewRepo.save(review);
    }
}
