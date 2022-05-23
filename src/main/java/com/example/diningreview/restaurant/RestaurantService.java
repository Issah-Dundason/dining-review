package com.example.diningreview.restaurant;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class RestaurantService {
    private final RestaurantRepository restaurantRepo;
    private final FoodRepository foodRepo;
    private final RestaurantFoodRepository restaurantFoodRepo;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository,
                             FoodRepository foodRepository,
                             RestaurantFoodRepository restaurantFoodRepo) {
        this.restaurantRepo = restaurantRepository;
        this.foodRepo = foodRepository;
        this.restaurantFoodRepo = restaurantFoodRepo;
    }


    public Restaurant save(Restaurant restaurant) {
        boolean exists = restaurantRepo.existsByNameAndAndZipCode(restaurant.getName(),
                restaurant.getZipCode());
        if(exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "restaurant exists");
        }

        addFood(restaurant, restaurant);

        return restaurantRepo.save(restaurant);
    }

    public void update(long id, Restaurant newRestaurant) {
       boolean exists = restaurantRepo.existsByNameAndIdIsNotAndStateIsAndCityIs(newRestaurant.getName(), id,
                newRestaurant.getState(), newRestaurant.getCity());
       if(exists) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                   "Restaurant with name exists!");
       }

       Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(id);

       Restaurant oldRestaurant = optionalRestaurant.orElseThrow(() ->
               new ResponseStatusException(HttpStatus.BAD_REQUEST));

       restaurantFoodRepo.deleteAllByRestaurant(oldRestaurant);

        addFood(newRestaurant, oldRestaurant);
        oldRestaurant.setName(newRestaurant.getName());
        oldRestaurant.setCity(newRestaurant.getCity());
        oldRestaurant.setZipCode(newRestaurant.getZipCode());
        oldRestaurant.setState(newRestaurant.getState());

        restaurantRepo.save(oldRestaurant);

    }

    private void addFood(Restaurant newRestaurant, Restaurant oldRestaurant) {
        for(long foodId: newRestaurant.getFoodIds()) {
            Optional<Food> optionalFood = foodRepo.findById(foodId);
            optionalFood.ifPresentOrElse(food -> {
                        RestaurantFood restaurantFood = new RestaurantFood();
                        restaurantFood.setFood(food);
                        oldRestaurant.addFood(restaurantFood);
                    },
                    () -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food doesn't exist");
                    });
        }
    }

}
