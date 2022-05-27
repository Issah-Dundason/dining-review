package com.example.diningreview.restaurant;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/restaurant")
public class RestaurantController {
    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public Map<String, Long> save(@RequestBody RestaurantForm form) {
        Restaurant restaurant = service.save(form);
        return Map.of("id", restaurant.getId());
    }

    @PutMapping("/{id}/update")
    public void update(@PathVariable Long id, @RequestBody RestaurantForm form) {
        service.update(id, form);
    }

}
