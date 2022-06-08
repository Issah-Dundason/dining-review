import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";


export const saveFood = createAsyncThunk("review/save-food", async (food) => {
    let token = localStorage.getItem("token");

    await axios.post("http://localhost:8080/admin/food/save", food, {
        headers: {
            Authorization: `Bearer ${token}`
        }
    });

});

export const getSavedFoods = createAsyncThunk("review/foods", async () => {
    console.log("Hello");
    const response = await axios.get("http://localhost:8080/public/foods");
    return response.data;
});

const initialState = {
    dishes: [],
    fetchingFoodsStatus: '',
    savingStatus: '',
    deletingStatus: ''
}

export const foodSlice = createSlice({
    name: "foodSlice",
    initialState, 
    extraReducers: (builder) => {
        builder.addCase(getSavedFoods.pending, (state) => {
            state.fetchingFoodsStatus = "connecting";
        })
        .addCase(getSavedFoods.rejected, (state) => {
            state.fetchingFoodsStatus = "failed";
        })
        .addCase(saveFood.pending, (state) => {
            state.savingStatus = "connecting";
        })
        .addCase(saveFood.rejected, (state) => {
            state.savingStatus = "failed";
        })
        .addCase(saveFood.fulfilled, (state) => {
            state.savingStatus = "done";
        })
        .addCase(getSavedFoods.fulfilled, (state, action) => {
            state.fetchingFoodsStatus = 'done'
            state.dishes = action.payload;
        })
    }
})

export const getDishes = (state) => state.food.dishes;
export const getFetchingStatus = (state) => state.food.fetchingFoodsStatus;
export const getSavingStatus = (state) => state.food.savingStatus;
export default foodSlice.reducer;