import { createContext, useState } from 'react'
import LoginSignUp from './pages/LoginSignup/LoginSignUp'
import SignupPage from './pages/SignupPage/SignupPage'
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './pages/HomePage/Home';

const AuthContext = createContext();

function App() {
  const [isLogin,setIsLogin] = useState(false);
const router = createBrowserRouter([
  {
    path:'/',
    element: <LoginSignUp/>
  },
  {
    path:'/signup',
    element:<SignupPage/>
  },
  {
    path:'/home/:name',
    element:<Home/>
  }
])
  return (
    <>
  
    <AuthContext.Provider value={{isLogin, setIsLogin}}>
    <RouterProvider router={router}/>
    </AuthContext.Provider>
    </>
  )
}

export default App
export {AuthContext} 
