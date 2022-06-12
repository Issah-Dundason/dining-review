package com.example.diningreview.user;

import com.example.diningreview.food.Food;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;


    @Column(name = "DISPLAY_NAME", unique = true, nullable = false)
    private String displayName;


    @Column(name = "CITY")
    private String city;


    @Column(name = "STATE")
    private String state;

    @Column(name = "ZIP_CODE")
    private String zipCode;


    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany
    @JoinTable(name = "USER_FOODS",
            joinColumns = @JoinColumn(name = "USER_ID", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "FOOD_ID", referencedColumnName = "ID"))
    private Set<Food> interestedFoods = new HashSet<>();

    public void addFood(Food food) {
        interestedFoods.add(food);
    }

    public void removeFood(Food food) {
        interestedFoods.remove(food);
    }


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "ROLES")
    private List<String> roles = new ArrayList<>();

    public User(String displayName,
                String city, String state,
                String zipCode, String password) {
        this.displayName = displayName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.password = password;
    }
}
