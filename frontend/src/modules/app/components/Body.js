import { useSelector } from 'react-redux';
import { Route, Routes } from "react-router-dom";

import Home from "./Home";

import { Login, SignUp, Logout, UpdateProfile, ProfileDetail, ChangePassword } from '../../users';
import { PageNotFound, Forbidden } from '../../common';
import users from '../../users';

import {CreatePost, PostDetails, UpdatePost} from '../../post';

const Body = () => {
	
  const loggedIn = useSelector(users.selectors.isLoggedIn);
	
  return (
    <div style={{"marginBottom": "100px"}}>
	    <Routes>
	       <Route path="/*" element={<Home />} />
		   <Route path="/post/post-details/:id" element={<PostDetails/>}/>
		   <Route path="/not-found" element={<PageNotFound/>}/>
		   <Route path="/forbidden" element={<Forbidden/>}/>
	       {loggedIn && <Route path="/users/logout" element={<Logout/>}/>}
	       {!loggedIn && <Route path="/users/login" element={<Login/>}/>}
	       {!loggedIn && <Route path="/users/signup" element={<SignUp/>}/>}
	       {loggedIn && <Route path="/post/create-post" element={<CreatePost/>}/>}
	       {loggedIn && <Route path="/users/profile-detail" element={<ProfileDetail/>}/>}
	       {loggedIn && <Route path="/users/update-profile" element={<UpdateProfile />} />}
	       {loggedIn && <Route path="/users/change-password" element={<ChangePassword/>}/>}
	       {loggedIn && <Route path="/post/post-update/:id" element={<UpdatePost/>}/>}
	    </Routes>
	</div>    
  );
};

export default Body;
