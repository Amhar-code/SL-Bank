import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import api from "../api/api";

const initialState = {
    user: {}, 
    status: 'IDLE',
}

export const registerUser = createAsyncThunk("user/register", async (userDetails) => {
    try {
            const{data, error} = await api.post('/user/register', userDetails)
            if(error) throw error;
            return data
        } catch(err) {
            console.log(err.message)
            throw err
        }
    }
)

export const userSlice = createSlice({
    name: 'user',
    initialState,
    reducers: {
        resetStatus: (state) => {
            state.status = 'IDLE'
        }
    },
    extraReducers(builder) {
        builder.addCase(registerUser.pending, (state) => {
                    state.status = 'PENDING'
                })
        builder.addCase(registerUser.fulfilled, (state, action) => {
                    state.status = 'SUCCESS'
                    state.user = action.payload
                })
        builder.addCase(registerUser.rejected, (state, action) =>{ 
                    state.status = 'FAILED'
                })
    }
})

export const { resetStatus } = userSlice.actions
export const fetchedUser = state => state.user.user
export const fetchUserStatus = state => state.user.status
export default userSlice.reducer;