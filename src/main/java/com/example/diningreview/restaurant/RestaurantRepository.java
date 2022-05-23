package com.example.diningreview.restaurant;

import org.springframework.data.repository.CrudRepository;

public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {
    boolean existsByNameAndAndZipCode(String name, String zipCode);
    boolean existsByNameAndIdIsNot(String name, Long id);
    boolean existsByNameAndIdIsNotAndStateIsAndCityIs(String name, Long id, String state, String city);
}
