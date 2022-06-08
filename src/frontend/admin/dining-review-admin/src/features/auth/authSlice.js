import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import axios from "axios";
import jwt_decode from "jwt-decode";

const initialState = {
    isAuthenticated: false,
    token: '',
    status: ''
}

export const loginUser = createAsyncThunk("review/login", async(userCredentials) => {
    const response = await axios.post("http://localhost:8080/public/login", userCredentials);
    return response.data;
});

export const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
       updateState: (state, action) => {
           state.isAuthenticated = action.payload.isAuthenticated;
           state.token = action.payload.token;
       }
    },
    extraReducers: (builder) => {
        builder.addCase(loginUser.pending, (state) => {
            state.status = "connecting";
        })
        .addCase(loginUser.fulfilled,(state, action) => {

            const decodedToken = jwt_decode(action.payload.token);
            const roles = decodedToken.roles;
            
            if(!roles.includes("ADMIN")) {
                state.status = "failed";
                return;
            }

            state.isAuthenticated = true;
            state.token = action.payload.token;
            state.status = "idle";
            localStorage.setItem("token", action.payload.token);
        })
        .addCase(loginUser.rejected, (state) => {
            state.status = "failed";
        })
    }
});


export function checkAndAssignToken(isAuthenticated, dispatch) {
    if(isAuthenticated) 
        return;

    let token = localStorage.getItem("token");

    if(!token) 
        return;

    const decodedToken = jwt_decode(token);

    if(Date.now() >= decodedToken.exp * 1000)
        return;

    dispatch(updateState({isAuthenticated: true, token}))
}

export const getStatus = (state) => state.auth.status;
export const getToken = (state) => state.auth.token;
export const getAuthenticated = (state) => state.auth.isAuthenticated;
export const {updateState} = authSlice.actions
export default authSlice.reducer;