package com.example.diningreview.user;

import com.example.diningreview.food.Food;
import com.example.diningreview.food.FoodRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    public User saveUser (User user) {
        boolean userExists = userRepo.existsByDisplayName(user.getDisplayName());

        if(userExists) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User exists!");
        }

        user.setPassword(encoder.encode(user.getPassword()));
        user.setRoles(List.of("ROLE_USER"));
        User savedUser = userRepo.save(user);

        saveInterests(user.getFoodIds(), savedUser);
        return savedUser;
    }

    public void updateUser(User user, String updaterDisplayName) {

        if(!user.getDisplayName().equals(updaterDisplayName)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Can't update data of another user!");
        }

        User savedUser = userRepo.findByDisplayName(updaterDisplayName).get();
        interestRepo.deleteAllByUser(savedUser);


        savedUser.setCity(user.getCity());
        savedUser.setZipCode(user.getZipCode());
        savedUser.setState(user.getState());
        savedUser.setPassword(encoder.encode(user.getPassword()));
        savedUser = userRepo.save(savedUser);

        saveInterests(user.getFoodIds(), savedUser);
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

    public User getUser(String displayName) {
        boolean userExists = userRepo.existsByDisplayName(displayName);
        if(!userExists) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return userRepo.findByDisplayName(displayName).get();
    }

    public List<Interest> getInterests(String displayName) {
        Optional<User> optionalUser = userRepo.findByDisplayName(displayName);

        if(optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        return interestRepo.findByUser(optionalUser.get());

    }
}
