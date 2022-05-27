package com.example.diningreview.review.repository;

import com.example.diningreview.DatabaseLoader;
import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.review.IRestaurantFoodScore;
import com.example.diningreview.review.model.Review;
import com.example.diningreview.user.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ReviewRepositoryTest {

    @Autowired private UserRepository userRepo;
    @Autowired private FoodRepository foodRepo;
    @Autowired private RestaurantRepository restaurantRepo;
    @Autowired private ReviewRepository reviewRepo;


    @Autowired
    private ReviewRepository underTest;

    DatabaseLoader databaseLoader;

    @BeforeAll
    public void setup() {
        databaseLoader = new DatabaseLoader(userRepo,
                foodRepo, restaurantRepo, reviewRepo);
        databaseLoader.loadDatabase();
    }


    @Test
    public void canGetFoodScoreForARestaurant() {
        //when
        List<Review> reviews = underTest.getReviewsByRestaurantId(databaseLoader.getRestaurantIds().get(0));

        assertThat(reviews.size()).isGreaterThan(0);

        List<IRestaurantFoodScore> scores = underTest.getScoreByRestaurant(databaseLoader.getRestaurantIds().get(0));

        assertThat(scores.size()).isGreaterThan(0);
    }
}