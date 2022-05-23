package com.example.diningreview.food;

import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class FoodServiceTest {
    @Mock private FoodRepository foodRepo;
    private FoodService underTest;

    @BeforeEach
    public void setup() {
        underTest = new FoodService(foodRepo);
    }

    @Test
    public void canSaveFood() {
        //given
        Food food = new Food();
        food.setName("Food1");
        //when
        underTest.save(food);
        //then
        Mockito.verify(foodRepo).save(Mockito.any());
    }

    @Test
    public void canUpdate() {
        //given
        Food old = new Food();
        old.setName("Food1");

        Food newFood = new Food();
        newFood.setName("Food2");

        Mockito.when(foodRepo.findById(Mockito.any())).thenReturn(Optional.of(old));
        //when
        underTest.update(1l, newFood);
        //then
        ArgumentCaptor<Food> captor = ArgumentCaptor.forClass(Food.class);
        Mockito.verify(foodRepo).save(captor.capture());
        Food captured = captor.getValue();
        assertThat(captured.getName()).isEqualTo("Food2");
    }

    @Test
    public void canNotUpdateNonExistentFood() {
        //given
        Food food = new Food();
        food.setName("Food1");
        Mockito.when(foodRepo.findById(Mockito.any())).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTest.update(1l, food));
    }
}