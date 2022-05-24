package com.example.diningreview;

import com.example.diningreview.food.Food;
import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.user.*;
import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;

    @Autowired
    public PublicController(FoodRepository foodRepository,
                            RestaurantRepository restaurantRepository,
                            UserService userService) {
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
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

}
