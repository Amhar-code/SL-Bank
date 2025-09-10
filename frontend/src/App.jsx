import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Redirect from './Pages/Redirect';
import Login from './Pages/Login';
import './App.css';

function App() {
  const router = createBrowserRouter([
    {
      path: "/",
      element: <Redirect />
    },
    {
      path: "/login",
      element: <Login />
    }
  ]);

  return (
    <RouterProvider router={router}></RouterProvider>
  );
}

export default App;