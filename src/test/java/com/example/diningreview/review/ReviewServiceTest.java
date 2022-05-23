package com.example.diningreview.review;

import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.review.repository.ReviewRepository;
import com.example.diningreview.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock private ReviewRepository reviewRepo;
    @Mock private FoodRepository foodRepo;
    @Mock private RestaurantRepository restaurantRepo;
    @Mock private UserRepository userRepo;

    @Test
    public void canSaveReview() {

    }
}