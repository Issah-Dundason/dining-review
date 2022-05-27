package com.example.diningreview.restaurant;

import com.example.diningreview.food.Food;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
@NoArgsConstructor(force = true)
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;


    @ManyToMany
    @JoinTable(name = "restaurant_foods",
            joinColumns = @JoinColumn(name = "restaurant_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "food_id", referencedColumnName = "id")
    )
    List<Food> availableFood = new ArrayList<>();

    public void addFood(Food food) {
        availableFood.add(food);
    }

    public void removeFood(Food food) {
        availableFood.remove(food);
    }

    public Restaurant(String name, String zipCode, String city, String state) {
        this.name = name;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }
}
