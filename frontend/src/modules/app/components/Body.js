import {useSelector} from 'react-redux';
import { Route, Routes } from "react-router-dom";

import Home from "./Home";
import Test from "./Test";

import {Login, SignUp, Logout} from '../../users';

import {FindPosts, FindPostsResult, CreatePost} from '../../post';

const Body = () => {
	
  const loggedIn = useSelector(users.selectors.isLoggedIn);
	
  return (
    <div>
	    <Routes>
	       <Route path="/*" element={<Home />} />
	       <Route path="/test" element={<Test />} />
	       <Route path="/post/find-posts" element={<FindPosts/>} />
	       <Route path="/post/feed" element={<FindPostsResult/>} />
	       {loggedIn && <Route path="/users/logout" element={<Logout/>}/>}
	       {!loggedIn && <Route path="/users/login" element={<Login/>}/>}
	       {!loggedIn && <Route path="/users/signup" element={<SignUp/>}/>}
	       {loggedIn && <Route path="/post/create-post" element={<CreatePost/>}/>}
	    </Routes>
	</div>    
  );
};

export default Body;
