package com.example.diningreview.restaurant;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name", unique = true)
    private String name;

    @NotBlank
    @Column(name = "zip_code")
    private String zipCode;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @OneToMany(cascade = CascadeType.ALL)
    private List<RestaurantFood> restaurantFoods = new ArrayList<>();

    public void addFood(RestaurantFood restaurantFood) {
        restaurantFood.setRestaurant(this);
        restaurantFoods.add(restaurantFood);
    }

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long[] foodIds;
}
