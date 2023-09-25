import {useSelector} from 'react-redux';
import { Route, Routes } from "react-router-dom";

import Home from "./Home";
import Test from "./Test";

import {Login, SignUp, Logout} from '../../users';
import users from '../../users';

import {CreatePost} from '../../post';

const Body = () => {
	
  const loggedIn = useSelector(users.selectors.isLoggedIn);
	
  return (
    <div style={{"marginBottom": "100px"}}>
	    <Routes>
	       <Route path="/poster/*" element={<Home />} />
	       <Route path="/poster/test" element={<Test />} />
	       {loggedIn && <Route path="/poster/users/logout" element={<Logout/>}/>}
	       {!loggedIn && <Route path="/poster/users/login" element={<Login/>}/>}
	       {!loggedIn && <Route path="/poster/users/signup" element={<SignUp/>}/>}
	       {loggedIn && <Route path="/poster/post/create-post" element={<CreatePost/>}/>}
	    </Routes>
	</div>    
  );
};

export default Body;
