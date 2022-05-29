## Dining Review :zzz: :memo: :surfer:

The app deals with the ff entities:
- User
- Review
- Restaurant
- Food

A Restaurant will have a list of food. Each food will be rated by users on a scale of 1 - 5, with 5 being the best. The score of a food for a particular restaurant is the average of all submitted scores for that food.

A restaurant will also have an overall score, which will be the average across all the submitted scores across all the foods for that restaurant.


## Entities

###         Admin
- [x] Create a restaurant
- [x] Update a restaurant
- [x] Create food
- [x] Update food
- []  Change the status of a review (pending to approved or pending to rejected)


### User
- [x] Register
- [x] Update data
- [x] Rate the food served by a restaurant
- [] Delete a Review

### Endpoints

</table>
<tr>
    <td>Method</td>
    <td>URL</td>
    <td>Uses authentication</td>
    <td>Request Body</td>
    <td>Response Body</td>
</tr>
<tr>
    <td>POST</td>
    <td>http://<HOST>:<PORT>/admin/food/save</td>
    <td>✅</td>
    <td><pre>``` {
    "name": "kenkey"
}```</pre></td>
    <td>
    <pre>
    ```{
        "id": 1
    }```
    </pre>
    </td>
</tr>
</table>


