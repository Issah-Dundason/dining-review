package com.example.diningreview.food;

import com.example.diningreview.exception.DisplayNameExistsException;
import com.example.diningreview.exception.FoodNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class FoodService {
    private final FoodRepository foodRepo;

    public FoodService(FoodRepository foodRepo) {
        this.foodRepo = foodRepo;
    }

    public Food save(FoodForm form) throws DisplayNameExistsException {
        try {
            Food food = new Food(form.getName());
            return foodRepo.save(food);
        } catch (Exception e) {
            throw new DisplayNameExistsException();
        }
    }

    public void update(long id, FoodForm form) {
        Food savedFood = foodRepo.findById(id).orElseThrow(FoodNotFoundException::new);
        savedFood.setName(form.getName());
        foodRepo.save(savedFood);
    }
}
