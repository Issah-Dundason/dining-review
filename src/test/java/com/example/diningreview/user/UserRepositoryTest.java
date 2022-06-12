package com.example.diningreview.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository underTest;

    @Sql("/test-db.sql")
    @Test
    void canCheckIfUserWithADisplayNameExists() {
        //given
        var nonTakenDisplayName = "any";
        var displayNameOfExistingUser = "User1";
        //when
        boolean exists1 = underTest.existsByDisplayName(displayNameOfExistingUser);
        boolean exists2 = underTest.existsByDisplayName(nonTakenDisplayName);
        boolean exists3 = underTest.existsByDisplayName(null);
        //then
        assertTrue(exists1);
        assertFalse(exists2);
        assertFalse(exists3);
    }

}