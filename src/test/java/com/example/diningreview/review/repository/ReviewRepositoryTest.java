package com.example.diningreview.review.repository;

import com.example.diningreview.food.FoodRepository;
import com.example.diningreview.restaurant.RestaurantRepository;
import com.example.diningreview.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepo;
    @Autowired
    private FoodRepository foodRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RestaurantRepository restaurantRepo;


//    public void canGetFoodScoresForRestaurant() {
//        //given
//        //food
//        Food breadAndEgg = new Food("Bread & Egg");
//        Food porridge = new Food("Porridge");
//        Food iceCream = new Food("Ice Cream");
//
//        breadAndEgg = foodRepo.save(breadAndEgg);
//        porridge = foodRepo.save(porridge);
//        iceCream = foodRepo.save(iceCream);
//
//        Restaurant restaurant1 = new Restaurant(null, "Restaurant1", "1234", "City1", "state1", new ArrayList<>());
//        //restaurant1.addFood(new RestaurantFood(null, breadAndEgg));
//      //  restaurant1.addFood(new RestaurantFood(null, porridge));
//      //  restaurant1.addFood(new RestaurantFood(null, iceCream));
//
//        restaurant1 = restaurantRepo.save(restaurant1);
//
//        Restaurant restaurant2 = new Restaurant(null, "Restaurant2", "1534", "City2", "state2", new ArrayList<>());
//       // restaurant2.addFood(new RestaurantFood(null, breadAndEgg));
//      //  restaurant2.addFood(new RestaurantFood(null, porridge));
//      //  restaurant2.addFood(new RestaurantFood(null, iceCream));
//
//        restaurant2 = restaurantRepo.save(restaurant2);
//
//        //user
//        User user1 = new User(null, "User1", "City1", "State1",
//                "1234", "password1",
//                List.of(Role.USER.name()));
//        User user2 = new User(null, "User2", "City2", "State2", "1463", "password2",
//                List.of(Role.USER.name()));
//
//        user1 = userRepo.save(user1);
//        user2 = userRepo.save(user2);
//
//        //Review user1 restaurant1
//        Review review = new Review("Nice food",
//                ReviewStatus.APPROVED,
//                restaurant1,
//                user1, new ArrayList<>());
//
//        //review.setFoodRatings(List.of(new FoodRating()));
//        FoodRating breadAndEggRating = new FoodRating(foodRepo, null, breadAndEgg, review, 3, null);
//        FoodRating porridgeRating = new FoodRating(foodRepo, null, porridge, review, 4, null);
//        FoodRating iceCreamRating = new FoodRating(foodRepo, null, iceCream, review, 1, null);
//        review.getFoodRatings().addAll(List.of(breadAndEggRating,porridgeRating, iceCreamRating));
//        reviewRepo.save(review);
//
//        //user1 restaurant2
//        review = new Review("Awesome food",
//                ReviewStatus.APPROVED,
//                restaurant2,
//                user1, new ArrayList<>());
//
//        breadAndEggRating = new FoodRating(foodRepo, null, breadAndEgg, review, 2, null);
//        porridgeRating = new FoodRating(foodRepo, null, porridge, review, 5, null);
//        iceCreamRating = new FoodRating(foodRepo, null, iceCream, review, 3, null);
//
//        review.getFoodRatings().addAll(List.of(breadAndEggRating,porridgeRating, iceCreamRating));
//        reviewRepo.save(review);
//
//        //user2 restaurant1
//        review = new Review("Awesome food",
//                ReviewStatus.APPROVED,
//                restaurant1,
//                user2, new ArrayList<>());
//
//        breadAndEggRating = new FoodRating(foodRepo, null, breadAndEgg, review, 2, null);
//        porridgeRating = new FoodRating(foodRepo, null, porridge, review, 4, null);
//        iceCreamRating = new FoodRating(foodRepo, null, iceCream, review, 1, null);
//
//        review.getFoodRatings().addAll(List.of(breadAndEggRating,porridgeRating, iceCreamRating));
//        reviewRepo.save(review);
//
//        //user2 restaurant2
//        review = new Review("Awesome food",
//                ReviewStatus.APPROVED,
//                restaurant2,
//                user2, new ArrayList<>());
//
//        breadAndEggRating = new FoodRating(foodRepo, null, breadAndEgg, review, 2, null);
//        porridgeRating = new FoodRating(foodRepo, null, porridge, review, 5, null);
//        iceCreamRating = new FoodRating(foodRepo, null, iceCream, review, 4, null);
//
//        review.getFoodRatings().addAll(List.of(breadAndEggRating,porridgeRating, iceCreamRating));
//        reviewRepo.save(review);
//
//        //when
//        List<RestaurantFoodScore> restaurant1Score = reviewRepo.getFoodScoreForRestaurant(restaurant1.getId());
//        List<RestaurantFoodScore> restaurant2Score = reviewRepo.getFoodScoreForRestaurant(restaurant2.getId());
//        //then
////        RestaurantFoodScore res1BreadEgg = restaurant1Score
////                .stream().filter(score -> Objects.equals(score.getName(), "Bread & Egg")).findFirst().get();
////        assertThat(Math.round(res1BreadEgg.getScore())).isEqualTo(Math.round((3 + 2) / 2));
//        assertThat(restaurant1Score.size()).isEqualTo(3);
//    }
}