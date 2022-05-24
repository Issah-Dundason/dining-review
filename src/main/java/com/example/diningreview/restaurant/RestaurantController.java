package com.example.diningreview.restaurant;

import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Map;

@RestController
@RequestMapping("/admin/restaurant")
public class RestaurantController {
    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @Transactional
    @PostMapping("/save")
    public Map<String, Long> save(@RequestBody RestaurantForm form) {
        Restaurant restaurant = service.save(form);
        return Map.of("id", restaurant.getId());
    }

    @Transactional
    @PutMapping("/{id}/update")
    public void update(@PathVariable Long id, @RequestBody RestaurantForm form) {
        service.update(id, form);
    }

}
