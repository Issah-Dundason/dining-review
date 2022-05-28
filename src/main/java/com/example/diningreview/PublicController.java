package com.example.diningreview;

import com.example.diningreview.food.Food;
import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.review.IRestaurantFoodScore;
import com.example.diningreview.review.repository.ReviewRepository;
import com.example.diningreview.user.*;
import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final ReviewRepository reviewRepo;

    @Autowired
    public PublicController(FoodRepository foodRepository,
                            RestaurantRepository restaurantRepository,
                            UserService userService, ReviewRepository reviewRepo) {
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.reviewRepo = reviewRepo;
    }


    @GetMapping("/foods")
    public Iterable<Food> getFoods() {
        return foodRepository.findAll();
    }

    @GetMapping("/restaurants")
    public Iterable<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    @PostMapping("/register")
    public Map<String, Object> createUser(@RequestBody UserForm form) {
        User user = userService.saveUser(form);
        return Map.of("id", user.getId(), "displayName", user.getDisplayName());
    }

    @GetMapping("/restaurants/{id}/food-scores")
    public List<IRestaurantFoodScore> getRestaurantFoodScores(@PathVariable long id) {
        return reviewRepo.getScoreByRestaurant(id);
    }

}
