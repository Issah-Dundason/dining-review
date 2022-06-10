package com.example.diningreview.restaurant;

import com.example.diningreview.exception.FoodNotFoundException;
import com.example.diningreview.exception.RestaurantNotFoundException;
import com.example.diningreview.exception.UnacceptableException;
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
        var exists = restaurantRepo.existsByNameAndZipCode(form.getName(),
                form.getZipCode());

        if(exists) throw new UnacceptableException();

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
        var exists = restaurantRepo.existsByNameAndIdIsNotAndStateIsAndCityIs(form.getName(), id,
                        form.getState(), form.getCity());

        if(exists) throw new UnacceptableException();

        var restaurant = restaurantRepo.findById(id).orElseThrow(RestaurantNotFoundException::new);


        restaurant.setName(form.getName());
        restaurant.setCity(form.getCity());
        restaurant.setZipCode(form.getZipCode());
        restaurant.setState(form.getState());

        restaurant.getAvailableFood().removeAll(restaurant.getAvailableFood());

        addFood(form, restaurant);

        restaurantRepo.save(restaurant);
    }

    private void addFood(RestaurantForm form, Restaurant restaurant) {
        if(form.getFoodIds() == null) return;

        for(long foodId: form.getFoodIds()) {
            foodRepo.findById(foodId).ifPresentOrElse(restaurant::addFood, FoodNotFoundException::new);
        }
    }

}
