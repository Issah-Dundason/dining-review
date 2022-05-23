package com.example.diningreview.food;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/admin/food")
public class FoodController {
    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public Map<String, Long> save(@Valid @RequestBody Food food) {
        //return Map.of("id", savedFood.getId());
        return null;
    }

    @PutMapping("/{id}/update")
    public void update(@PathVariable Long id,@Valid @RequestBody Food food){
    }

}
