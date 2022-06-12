package com.example.diningreview.food;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/admin/food")
public class FoodController {
    private final FoodService service;

    public FoodController(FoodService service) {
        this.service = service;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public Map<String, Long> save(@Valid @RequestBody FoodForm form) {
        Food food = service.save(form);
        return Map.of("id", food.getId());
    }

    @PutMapping("/{id}/update")
    public void update(@PathVariable Long id,@Valid @RequestBody FoodForm form){
        service.update(id, form);
    }

    @DeleteMapping("/{id}/delete")
    public void delete(@PathVariable long id) {
        service.delete(id);
    }

}
