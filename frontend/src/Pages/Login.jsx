import React, { useState } from 'react';
import { FaPiggyBank, FaChevronRight } from 'react-icons/fa';
import InputComponent from '../components/inputComponent';

function Login() {
  const [user, setUser] = useState({
    username: "",
    password: ""
  });

  const handleInputChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };
  
  const enableButton = user.username.length > 0 && user.password.length > 0;
  
  const login = () => {
    alert("Login Successful");
  };
  
  const disablestyle = !enableButton ? "bg-blue-300 hover:bg-blue-300" : "hover:bg-opacity-90 bg-blue-500";

  return (
    <main className='font-roboto flex flex-col w-screen sm:w-full lg:w-screen 
    md:w-screen h-screen justify-center items-center bg-gradient-to-r
    from-gray-300 to-white-500 relative'>
      <section className='flex flex-col justify-center p-2 w-full
      gap-8 items-center sm:w-3/5 xl:w-2/5 sm:p-6'>
        <h1 className='text-xl font-bold flex flex-col items-center'>
          <FaPiggyBank size={40}/> 
          SL-Bank
        </h1>
        <form className='flex flex-col gap-4 bg-white p-5 rounded-md 
        sm:min-w-[500px] flex-1 w-full'>
          <h2 className='text-lg text-black'>Login in to your account</h2>
          <InputComponent inputProp={{ name: user.username, type: "email", 
            label: "Email", field: "username", placeholder: "Enter Your Email", 
            handleInputChange }}/>
          <InputComponent inputProp={{ name: user.password, type: "password", 
            label: "Password", field: "password", placeholder: "Enter Your Password", 
            handleInputChange }}/>
          <a className='underline text-blue-500 flex items-center' href='/'>
            Forgot Password <FaChevronRight />
          </a>
          <button 
            disabled={!enableButton} 
            onClick={login} 
            type="button" 
            className={`${disablestyle} p-2 rounded-xl text-white font-bold mt-2 t
            ransition-all`}>
            LOGIN
          </button>
        </form>
        <p>Don't have an account? <a href='/signup' className='underline 
        text-blue-500'>Create One</a></p>
      </section>
    </main>
  );
}

export default Login;