package com.example.diningreview.review.repository;

import com.example.diningreview.exception.RestaurantNotFoundException;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.review.IRestaurantFoodScore;
import com.example.diningreview.review.model.ReviewStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository underTest;

    @Autowired
    private RestaurantRepository restaurantRepo;

    @Sql(scripts = "/test-db.sql")
    @Test
    public void canGetPendingFoodScoresForARestaurant() {
        var restaurant = restaurantRepo.findById(2L)
                .orElseThrow(RestaurantNotFoundException::new);

        List<IRestaurantFoodScore> scores = underTest
                .getScoreByRestaurantAndStatus(restaurant,
                ReviewStatus.PENDING);

        assertThat(scores.size()).isEqualTo(3);
    }

    @Sql(scripts = "/test-db.sql")
    @Test
    public void canGetAPProvedFoodScoresForARestaurant() {
        //given
        var restaurant = restaurantRepo.findById(1L)
                .orElseThrow(RestaurantNotFoundException::new);

        //when
        List<IRestaurantFoodScore> score = underTest
                .getScoreByRestaurantAndStatus(restaurant,
                ReviewStatus.APPROVED);

        //then
        score.forEach(s -> {
            if(s.getName().equals("Bread & Egg")) {
                assertThat(s.getScore()).isEqualTo(2);
            }

            if(s.getName().equals("Porridge")) {
                assertThat(s.getScore()).isEqualTo(3);
            }

            if(s.getName().equals("Tom brown")) {
                assertThat(s.getScore()).isEqualTo(1);
            }
        });

    }
}