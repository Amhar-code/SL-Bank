import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Redirect from './Pages/Redirect';
import Login from './Pages/Login';
import './App.css';
import Register from './Pages/Register';

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
    }
  ]);

  return (
    <RouterProvider router={router}></RouterProvider>
  );
}

export default App;