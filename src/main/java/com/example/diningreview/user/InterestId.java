package com.example.diningreview.user;

import lombok.Data;

import java.io.Serializable;


@Data
public class InterestId implements Serializable {

    private Long user;


    private Long food;
}
