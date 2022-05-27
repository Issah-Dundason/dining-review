package com.example.diningreview.food;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class FoodService {
    private final FoodRepository foodRepo;

    public FoodService(FoodRepository foodRepo) {
        this.foodRepo = foodRepo;
    }

    public Food save(FoodForm form) {
        try {
            Food food = new Food();
            food.setName(form.getName());
            return foodRepo.save(food);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name already exist.");
        }
    }

    public void update(long id, FoodForm form) {
        Optional<Food> optionalFood = foodRepo.findById(id);
        if(optionalFood.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        Food savedFood = optionalFood.get();
        savedFood.setName(form.getName());
    }
}
