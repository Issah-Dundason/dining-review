package com.example.diningreview.review;

import com.example.diningreview.exception.RestaurantNotFoundException;
import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.review.model.Review;
import com.example.diningreview.review.model.ReviewStatus;
import com.example.diningreview.review.repository.FoodRatingRepository;
import com.example.diningreview.review.repository.ReviewRepository;
import com.example.diningreview.user.User;
import com.example.diningreview.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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

        assertThatThrownBy(() -> underTest.saveReview(form, "User1"))
                .isInstanceOf(RestaurantNotFoundException.class);

   }

    @Test
    public void canUpdateTheStatusOfASavedUser() {
        //given
        User user = new User("User2", "City2", "State2", "1463", "password2");
        Restaurant restaurant = new Restaurant("Restaurant2", "1534", "City2", "state2");
        Review review = new Review(restaurant, user, "Nice food");

        ReviewStatusUpdateForm form = new ReviewStatusUpdateForm();
        form.setStatus(1);
        form.setRestaurantId(1);
        form.setUserDisplayName(user.getDisplayName());

        Mockito.when(restaurantRepo.findById(1L)).thenReturn(Optional.of(restaurant));
        Mockito.when(userRepo.findByDisplayName(user.getDisplayName())).thenReturn(Optional.of(user));
        Mockito.when(reviewRepo.findByRestaurantAndUser(restaurant, user)).thenReturn(Optional.of(review));
        //when
        underTest.changeReviewStatus(form);
        //then
        ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
        Mockito.verify(reviewRepo).save(captor.capture());
        Review savedReview = captor.getValue();
        assertThat(savedReview.getReviewStatus()).isEqualTo(ReviewStatus.APPROVED);
    }
}