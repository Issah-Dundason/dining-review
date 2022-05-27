package com.example.diningreview.review;

import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.review.repository.FoodRatingRepository;
import com.example.diningreview.review.repository.ReviewRepository;
import com.example.diningreview.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @Mock private ReviewRepository reviewRepo;
    @Mock private FoodRepository foodRepo;
    @Mock private RestaurantRepository restaurantRepo;
    @Mock private UserRepository userRepo;
    @Mock private FoodRatingRepository foodRatingRepo;
    private ReviewService underTest;

    @BeforeEach
    public void setup() {
        underTest = new ReviewService(reviewRepo,
                foodRepo, restaurantRepo, userRepo, foodRatingRepo);
    }

    @Test
    public void canSaveReview() {

    }

    @Test
    public void canNotSaveReviewWithoutRestaurant() {
        //given
        ReviewForm form = new ReviewForm();
        form.setRestaurantId(1l);
        form.setCommentary("Comment");

        Mockito.when(restaurantRepo.findById(1l)).thenReturn(Optional.empty());

        try {
            //when
            underTest.saveReview(form, "User1");
        } catch (Exception e) {
            //then
            Mockito.verify(restaurantRepo).findById(1l);
            Mockito.verify(userRepo, Mockito.never()).findByDisplayName(Mockito.any());
        }
    }

    @Test
    public void canUpdateReview() {
    }
}