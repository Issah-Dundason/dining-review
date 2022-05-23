package com.example.diningreview.user;


import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class InterestRepositoryTest {
    @Autowired
    private InterestRepository underTest;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FoodRepository foodRepo;

    @Test
    public void canFindByUser() {
        //given
        Food food = new Food();
        food.setName("Food1");

        User user = new User("User1",
                "city1", "state1", "1234", "password1");

        food = foodRepo.save(food);
        user = userRepo.save(user);

        Interest interest = new Interest(user, food);
        underTest.save(interest);

        //when
        List<Interest> userInterests = underTest.findByUser(user);
        //then
        assertThat(userInterests).asList().contains(interest);

    }

    @Test
    public void canDeleteAllByUser() {
        //given
        Food food1 = new Food();
        food1.setName("Food1");

        Food food2 = new Food();
        food2.setName("Food2");

        User user = new User("User1",
                "city1", "state1", "1234", "password1");

        food1 = foodRepo.save(food1);
        food2 = foodRepo.save(food2);
        user = userRepo.save(user);

        underTest.save(new Interest(user, food1));
        underTest.save(new Interest(user, food2));
        //when
        underTest.deleteAllByUser(user);
        //then
        assertThat(underTest.findByUser(user)).asList().isEmpty();
    }
}