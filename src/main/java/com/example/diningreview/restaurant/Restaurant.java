package com.example.diningreview.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

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

    private String city;

    private String state;

    @OneToMany(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<RestaurantFood> restaurantFoods = new ArrayList<>();

    public void addFood(RestaurantFood restaurantFood) {
        restaurantFood.setRestaurant(this);
        restaurantFoods.add(restaurantFood);
    }

    public Restaurant(String name, String zipCode, String city, String state) {
        this.name = name;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
    }
}
