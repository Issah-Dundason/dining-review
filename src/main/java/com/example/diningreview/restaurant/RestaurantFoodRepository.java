package com.example.diningreview.restaurant;

import org.springframework.data.repository.CrudRepository;

public interface RestaurantFoodRepository extends CrudRepository<RestaurantFood, RestaurantFoodId> {
    void deleteAllByRestaurant(Restaurant restaurant);
}
