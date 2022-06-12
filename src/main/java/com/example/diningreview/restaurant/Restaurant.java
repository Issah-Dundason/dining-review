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
@Table(name = "RESTAURANTS")
public class Restaurant {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "ZIP_CODE")
    private String zipCode;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;


    @ManyToMany
    @JoinTable(name = "RESTAURANT_FOODS",
            joinColumns = @JoinColumn(name = "RESTAURANT_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "FOOD_ID", referencedColumnName = "ID")
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
