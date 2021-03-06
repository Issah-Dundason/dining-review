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
    private RestaurantService underTest;

    @BeforeEach
    public void setup() {
        underTest = new RestaurantService(
                restaurantRepo,
                foodRepo);
    }

    @Test
    public void canSaveRestaurant() {
        //given
        RestaurantForm form = new RestaurantForm("Res1",
                "1234", "City1", "State1", new Long[]{1l});

        Food food = new Food("food");

        Mockito.when(foodRepo.findById(1l)).thenReturn(Optional.of(food));
        //when
        underTest.save(form);
        //then
        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        Mockito.verify(restaurantRepo).save(captor.capture());
        Restaurant restaurant = captor.getValue();
        assertThat(restaurant.getName()).isEqualTo(form.getName());
        assertThat(restaurant.getState()).isEqualTo(form.getState());
        assertThat(restaurant.getCity()).isEqualTo(form.getCity());
        assertThat(restaurant.getZipCode()).isEqualTo(form.getZipCode());
        assertThat(restaurant.getAvailableFood()).asList().contains(food);
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
        Food food1 = new Food("Egg");
        savedRestaurant.addFood(food1);

        //new
        RestaurantForm form = new RestaurantForm("Res1",
                "1234", "City2", "State3",(new Long[]{1l}));

        Food food = new Food();
        food.setName("food");

        Mockito.when(foodRepo.findById(1l)).thenReturn(Optional.of(food));
        Mockito.when(restaurantRepo.existsByNameAndIdIsNotAndStateIsAndCityIs(Mockito.any(),
                Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(false);
        Mockito.when(restaurantRepo.findById(1l)).thenReturn(Optional.of(savedRestaurant));
        //when
        underTest.update(1l, form);
        //then
        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        Mockito.verify(restaurantRepo).save(captor.capture());
        Restaurant restaurant = captor.getValue();
        assertThat(restaurant.getName()).isEqualTo(form.getName());
        assertThat(restaurant.getZipCode()).isEqualTo(form.getZipCode());
        assertThat(restaurant.getState()).isEqualTo(form.getState());
        assertThat(restaurant.getAvailableFood()).asList().doesNotContain(food1);
        assertThat(restaurant.getAvailableFood()).asList().contains(food);
    }


}