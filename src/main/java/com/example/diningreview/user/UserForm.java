package com.example.diningreview.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class UserForm {

    @Size(min = 3)
    @NotBlank
    private String displayName;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    private String zipCode;

    @Size(min = 5)
    @NotBlank
    private String password;

    private Long[] foodIds;

}
