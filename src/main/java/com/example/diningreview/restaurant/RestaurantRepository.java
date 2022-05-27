package com.example.diningreview.restaurant;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.Query;


public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

    boolean existsByNameAndZipCode(String name, String zipCode);

    boolean existsByNameAndIdIsNotAndStateIsAndCityIs(String name, Long id, String state, String city);

    @Query(value = "select cast(case when count(*) > 0 then 1 else 0 end as bit) " +
            "from restaurant_foods where food_id = ?1 and restaurant_id = ?2",
            nativeQuery = true)
    boolean restaurantHasFood(Long foodId, Long restaurantId);
}
