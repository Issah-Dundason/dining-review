package com.example.diningreview.user;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository userRepo;
    @Mock private PasswordEncoder encoder;
    @Mock private FoodRepository foodRepo;
    @Mock private InterestRepository interestRepo;
    private UserService underTest;

    @BeforeEach
    public void setup() {
        underTest = new UserService(userRepo, encoder, foodRepo, interestRepo);
    }

    @Test
    public void canSaveUser() {
        //given
        User user = new User("User1",
                "city1", "state1", "1234", "password1");
        user.setFoodIds(new Long[]{1L, 2L, 3L});
        Mockito.when(foodRepo.findById(Mockito.any())).thenReturn(java.util.Optional.of(new Food()));
        //when
        underTest.saveUser(user);

        //then
        Mockito.verify(encoder).encode("password1");
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepo).save(argumentCaptor.capture());
        User savedUser = argumentCaptor.getValue();
        assertThat(savedUser.getRoles()).asList().contains("ROLE_USER");
        Mockito.verify(foodRepo).findById(1L);
        Mockito.verify(interestRepo, Mockito.times(3)).save(Mockito.any());
    }

    @Test
    public void throwsExceptionWhenSavingExistingUser() {
        //given
        User user = new User("User1",
                "city1", "state1", "1234", "password1");
        Mockito.when(userRepo.existsByDisplayName(Mockito.any()))
                .thenReturn(true);
        //when
        //then
        assertThatThrownBy(() -> {underTest.saveUser(user);});
    }

    @Test
    public void preventsOneUserFromUpdatingAnotherUserData() {
        //given
        User user = new User("User1",
                "city1", "state1", "1234", "password1");
        //when
        //then
        assertThatThrownBy(() -> {underTest.updateUser(user, "user2");});
    }

    @Test
    public void canUpdateUser() {
        //given
        User newUser = new User("User1",
                "city1", "state1", "1234", "password1");

        User oldUser = new User("User1",
                "city2", "state3", "1234", "password5");

        newUser.setFoodIds(new Long[]{1L, 2L, 3L});
        Mockito.when(foodRepo.findById(Mockito.any())).thenReturn(java.util.Optional.of(new Food()));
        Mockito.when(userRepo.findByDisplayName(Mockito.any())).thenReturn(java.util.Optional.of(oldUser));
        //when
        underTest.updateUser(newUser, "User1");
        //then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(interestRepo).deleteAllByUser(Mockito.any());
        Mockito.verify(userRepo).findByDisplayName(Mockito.any());
        Mockito.verify(interestRepo, Mockito.atLeast(1)).save(Mockito.any());
        Mockito.verify(userRepo).save(captor.capture());
        User user = captor.getValue();
        assertThat(user.getCity()).isEqualTo(newUser.getCity());
        assertThat(user.getState()).isEqualTo(newUser.getState());
        assertThat(user.getZipCode()).isEqualTo(newUser.getZipCode());
    }
}