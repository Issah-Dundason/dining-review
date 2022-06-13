## Dining Review :zzz: :memo: :surfer:

The app deals with the ff entities:
- User
- Review
- Restaurant
- Food

A Restaurant will have a list of food. Each food will be rated by users on a scale of 1 - 5, with 5 being the best. The score of a particular food served by a particular restaurant is the average of all submitted scores for that food.

A restaurant will also have an overall score, which will be the average across all the submitted scores across all the foods for that restaurant.


## Entities

###         Admin
- [x] Create a restaurant
- [x] Update a restaurant
- [x] Create food
- [x] Update food
- [x]  Change the status of a review (pending to approved or pending to rejected)


### User
- [x] Register
- [x] Update data
- [x] Rate the food served by a restaurant
- [ ] Delete a Review

### Endpoints

| Method  | URL  | Uses Authentication  | Request Body| Response Body|
|:-:|:-:|:-:|:-|:-|
|POST|/public/login | ❎ | Login details | A json object with a token that can be used to access some parts of the app
| POST  |/admin/food/save   | ✅  |A json object with the details of  a food|Returns the id of the saved food |
| PUT  |/admin/food/{{id}}/update  | ✅  | A json object with the details of  a food|   |
| POST  |/admin/restaurant/save | ✅ | A json object with the details of a restaurant| Returns an object that contains the id of the saved restaurant|
| PUT |/admin/restaurant/{{id}}/update   | ✅  | A json object with the details of a restaurant|   |
|  GET |/public/foods| ❎  |   | Returns a list of food in the system  |
| GET  |/public/restaurants  | ❎  |   | Returns a list of restaurants in the system  |
|  POST |/public/register | ❎  | A json object with the details of a user  | Returns an object that contains the id and display name of the saved user  |
| PUT  |/user/update | ✅  | A json object with the details of a user  |   |
| GET  |/user/{{display-name}}  | ✅  |   | Returns a json object with the supplied display name  |
|POST   |/review/save | ✅  |  A json object with the details | Returns the saved review  |
| PUT  |/review/update | ✅ |A json object representing a review.    |   |
| GET  |/public/restaurants/{{id}}/food-scores  | ❎  |   | Returns a list of objects.Each object has the name of a food served by the restaurant, the approved score(its rating on a scale of 1 - 5) and the number of users who took part in rating the food  |

### Example
Login details
```
   {
    "displayName": "user1",
    "password": "user1password"
}
```

Login response 
```
 {
    "token": "eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJBZG1pbiIsImlhdCI6MTY1NDU1Nzk4MCwicm9sZXMiOlsiQURNSU4iLCJVU0VSIl0sImV4cCI6MTY1NDU2MTU4MH0.M2PmS79ti3L2HL1fIKF36Ng9JkLPUamApV6AsC3knaySIW_Ly5f1PlkrFJlogbsJ"
 }

```

Json Object to send when creating food or updating food
``` 
    {
    "name": "kenkey"
    }
```

Json Object to send when creating a restaurant or updating a restaurant

foodIds = the ids of food served by a restaurant

```
{
   "name": "Restaurant1",
   "zipCode": "1238",
   "city": "City1",
   "state": "state1",
   "foodIds": [2]
}

```

Json Object to send when creating a review or updating a review
```
{
  "commentary": "Comment",
  "restaurantId": 3,
  "foodRating" : [
	{
	   "rate": 3,
        "foodId": 2
       }
   ]
}
```

Json object to send when registering a user or updating a user.

User displayName can't be changed but it should be provided when updating a user
```
{
   "displayName": "user1",
   "city": "City1",
   "state": "state1",
   "zipCode": "1234",
   "password": "user1password",
    "foodIds": []
}

```