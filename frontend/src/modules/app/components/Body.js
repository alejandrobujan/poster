import {useSelector} from 'react-redux';
import { Route, Routes } from "react-router-dom";

import Home from "./Home";
import Test from "./Test";

import {Login, SignUp, Logout, ProfileDetail} from '../../users';
import users from '../../users';

import {CreatePost} from '../../post';

const Body = () => {
	
  const loggedIn = useSelector(users.selectors.isLoggedIn);
	
  return (
    <div style={{"marginBottom": "100px"}}>
	    <Routes>
	       <Route path="/*" element={<Home />} />
	       <Route path="/test" element={<Test />} />
	       {loggedIn && <Route path="/users/logout" element={<Logout/>}/>}
	       {!loggedIn && <Route path="/users/login" element={<Login/>}/>}
	       {!loggedIn && <Route path="/users/signup" element={<SignUp/>}/>}
	       {loggedIn && <Route path="/post/create-post" element={<CreatePost/>}/>}
	       {loggedIn && <Route path="/users/profile-detail" element={<ProfileDetail/>}/>}
	    </Routes>
	</div>    
  );
};

export default Body;
