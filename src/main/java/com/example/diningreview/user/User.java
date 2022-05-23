package com.example.diningreview.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Data
@Entity
@NoArgsConstructor(force = true)
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Size(min = 3)
    @NotBlank
    @Column(name = "display_name", unique = true, nullable = false)
    private String displayName;

    @NotBlank
    @Column(name = "city")
    private String city;

    @NotBlank
    @Column(name = "state")
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Size(min = 5)
    @NotBlank
    @Column(name = "password")
    private String password;

    public User(String displayName,
                String city, String state,
                String zipCode, String password) {
        this.displayName = displayName;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.password = password;
    }

    @JsonIgnore
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles")
    private List<String> roles;

    @JsonProperty(access = WRITE_ONLY, value = "foodIds")
    @Transient
    private Long[] foodIds = new Long[0];
}
