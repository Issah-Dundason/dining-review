package com.example.diningreview.review;

import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.review.model.FoodRating;
import com.example.diningreview.review.model.Review;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
        //given
        Restaurant restaurant = new Restaurant();
        restaurant.setName("res1");

        FoodRating rating1 = new FoodRating();
        FoodRating rating2 = new FoodRating();

        User user = new User(null, "User1",
                "city1", "state1", "1234", "password1", List.of());

        ReviewForm form = new ReviewForm();
        form.setRestaurantId(1l);
        form.setCommentary("Comment");
        form.setFoodRatings(List.of(rating1, rating2));

        Mockito.when(restaurantRepo.findById(1l)).thenReturn(Optional.of(restaurant));
        Mockito.when(userRepo.findByDisplayName("User1")).thenReturn(Optional.of(user));
        //when
        underTest.saveReview(form, "User1");
        //then
        Mockito.verify(restaurantRepo).findById(1l);
        Mockito.verify(userRepo).findByDisplayName("User1");
        ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
        Mockito.verify(reviewRepo).save(captor.capture());
        Review review = captor.getValue();
        assertThat(review.getFoodRatings().get(0).getFoodRepo()).isEqualTo(foodRepo);
        assertThat(review.getUser()).isEqualTo(user);
        assertThat(review.getRestaurant().getName()).isEqualTo("res1");
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
        //given
        FoodRating rating = new FoodRating();
        rating.setFoodId(3l);
        rating.setRate(3);

        ReviewForm form = new ReviewForm();
        form.setCommentary("Comment");
        form.setFoodRatings(List.of(rating));
        form.setRestaurantId(2l);

        Review oldReview = new Review();
        oldReview.setCommentary("Comment2");

        User user = new User();
        Restaurant restaurant = new Restaurant();

        Mockito.when(restaurantRepo.findById(2l)).thenReturn(Optional.of(restaurant));
        Mockito.when(userRepo.findByDisplayName("user1")).thenReturn(Optional.of(user));
        Mockito.when(reviewRepo.findByRestaurantAndUser(restaurant, user)).thenReturn(Optional.of(oldReview));

        //when
        underTest.updateReview(form, "user1");
        //then
        Mockito.verify(foodRatingRepo).deleteAllByReview(oldReview);
        ArgumentCaptor<Review> captor = ArgumentCaptor.forClass(Review.class);
        Mockito.verify(reviewRepo).save(captor.capture());
        Review review = captor.getValue();
        assertThat(review.getCommentary()).isEqualTo(form.getCommentary());

    }
}