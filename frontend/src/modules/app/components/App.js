import {useEffect} from 'react';
import {useDispatch} from 'react-redux';

import Header from './Header';
import Body from './Body';
import Footer from './Footer';
import users from '../../users';
import post from '../../post';

const App = () => {
    const dispatch = useDispatch();
    useEffect(() => {

        dispatch(users.actions.tryLoginFromServiceToken(
            () => dispatch(users.actions.logout())));
        dispatch(post.actions.findPosts({page: 0}));
        dispatch(post.actions.findAllCategories());
    });
    
    return(
		<div>
			<Header/>
			<Body/>
			<Footer/>
		</div>
	);
};

export default App;
