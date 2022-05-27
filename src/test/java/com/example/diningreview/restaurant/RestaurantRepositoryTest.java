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
    public void shouldSaveRestaurantAndItFood() {
        //given
        Food food1 = new Food("Food1");
        Food food2 = new Food("Food2");
        food1 = foodRepo.save(food1);
        food2 = foodRepo.save(food2);

        Restaurant restaurant = new Restaurant("Restaurant1", "1234", "City1", "state1");

        //when
        restaurant = underTest.save(restaurant);
        restaurant = underTest.findById(restaurant.getId()).get();
        //then
    }

    @Test
    public void canCheckRestaurantExistsWithNameAndZipCode() {
        //given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("res1");
        restaurant.setZipCode("1234");
        restaurant.setState("state1");
        restaurant.setCity("city1");
        underTest.save(restaurant);
        //when
        //then
        assertThat(underTest.existsByNameAndZipCode("res1", "1234")).isTrue();
    }

    @Test
    public void canCheckRestaurantWithoutButSameNameExist() {
        //given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("res1");
        restaurant.setZipCode("1234");
        restaurant.setState("state1");
        restaurant.setCity("city1");
        restaurant = underTest.save(restaurant);
        //when
        //then
        assertThat(underTest.existsByNameAndIdIsNot("res1", 80l)).isTrue();
        assertThat(underTest.existsByNameAndIdIsNot("res1", restaurant.getId())).isFalse();
    }

    @Test
    public void checkTwoRestaurantsInCityHasSameName() {
        //given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("res1");
        restaurant.setZipCode("1234");
        restaurant.setState("state1");
        restaurant.setCity("city1");
        underTest.save(restaurant);
        //when
        boolean exists = underTest.existsByNameAndIdIsNotAndStateIsAndCityIs("res1",
                80l, "state1", "city1");
        //then
        assertThat(exists).isTrue();
    }
}