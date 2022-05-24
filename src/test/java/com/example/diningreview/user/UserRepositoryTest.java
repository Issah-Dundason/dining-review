package com.example.diningreview.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Test
    void canCheckIfUserWithADisplayNameExists() {
        //given
        var nonTakenDisplayName = "any";
        User user = new User(null, "User1",
                "city1", "state1", "1234", "password1", null);
        underTest.save(user);
        //when
        boolean exists1 = underTest.existsByDisplayName(user.getDisplayName());
        boolean exists2 = underTest.existsByDisplayName(nonTakenDisplayName);
        boolean exists3 = underTest.existsByDisplayName(null);
        //then
        assertTrue(exists1);
        assertFalse(exists2);
        assertFalse(exists3);
    }

}