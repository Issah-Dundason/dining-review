package com.example.diningreview.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/user/{display-name}")
    public User getUser(@PathVariable(name = "display-name") String id) {
        return null;
    }

    @Transactional
    @PutMapping("/user/update")
    public void updateUser(@Valid @RequestBody User user, @AuthenticationPrincipal UserDetails details) {
    }

    @GetMapping("/user/{display-name}/interests")
    public List<Interest> getInterests(@PathVariable(name = "display-name") String displayName) {
        return null;
    }

}
