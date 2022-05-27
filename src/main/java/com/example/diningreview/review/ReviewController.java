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
        return service.saveReview(form, details.getUsername());
    }

    @PutMapping("update")
    public void update (@RequestBody ReviewForm form,
                       @AuthenticationPrincipal UserDetails details) {
        service.updateReview(form, details.getUsername());
    }

}
