package com.example.diningreview.restaurant;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
public class RestaurantForm {

    @NotBlank
    private String name;

    @NotBlank
    private String zipCode;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    private Long[] foodIds;
}
