package com.example.diningreview.user;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping("/user/{display-name}")
    public User getUser(@PathVariable(name = "display-name") String id,
                        @AuthenticationPrincipal UserDetails details) {
        return service.getUser(id, details.getUsername());
    }

    @PutMapping("/user/update")
    public void updateUser(@Valid @RequestBody UserForm form,
                           @AuthenticationPrincipal UserDetails details) {
        service.updateUser(form, details.getUsername());
    }

    @GetMapping("/user/{display-name}/interests")
    public List<Interest> getInterests(@PathVariable(name = "display-name") String displayName,
                                       @AuthenticationPrincipal UserDetails details) {
        return service.getInterests(displayName, details.getUsername());
    }

}
