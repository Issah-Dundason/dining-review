package com.example.diningreview.food;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class FoodService {
    private final FoodRepository foodRepo;

    public FoodService(FoodRepository foodRepo) {
        this.foodRepo = foodRepo;
    }

    public Food save(Food food) {
        try {
            return foodRepo.save(food);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already exist.");
        }
    }

    public void update(long id, Food food) {
        Optional<Food> optionalFood = foodRepo.findById(id);
        if(optionalFood.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Food savedFood = optionalFood.get();
        savedFood.setName(food.getName());
        foodRepo.save(savedFood);
    }
}
