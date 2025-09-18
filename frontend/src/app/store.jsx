import { configureStore } from '@reduxjs/toolkit';
import userReducer from '../features/user/userSlice';
import pageReducer from '../features/page/pageSlice'; 
import accountReducer from '../features/account/accountSlice';  

export const store = configureStore({
  reducer: {
    user: userReducer,
    pages: pageReducer, 
    account: accountReducer,  
  },
});