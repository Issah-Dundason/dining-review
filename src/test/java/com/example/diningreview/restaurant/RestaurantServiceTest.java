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
        underTest = new RestaurantService(
                restaurantRepo,
                foodRepo,
                restaurantFoodRepo);
    }

    @Test
    public void canSaveRestaurant() {
        //given
        RestaurantForm form = new RestaurantForm("Res1",
                "1234", "City1", "State1", new Long[]{1l});


        Food food = new Food();
        food.setName("food");

        Mockito.when(foodRepo.findById(1l)).thenReturn(Optional.of(food));
        //when
        underTest.save(form);
        //then
        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        Mockito.verify(restaurantRepo).save(captor.capture());
        RestaurantFood resFood = captor.getValue().getRestaurantFoods().get(0);
        assertThat(resFood.getFood().getName()).isEqualTo("food");
    }

    @Test
    public void canUpdateRestaurant() {
        //given
        Restaurant savedRestaurant = new Restaurant();
        savedRestaurant.setId(1l);
        savedRestaurant.setName("Res1");
        savedRestaurant.setName("res1");
        savedRestaurant.setZipCode("1234");
        savedRestaurant.setState("state1");
        savedRestaurant.setCity("city1");

        //new
        RestaurantForm form = new RestaurantForm("Res1",
                "1234", "City1", "State1",(new Long[]{1l}));

        Food food = new Food();
        food.setName("food");

        Mockito.when(foodRepo.findById(1l)).thenReturn(Optional.of(food));
        Mockito.when(restaurantRepo.existsByNameAndIdIsNotAndStateIsAndCityIs(Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
        Mockito.when(restaurantRepo.findById(1l)).thenReturn(Optional.of(savedRestaurant));
        //when
        underTest.update(1l, form);
        //then
        Mockito.verify(restaurantRepo).findById(1l);
        Mockito.verify(foodRepo).findById(1l);
        Mockito.verify(restaurantFoodRepo).deleteAllByRestaurant(savedRestaurant);
        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        Mockito.verify(restaurantRepo).save(captor.capture());
        Restaurant restaurant = captor.getValue();
        assertThat(restaurant.getName()).isEqualTo(form.getName());
        assertThat(restaurant.getZipCode()).isEqualTo(form.getZipCode());
        assertThat(restaurant.getState()).isEqualTo(form.getState());
        assertThat(restaurant.getRestaurantFoods()).asList().size().isEqualTo(1);
    }


}