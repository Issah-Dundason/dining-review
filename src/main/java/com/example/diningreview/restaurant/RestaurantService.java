package com.example.diningreview.restaurant;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class RestaurantService {
    private final RestaurantRepository restaurantRepo;
    private final FoodRepository foodRepo;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository,
                             FoodRepository foodRepository) {
        this.restaurantRepo = restaurantRepository;
        this.foodRepo = foodRepository;
    }

    public Restaurant save(RestaurantForm form) {
        boolean exists = restaurantRepo.existsByNameAndZipCode(form.getName(),
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

       Restaurant restaurant = optionalRestaurant.orElseThrow(() ->
               new ResponseStatusException(HttpStatus.BAD_REQUEST));

        restaurant.setName(form.getName());
        restaurant.setCity(form.getCity());
        restaurant.setZipCode(form.getZipCode());
        restaurant.setState(form.getState());

        restaurant.getAvailableFood().removeAll(restaurant.getAvailableFood());

        addFood(form, restaurant);

        //restaurantRepo.save(restaurant);
    }

    private void addFood(RestaurantForm form, Restaurant restaurant) {
        if(form.getFoodIds() == null) return;

        for(long foodId: form.getFoodIds()) {

            Optional<Food> optionalFood = foodRepo.findById(foodId);
            optionalFood.ifPresentOrElse(restaurant::addFood,
                    () -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food doesn't exist");
                    });
        }
    }

}
