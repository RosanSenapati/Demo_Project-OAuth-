import React, { useContext, useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { useNavigate } from 'react-router-dom'
import { useParams } from 'react-router-dom'
import {toast,ToastContainer } from 'react-toastify'
import './Home.css';
import {AuthContext} from '../../Context/AuthContext'
export default function Home() {
  const {isLogin} = useContext(AuthContext)
  const {name} = useParams();
  const navigate = useNavigate();
  console.log(name);
const flag = true;
  useEffect(()=>{
    if(isLogin)
    {
      toast.success('Logged in Successfully')
    }
    else{
      navigate('/')
    }
  },[isLogin])

  return (
  
    // <div className="container title bg-white d-flex justify-content-center flex-column  ">
    //   <div><div><p className='fs-1 text-center'>Message</p></div><p className='fs-5 text-dark'>Welcome Page <span className='text-primary'>{name}</span></p></div>
    //   </div>
    <div className="wrapper shadow-lg ">
    <div className="title"><span>Message</span></div>
    <div className='fs-3 p-4'><p className='text-center '>Welcome <span className='text-primary'>{name}</span> </p></div>
    <ToastContainer/>
  </div>
    
  )
}