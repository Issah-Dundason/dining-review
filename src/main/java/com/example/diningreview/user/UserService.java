package com.example.diningreview.user;

import com.example.diningreview.exception.FoodNotFoundException;
import com.example.diningreview.exception.UnacceptableException;
import com.example.diningreview.exception.UserExistsException;
import com.example.diningreview.exception.UserNotFoundException;
import com.example.diningreview.food.FoodRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class UserService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final FoodRepository foodRepo;
    private final ModelMapper mapper;

    public UserService(final UserRepository userRepo,
                       final PasswordEncoder encoder,
                       final FoodRepository foodRepo, ModelMapper mapper) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.foodRepo = foodRepo;
        this.mapper = mapper;
    }

    public User saveUser (UserForm form) {
        boolean userExists = userRepo.existsByDisplayName(form.getDisplayName());

        if(userExists) throw new UserExistsException();

        User user = this.mapper.map(form, User.class);

        user.setRoles(List.of(Role.USER.name()));
        saveInterests(form.getFoodIds(), user);
        user.setPassword(encoder.encode(form.getPassword()));

        return userRepo.save(user);
    }

    public void updateUser(UserForm form, String updaterDisplayName) {

        if(!form.getDisplayName().equals(updaterDisplayName))
            throw new UnacceptableException();

        User savedUser = userRepo.findByDisplayName(updaterDisplayName)
                .orElseThrow(UserNotFoundException::new);

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
            foodRepo.findById(foodId)
                    .ifPresentOrElse(user::addFood, FoodNotFoundException::new);
        }
    }

    public User getUser(String displayName, UserDetails principal) {

        User user = userRepo.findByDisplayName(displayName).orElseThrow(UserNotFoundException::new);

        var roles = principal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        if(!displayName.equals(principal.getUsername()) && !roles.contains(Role.ADMIN.name()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);

        return user;
    }
}
