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
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;


    @Column(name = "display_name", unique = true, nullable = false)
    private String displayName;


    @Column(name = "city")
    private String city;


    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;


    @Column(name = "password")
    private String password;

    @ManyToMany
    @JoinTable(name = "user_foods",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "food_id", referencedColumnName = "id"))
    private Set<Food> interestedFoods = new HashSet<>();

    public void addFood(Food food) {
        interestedFoods.add(food);
    }

    public void removeFood(Food food) {
        interestedFoods.remove(food);
    }


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles")
    private List<String> roles = new ArrayList<>();

    public User(String displayName, String city, String state, String zipCode, String password) {
        this.displayName = displayName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.password = password;
    }
}
