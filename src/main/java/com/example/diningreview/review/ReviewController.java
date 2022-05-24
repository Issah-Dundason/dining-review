package com.example.diningreview.review;


import com.example.diningreview.review.model.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService service;

    public ReviewController(ReviewService service) {
        this.service = service;
    }


    @PostMapping("/save")
    public Review save(@RequestBody ReviewForm form,
                       @AuthenticationPrincipal UserDetails details) {
        return null;
    }


    @PutMapping("{displayName}/{restaurantId}/update")
    public void update(@PathVariable String displayName,
            @PathVariable long restaurantId,
            @RequestBody ReviewForm form,
                       @AuthenticationPrincipal UserDetails details) {
    }

}
