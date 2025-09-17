import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import { Provider } from 'react-redux';
import Redirect from './Pages/Redirect';
import Login from './Pages/Login';
import './App.css';
import Register from './Pages/Register';
import { store } from './app/store';
import RegisterSuccessful from './Pages/RegisterSuccessful'

function App() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Redirect />
    },
    {
      path: "/login",
      element: <Login />
    },
    {
      path: "/register",
      element: <Register />
    },
    {
      path: "/successful",  // Fixed typo: was "/sucessful"
      element: <RegisterSuccessful />
    }
  ]);

  return (
    <Provider store={store}>
      <RouterProvider router={router} />
    </Provider>
  );
}

export default App;