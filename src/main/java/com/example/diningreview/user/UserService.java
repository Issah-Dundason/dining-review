package com.example.diningreview.user;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final FoodRepository foodRepo;

    public UserService(final UserRepository userRepo,
                       final PasswordEncoder encoder,
                       final FoodRepository foodRepo) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.foodRepo = foodRepo;
    }

    public User saveUser (UserForm form) {
        boolean userExists = userRepo.existsByDisplayName(form.getDisplayName());

        if(userExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exists!");
        }

        User user = new User();
        user.setPassword(encoder.encode(form.getPassword()));
        user.setRoles(List.of(Role.USER.name()));
        user.setDisplayName(form.getDisplayName());
        user.setZipCode(form.getZipCode());
        user.setState(form.getState());
        user.setCity(form.getCity());
        saveInterests(form.getFoodIds(), user);
        return userRepo.save(user);
    }

    public void updateUser(UserForm form, String updaterDisplayName) {

        if(!form.getDisplayName().equals(updaterDisplayName)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Can't update data of another user!");
        }

        User savedUser = userRepo.findByDisplayName(updaterDisplayName).get();

        savedUser.setCity(form.getCity());
        savedUser.setZipCode(form.getZipCode());
        savedUser.setState(form.getState());
        savedUser.setPassword(encoder.encode(form.getPassword()));
        savedUser.getInterestedFoods().removeAll(savedUser.getInterestedFoods());
        saveInterests(form.getFoodIds(), savedUser);
        userRepo.save(savedUser);
    }

    private void saveInterests(Long[] foodIds, User user) {
        for(long foodId : foodIds) {
            Optional<Food> optionalFood = foodRepo.findById(foodId);
            optionalFood.ifPresentOrElse(user::addFood,
                    () -> {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Food doesn't exist");
                    });
        }
    }

    public User getUser(String displayName, String getterName) {
        boolean userExists = userRepo.existsByDisplayName(displayName);

        if(!userExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        User user = userRepo.findByDisplayName(displayName).get();
        User getter = userRepo.findByDisplayName(getterName).get();


        if(!displayName.equals(getterName) && !getter.getRoles().contains(Role.ADMIN.name()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return user;
    }
}
