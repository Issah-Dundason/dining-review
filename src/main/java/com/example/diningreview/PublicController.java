package com.example.diningreview;

import com.example.diningreview.food.Food;
import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.review.IRestaurantFoodScore;
import com.example.diningreview.review.model.ReviewStatus;
import com.example.diningreview.review.repository.ReviewRepository;
import com.example.diningreview.security.JwtTokenUtil;
import com.example.diningreview.user.*;
import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/public")
public class PublicController {
    private final FoodRepository foodRepository;
    private final RestaurantRepository restaurantRepository;
    private final UserService userService;
    private final ReviewRepository reviewRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public PublicController(FoodRepository foodRepository,
                            RestaurantRepository restaurantRepository,
                            UserService userService, ReviewRepository reviewRepo,
                            AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil) {
        this.foodRepository = foodRepository;
        this.restaurantRepository = restaurantRepository;
        this.userService = userService;
        this.reviewRepo = reviewRepo;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
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

         Restaurant restaurant = restaurantRepository.findById(id).orElseThrow(() ->
                 new ResponseStatusException(
                 HttpStatus.BAD_REQUEST
         ));

         return reviewRepo.getScoreByRestaurantAndStatus(restaurant, ReviewStatus.APPROVED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestBody LoginForm form) {
        try {
           Authentication authentication =  authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(form.getDisplayName(), form.getPassword()));
            return ResponseEntity.ok(Map.of("token", jwtTokenUtil.generateToken(authentication)));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
