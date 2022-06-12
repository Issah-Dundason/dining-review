package com.example.diningreview.food;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor(force = true)
@Table(name = "FOODS")
public class Food {

    @Id
    @Column(name = "ID")
    @GeneratedValue
    private Long id;

    @Column(name = "NAME", unique = true)
    private String name;

    public Food(String name) {
        this.name = name;
    }

}
