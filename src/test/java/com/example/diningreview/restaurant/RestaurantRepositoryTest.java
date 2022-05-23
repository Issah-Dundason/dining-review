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
        Food food1 = new Food();
        food1.setName("Food1");

        Food food2 = new Food();
        food2.setName("Food2");

        food1 = foodRepo.save(food1);
        food2 = foodRepo.save(food2);

        Restaurant restaurant = new Restaurant();
        restaurant.setName("res1");
        restaurant.setZipCode("1234");

        RestaurantFood resFood = new RestaurantFood();
        resFood.setFood(food1);
        restaurant.addFood(resFood);

        resFood = new RestaurantFood();
        resFood.setFood(food2);
        restaurant.addFood(resFood);

        //when
        restaurant = underTest.save(restaurant);
        //then
        List<String> servedFood = restaurant.getRestaurantFoods().stream()
                .map(served -> served.getFood().getName())
                .collect(Collectors.toList());
        assertThat(servedFood).asList().contains("Food1", "Food2");

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
        assertThat(underTest.existsByNameAndAndZipCode("res1", "1234")).isTrue();
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
        restaurant = underTest.save(restaurant);
        //when
        boolean exists = underTest.existsByNameAndIdIsNotAndStateIsAndCityIs("res1",
                80l, "state1", "city1");
        //then
        assertThat(exists).isTrue();
    }
}