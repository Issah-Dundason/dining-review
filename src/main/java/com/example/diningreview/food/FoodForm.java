package com.example.diningreview.food;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor(force = true)
@Data
public class FoodForm {
    @NotBlank
    private String name;
}
