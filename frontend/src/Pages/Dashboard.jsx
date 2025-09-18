import React from "react";
import Header from "../components/dashboard/Header";
import NavBar from "../components/dashboard/Navbar";
import { Route, Routes } from "react-router-dom";
import Home from "./dashboard/Home"
import Account from "./dashboard/Account"
import Payment from "./dashboard/Payment"
import Transaction from "./dashboard/Transaction"
import Card from "./dashboard/Card"
import Settings from "./dashboard/Settings"
import Profile from "./dashboard/Profile"
import Convert from "./dashboard/Convert"

const Dashboard = () => {
  return (
    <main className="font-roboto w-full overflow-x-hidden bg-gradient-to-r from-gray-300 to-white-500 min-h-screen">
        <div className="flex flex-row w-full min-h-screen relative">
          <NavBar />
          <div className="flex-1 flex flex-col ml-0 lg:ml-[300px]">
            <Header />
            <section className='z-10 relative flex-1 p-4 sm:p-6 w-full overflow-auto mt-16'>
              <Routes>
                <Route path='/' element={<Home />}/>
                <Route path='/accounts' element={<Account />}/>
                <Route path='/payments' element={<Payment />}/>
                <Route path='/transactions' element={<Transaction />}/>
                <Route path='/cards' element={<Card />}/>
                <Route path='/settings' element={<Settings />}/>
                <Route path='/profile' element={<Profile />}/>
                <Route path='/convert' element={<Convert />}/>
              </Routes>
            </section>
          </div>
        </div>
      </main>
  )
}

export default Dashboard