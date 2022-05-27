package com.example.diningreview;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.review.model.FoodRating;
import com.example.diningreview.review.model.Review;
import com.example.diningreview.review.repository.ReviewRepository;
import com.example.diningreview.user.Role;
import com.example.diningreview.user.User;
import com.example.diningreview.user.UserRepository;

import java.util.ArrayList;
import java.util.List;


public class DatabaseLoader {
    private final UserRepository userRepo;
    private final FoodRepository foodRepo;
    private final RestaurantRepository restaurantRepo;
    private final ReviewRepository reviewRepo;

    private List<Long> restaurantIds = new ArrayList<>();


    public DatabaseLoader(UserRepository userRepo,
                          FoodRepository foodRepo,
                          RestaurantRepository restaurantRepo,
                          ReviewRepository reviewRepo) {
        this.userRepo = userRepo;
        this.foodRepo = foodRepo;
        this.restaurantRepo = restaurantRepo;
        this.reviewRepo = reviewRepo;
    }

    public void loadDatabase() {
        //given
        //food
        Food breadAndEgg = new Food("Bread & Egg");
        Food porridge = new Food("Porridge");
        Food iceCream = new Food("Ice Cream");

        breadAndEgg = foodRepo.save(breadAndEgg);
        porridge = foodRepo.save(porridge);
        iceCream = foodRepo.save(iceCream);

        Restaurant restaurant1 = new Restaurant("Restaurant1", "1234", "City1", "state1");
        restaurant1.addFood(breadAndEgg);
        restaurant1.addFood(porridge);
        restaurant1.addFood(iceCream);
        restaurant1 = restaurantRepo.save(restaurant1);
        restaurantIds.add(restaurant1.getId());

        Restaurant restaurant2 = new Restaurant("Restaurant2", "1534", "City2", "state2");
        restaurant2.addFood(breadAndEgg);
        restaurant2.addFood(porridge);
        restaurant2.addFood(iceCream);
        restaurant2 = restaurantRepo.save(restaurant2);
        restaurantIds.add(restaurant2.getId());
        //user
        User user1 = new User("User1", "City1", "State1",
                "1234", "password1");
        user1.getRoles().add(Role.USER.name());

        User user2 = new User("User2", "City2", "State2", "1463", "password2");
        user2.getRoles().add(Role.USER.name());

        user1 = userRepo.save(user1);
        user2 = userRepo.save(user2);


        //Review user1 restaurant1
        Review review = new Review(restaurant1, user1, "Nice food");

        review.addFoodRating(new FoodRating(breadAndEgg, 3));
        review.addFoodRating(new FoodRating(porridge, 4));
        review.addFoodRating(new FoodRating(iceCream, 1));

        reviewRepo.save(review);

        //user1 restaurant2
        review = new Review(restaurant2, user1, "Awesome food");

        review.addFoodRating(new FoodRating(breadAndEgg, 2));
        review.addFoodRating(new FoodRating(porridge, 5));
        review.addFoodRating(new FoodRating(iceCream, 3));

        reviewRepo.save(review);

        //user2 restaurant1
        review = new Review(restaurant1, user2, "Herh!!!");

        review.addFoodRating(new FoodRating(breadAndEgg, 2));
        review.addFoodRating(new FoodRating(porridge, 4));
        review.addFoodRating(new FoodRating(iceCream, 1));

        reviewRepo.save(review);

        //user2 restaurant2
        review = new Review(restaurant2, user2, "😋😋😋 food");

        review.addFoodRating(new FoodRating(breadAndEgg, 2));
        review.addFoodRating(new FoodRating(porridge, 5));
        review.addFoodRating(new FoodRating(iceCream, 4));

        reviewRepo.save(review);
    }

    public List<Long> getRestaurantIds() {
        return restaurantIds;
    }
}
