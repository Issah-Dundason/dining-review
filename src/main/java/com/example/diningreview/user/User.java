package com.example.diningreview.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles")
    private List<String> roles;
}
