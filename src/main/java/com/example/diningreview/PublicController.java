package com.example.diningreview;

import com.example.diningreview.food.Food;
import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.user.User;
import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.user.InterestRepository;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {
    private final FoodRepository foodRepository;
    private final InterestRepository interestRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final RestaurantRepository restaurantRepository;

    @Autowired
    public PublicController(FoodRepository foodRepository,
                            InterestRepository interestRepository,
                            UserRepository userRepository, PasswordEncoder encoder,
                            RestaurantRepository restaurantRepository) {
        this.foodRepository = foodRepository;
        this.interestRepository = interestRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.restaurantRepository = restaurantRepository;
    }

    @GetMapping("/foods")
    public Iterable<Food> getFoods() {
        return foodRepository.findAll();
    }

    @GetMapping("/restaurants")
    public Iterable<Restaurant> getRestaurants() {
        return restaurantRepository.findAll();
    }

    @Transactional
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> saveUser(@Valid  @RequestBody User user) {
        return null;
    }

}
