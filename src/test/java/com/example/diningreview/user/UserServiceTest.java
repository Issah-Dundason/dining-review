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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository userRepo;
    @Mock private PasswordEncoder encoder;
    @Mock private FoodRepository foodRepo;
    private UserService underTest;

    @BeforeEach
    public void setup() {
        underTest = new UserService(userRepo, encoder, foodRepo);
    }

    @Test
    public void canSaveUser() {
        //given
        UserForm form = new UserForm("User1",
                "city1", "state1", "1234", "password1",
                new Long[]{1L, 2L, 3L}
        );

        Mockito.when(foodRepo.findById(Mockito.any())).thenReturn(java.util.Optional.of(new Food()));
        //when
        underTest.saveUser(form);

        //then
        Mockito.verify(encoder).encode("password1");
        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepo).save(argumentCaptor.capture());
        User savedUser = argumentCaptor.getValue();
        assertThat(savedUser.getDisplayName()).isEqualTo(form.getDisplayName());
        assertThat(savedUser.getPassword()).isEqualTo(encoder.encode("password1"));
        assertThat(savedUser.getRoles()).asList().contains(Role.USER.name());
        Mockito.verify(foodRepo).findById(1L);
    }

    @Test
    public void throwsExceptionWhenSavingExistingUser() {
        //given
        UserForm form = new UserForm("User1",
                "city1", "state1", "1234", "password1", new Long[] {});
        Mockito.when(userRepo.existsByDisplayName(Mockito.any()))
                .thenReturn(true);
        //when
        //then
        assertThatThrownBy(() -> {underTest.saveUser(form);});
    }

    @Test
    public void preventsOneUserFromUpdatingAnotherUserData() {
        //given
        UserForm form = new UserForm("User1",
                "city1", "state1", "1234", "password1", new Long[]{});
        //when
        //then
        assertThatThrownBy(() -> {underTest.updateUser(form, "user2");});
    }

    @Test
    public void canUpdateUser() {
        //given
        UserForm form = new UserForm("User1",
                "city1", "state1", "1234", "password1", new Long[]{1L, 2L, 3L});

        User toBeSaved = new User("User1",
                "city2", "state3", "1234", "password5");


        Mockito.when(foodRepo.findById(1L)).thenReturn(java.util.Optional.of(new Food("food1")));
        Mockito.when(foodRepo.findById(2L)).thenReturn(java.util.Optional.of(new Food("food2")));
        Mockito.when(foodRepo.findById(3L)).thenReturn(java.util.Optional.of(new Food("food3")));

        Mockito.when(userRepo.findByDisplayName(Mockito.any())).thenReturn(java.util.Optional.of(toBeSaved));
        //when
        underTest.updateUser(form, "User1");
        //then
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepo).findByDisplayName(Mockito.any());
        Mockito.verify(userRepo).save(captor.capture());
        User user = captor.getValue();
        assertThat(user.getCity()).isEqualTo(form.getCity());
        assertThat(user.getState()).isEqualTo(form.getState());
        assertThat(user.getZipCode()).isEqualTo(form.getZipCode());
        assertThat(user.getInterestedFoods().size()).isEqualTo(3);
    }

    @Test
    public void onlyAdminCanGetAnyUserData() {
        //given
        String adminDisplayName = "Admin";
        String displayName = "user1";

        User admin = new User(adminDisplayName, "NONE",
                "NONE", "NONE", "password");
        admin.setRoles(List.of(Role.ADMIN.name()));

        User user = new User();


        Mockito.when(userRepo.findByDisplayName(adminDisplayName))
                .thenReturn(Optional.of(admin));
        Mockito.when(userRepo.findByDisplayName(displayName)).thenReturn(Optional.of(user));
        Mockito.when(userRepo.existsByDisplayName(displayName)).thenReturn(true);

        //when
        //then
        underTest.getUser(displayName, adminDisplayName);
    }

    @Test
    public void aUserCanGetOnlyOwnedData() {
        //given
        String getterName = "User1";
        User getter = new User();
        getter.setRoles(List.of(Role.USER.name()));

        String displayName = "User1";

        Mockito.when(userRepo.findByDisplayName(getterName))
                .thenReturn(Optional.of(getter));
        Mockito.when(userRepo.findByDisplayName(displayName)).thenReturn(Optional.of(new User()));
        Mockito.when(userRepo.existsByDisplayName(displayName)).thenReturn(true);

        //when then
        underTest.getUser(displayName, getterName);
    }

    @Test
    public void userCanNotGetDataOfDifferentUser() {
        //given
        String getterName = "User1";
        User getter = new User();
        getter.setRoles(List.of(Role.USER.name()));

        String displayName = "User2";

        Mockito.when(userRepo.findByDisplayName(getterName))
                .thenReturn(Optional.of(getter));
        Mockito.when(userRepo.findByDisplayName(displayName)).thenReturn(Optional.of(new User()));
        Mockito.when(userRepo.existsByDisplayName(displayName)).thenReturn(true);

        //when//then
        assertThatThrownBy(() -> underTest.getUser(displayName, getterName));

    }
}