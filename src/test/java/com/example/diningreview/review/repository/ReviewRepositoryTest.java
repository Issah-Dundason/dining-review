package com.example.diningreview.review.repository;

import com.example.diningreview.DatabaseLoader;
import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.Restaurant;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.review.IRestaurantFoodScore;
import com.example.diningreview.review.model.ReviewStatus;
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
    public void canGetFoodScoreByRestaurantAndStatus() {
        long resId = databaseLoader.getRestaurantIds().get(1);

        Restaurant restaurant = restaurantRepo.findById(resId).get();

        List<IRestaurantFoodScore> score = underTest
                .getScoreByRestaurantAndStatus(restaurant,
                ReviewStatus.PENDING);

        assertThat(score.size()).isGreaterThan(0);
    }

    @Test
    public void canGetApprovedFoodScore() {
        long resId = databaseLoader.getRestaurantIds().get(0);

        Restaurant restaurant = restaurantRepo.findById(resId).get();

        List<IRestaurantFoodScore> score = underTest.getScoreByRestaurantAndStatus(restaurant,
                ReviewStatus.APPROVED);

        score.forEach(s -> {
            if(s.getName().equals("Bread & Egg")) {
                assertThat(s.getScore()).isEqualTo(2);
            }

            if(s.getName().equals("Porridge")) {
                assertThat(s.getScore()).isEqualTo(3);
            }

            if(s.getName().equals("Ice Cream")) {
                assertThat(s.getScore()).isEqualTo(1);
            }
        });

    }
}