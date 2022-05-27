package com.example.diningreview.restaurant;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class RestaurantRepositoryTest {

    @Autowired
    private RestaurantRepository underTest;


    @Autowired
    private FoodRepository foodRepo;

    @Test
    public void canSaveRestaurantAndFoodAvailable() {
        //given
        Food food1 = new Food("Food1");
        Food food2 = new Food("Food2");
        food1 = foodRepo.save(food1);
        food2 = foodRepo.save(food2);

        Restaurant restaurant = new Restaurant("Restaurant1", "1234", "City1", "state1");
        restaurant.addFood(food1);
        restaurant.addFood(food2);

        //when
        restaurant = underTest.save(restaurant);
        //then
        assertThat(restaurant.getAvailableFood()).asList().contains(food1, food2);
    }

    @Test
    public void canCheckRestaurantExistsWithNameAndZipCode() {
        //given
        Restaurant restaurant = new Restaurant("res1", "1234", "state1", "city1");
        underTest.save(restaurant);
        //when
        //then
        assertThat(underTest.existsByNameAndZipCode("res1", "1234")).isTrue();
    }


    @Test
    public void checkRestaurantInTheSameCityAndStateHasSameName() {
        //given
        Restaurant restaurant = new Restaurant("res1", "1234", "state1", "city1");
        restaurant = underTest.save(restaurant);

        //when
        boolean exists = underTest.existsByNameAndIdIsNotAndStateIsAndCityIs("res1",
                restaurant.getId() * 3, restaurant.getState(), restaurant.getCity());
        //then
        assertThat(exists).isTrue();
    }

    @Test
    public void canCheckRestaurantWithIDPreparesACertainFood() {
        //given
        Food food1 = new Food("Food1");
        Food food2 = new Food("Food2");
        food1 = foodRepo.save(food1);
        food2 = foodRepo.save(food2);

        Restaurant restaurant = new Restaurant("Restaurant1", "1234", "City1", "state1");
        restaurant.addFood(food1);
        restaurant.addFood(food2);
        restaurant = underTest.save(restaurant);
        //when
        boolean exists = underTest.restaurantHasFood(food1.getId(), restaurant.getId());
        //then
        assertThat(exists).isTrue();
    }
}