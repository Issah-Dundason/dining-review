package com.example.diningreview.food;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "foods")
public class Food {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @NotBlank
    @Column(name = "name", unique = true)
    private String name;
}
