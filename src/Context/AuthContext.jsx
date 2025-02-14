import React, { createContext, useState } from 'react'

const AuthContext = createContext();
function AuthContextProvider({children}) {
    const [isLogin,setIsLogin] = useState(false);
    return (
      <>
    <AuthContext.Provider value={{isLogin,setIsLogin}}>
    {children}
    </AuthContext.Provider>
    </>
  )
}

export {AuthContext,AuthContextProvider}