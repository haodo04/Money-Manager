import React, { useContext } from 'react'
import { AppContext } from '../context/AppContext'
import { User } from 'lucide-react';
import { SIZE_BAR_DATA } from '../assets/assets';

const Sidebar = () => {
    const {user} = useContext(AppContext);
  return (
    <div className='w-64 h-[calc(100vh-61px)] bg-white border-gray-200/5 p-5 sticky top-[61px] z-20'>
        <div className="flex flex-col items-center justify-center gap-3 mt-3 mb-7">
            {user?.profileImage ? (
                <img src={user?.profileImage || ""} alt="profile image" className='w-20 h-20 bg-slate-400 rounded-full' />
            ) : (
                <User className='w-20 h-20 text-xl'/>
            )}
            <h5 className='text-gray-950 font-medium leading-6'>{user.fullname || ""}</h5>
        </div>
        {SIZE_BAR_DATA.map((item , index) => (
            <button
                key={`menu_${index}`}
                className="w-full flex items-center gap-4 text-[15px] py-3 px-6 rounded-lg mb-3">
                <item.icon className='text-xl'/>
                {item.label}
            </button>
        ))}
    </div>
  )
}

export default Sidebar