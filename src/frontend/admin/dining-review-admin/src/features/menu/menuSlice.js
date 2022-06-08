import { createSlice } from "@reduxjs/toolkit";

export const menuSlice = createSlice({
    name: "menuVisibility",
    initialState: {
        value: false
    }, 
    reducers: {
        toggle: (state, action) => {
            state.value = action.payload;
        }
    }
})

export const {toggle} = menuSlice.actions;

export default menuSlice.reducer;