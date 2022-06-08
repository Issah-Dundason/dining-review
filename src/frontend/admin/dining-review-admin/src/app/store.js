import { configureStore } from "@reduxjs/toolkit";
import menuReducer from "../features/menu/menuSlice";
import authReducer from "../features/auth/authSlice";
import foodReducer from "../features/food/foodSlice";

export default configureStore({
    reducer: {
        menuVisibility: menuReducer,
        auth: authReducer,
        food: foodReducer
    },
})