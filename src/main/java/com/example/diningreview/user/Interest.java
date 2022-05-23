package com.example.diningreview.user;

import com.example.diningreview.food.Food;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Entity
@Table(name = "interests")
@IdClass(InterestId.class)
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Interest {

    @Id
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

}
