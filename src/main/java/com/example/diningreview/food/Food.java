package com.example.diningreview.food;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor(force = true)
@Table(name = "foods")
public class Food {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    public Food(String name) {
        this.name = name;
    }

}
