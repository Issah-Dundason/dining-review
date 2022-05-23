package com.example.diningreview.restaurant;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
    @Mock private RestaurantRepository restaurantRepo;
    @Mock private FoodRepository foodRepo;
    @Mock private RestaurantFoodRepository restaurantFoodRepo;
    private RestaurantService underTest;

    @BeforeEach
    public void setup() {
        underTest = new RestaurantService(restaurantRepo, foodRepo, restaurantFoodRepo);
    }

    @Test
    public void canSaveRestaurant() {
        //given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("Res1");
        restaurant.setFoodIds(new Long[]{1l});

        Food food = new Food();
        food.setName("food");

        Mockito.when(foodRepo.findById(1l)).thenReturn(java.util.Optional.of(food));
        //when
        underTest.save(restaurant);
        //then
        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        Mockito.verify(restaurantRepo).save(captor.capture());
        RestaurantFood resFood = captor.getValue().getRestaurantFoods().get(0);
        assertThat(resFood.getFood().getName()).isEqualTo("food");
    }

    @Test
    public void canUpdateRestaurant() {
        //given
        Restaurant oldRestaurant = new Restaurant();
        oldRestaurant.setId(1l);
        oldRestaurant.setName("Res1");
        oldRestaurant.setName("res1");
        oldRestaurant.setZipCode("1234");
        oldRestaurant.setState("state1");
        oldRestaurant.setCity("city1");
        oldRestaurant.setFoodIds(new Long[]{1l});

        //new
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setId(1l);
        newRestaurant.setName("res2");
        newRestaurant.setZipCode("1235");
        newRestaurant.setState("state2");
        newRestaurant.setCity("city2");
        newRestaurant.setFoodIds(new Long[]{1l});

        Food food = new Food();
        food.setName("food");

        Mockito.when(foodRepo.findById(1l)).thenReturn(Optional.of(food));
        Mockito.when(restaurantRepo.existsByNameAndIdIsNotAndStateIsAndCityIs(Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
        Mockito.when(restaurantRepo.findById(1l)).thenReturn(Optional.of(oldRestaurant));
        //when
        underTest.update(1l, newRestaurant);
        //then
        Mockito.verify(restaurantRepo).findById(1l);
        Mockito.verify(foodRepo).findById(1l);
        Mockito.verify(restaurantFoodRepo).deleteAllByRestaurant(oldRestaurant);
        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        Mockito.verify(restaurantRepo).save(captor.capture());
        Restaurant restaurant = captor.getValue();
        assertThat(restaurant.getName()).isEqualTo(newRestaurant.getName());
        assertThat(restaurant.getZipCode()).isEqualTo(newRestaurant.getZipCode());
        assertThat(restaurant.getState()).isEqualTo(newRestaurant.getState());
        assertThat(restaurant.getRestaurantFoods()).asList().size().isEqualTo(1);
    }


}