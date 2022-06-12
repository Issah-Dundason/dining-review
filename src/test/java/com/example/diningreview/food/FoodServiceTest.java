package com.example.diningreview.food;

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
        FoodForm form = new FoodForm("Food1");
        //when
        underTest.save(form);
        //then
        ArgumentCaptor<Food> captor = ArgumentCaptor.forClass(Food.class);
        Mockito.verify(foodRepo).save(captor.capture());
        Food food = captor.getValue();
        assertThat(food.getName()).isEqualTo(form.getName());
    }

    @Test
    public void canUpdate() {
        //given
        Food old = new Food();
        old.setName("Food1");

        FoodForm form = new FoodForm("Food2");

        Mockito.when(foodRepo.findById(Mockito.any())).thenReturn(Optional.of(old));
        //when
        underTest.update(1l, form);
        //then
        ArgumentCaptor<Food> captor = ArgumentCaptor.forClass(Food.class);
        Mockito.verify(foodRepo).save(captor.capture());
        Food captured = captor.getValue();
        assertThat(captured.getName()).isEqualTo("Food2");
    }

    @Test
    public void canNotUpdateNonExistentFood() {
        //given
        FoodForm form = new FoodForm("Food1");
        Mockito.when(foodRepo.findById(Mockito.any())).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> underTest.update(1l, form));
    }

}