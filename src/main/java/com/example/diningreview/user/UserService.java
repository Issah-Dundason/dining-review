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

@Transactional
@Service
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final FoodRepository foodRepo;
    private final InterestRepository interestRepo;

    public UserService(final UserRepository userRepo,
                       final PasswordEncoder encoder,
                       final FoodRepository foodRepo,
                       final InterestRepository interestRepo) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.foodRepo = foodRepo;
        this.interestRepo = interestRepo;
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
        User savedUser = userRepo.save(user);

        saveInterests(form.getFoodIds(), savedUser);
        return savedUser;
    }

    public void updateUser(UserForm form, String updaterDisplayName) {

        if(!form.getDisplayName().equals(updaterDisplayName)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Can't update data of another user!");
        }

        User savedUser = userRepo.findByDisplayName(updaterDisplayName).get();
        interestRepo.deleteAllByUser(savedUser);


        savedUser.setCity(form.getCity());
        savedUser.setZipCode(form.getZipCode());
        savedUser.setState(form.getState());
        savedUser.setPassword(encoder.encode(form.getPassword()));
        savedUser = userRepo.save(savedUser);

        saveInterests(form.getFoodIds(), savedUser);
    }

    private void saveInterests(Long[] foodIds, User user) {
        for(long foodId : foodIds) {
            Optional<Food> optionalFood = foodRepo.findById(foodId);
            optionalFood.ifPresentOrElse(food -> interestRepo.save(new Interest(user, food)),
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

    public List<Interest> getInterests(String displayName, String getterName) {
        Optional<User> optionalUser = userRepo.findByDisplayName(displayName);

        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        User user = optionalUser.get();
        User getter = userRepo.findByDisplayName(getterName).get();

        if(!displayName.equals(getterName) & !getter.getRoles().contains(Role.ADMIN.name()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return interestRepo.findByUser(optionalUser.get());

    }
}
