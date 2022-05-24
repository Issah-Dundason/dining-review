package com.example.diningreview.restaurant;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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


    public Restaurant save(RestaurantForm form) {
        boolean exists = restaurantRepo.existsByNameAndAndZipCode(form.getName(),
                form.getZipCode());
        if(exists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "restaurant exists");
        }

        Restaurant restaurant = new Restaurant(
                form.getName(),
                form.getZipCode(),
                form.getCity(),
                form.getState()
                );

        addFood(form, restaurant);

        return restaurantRepo.save(restaurant);
    }

    public void update(long id, RestaurantForm form) {
       boolean exists = restaurantRepo.existsByNameAndIdIsNotAndStateIsAndCityIs(form.getName(), id,
                form.getState(), form.getCity());
       if(exists) {
           throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                   "Restaurant with name exists!");
       }

       Optional<Restaurant> optionalRestaurant = restaurantRepo.findById(id);

       Restaurant oldRestaurant = optionalRestaurant.orElseThrow(() ->
               new ResponseStatusException(HttpStatus.BAD_REQUEST));

       restaurantFoodRepo.deleteAllByRestaurant(oldRestaurant);

        addFood(form, oldRestaurant);
        oldRestaurant.setName(form.getName());
        oldRestaurant.setCity(form.getCity());
        oldRestaurant.setZipCode(form.getZipCode());
        oldRestaurant.setState(form.getState());

        restaurantRepo.save(oldRestaurant);
    }

    private void addFood(RestaurantForm form, Restaurant oldRestaurant) {
        if(form.getFoodIds() == null) return;

        for(long foodId: form.getFoodIds()) {
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
