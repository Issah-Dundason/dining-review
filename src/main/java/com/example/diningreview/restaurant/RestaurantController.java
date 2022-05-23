package com.example.diningreview.restaurant;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin/restaurant")
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;
    private final RestaurantFoodRepository restaurantFoodRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public RestaurantController(RestaurantRepository restaurantRepository,
                                RestaurantFoodRepository restaurantFoodRepository, FoodRepository foodRepository) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantFoodRepository = restaurantFoodRepository;
        this.foodRepository = foodRepository;
    }

    @Transactional
    @PostMapping("/save")
    public Map<String, Long> save(@RequestBody Restaurant restaurant) {
        //return Map.of("id", saved.getId());
        return null;
    }

    @Transactional
    @PutMapping("/{id}/update")
    public void update(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);
        if(optionalRestaurant.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        Restaurant savedRestaurant = optionalRestaurant.get();
        savedRestaurant.setName(restaurant.getName());
        savedRestaurant.setZipCode(restaurant.getZipCode());
        restaurantFoodRepository.deleteAllByRestaurant(savedRestaurant);
        savedRestaurant = restaurantRepository.save(savedRestaurant);

        for(long foodId: restaurant.getFoodIds()) {
            Optional<Food> optionalFood = foodRepository.findById(foodId);
            Restaurant finalSavedRestaurant = savedRestaurant;
            optionalFood.ifPresentOrElse(food -> {
                        RestaurantFood restaurantFood = new RestaurantFood(finalSavedRestaurant, food);
                        restaurantFoodRepository.save(restaurantFood);
                    },
                    () -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food doesn't exist");
                    });
        }

    }

}
