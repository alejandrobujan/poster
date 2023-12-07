import {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import { v4 as uuidv4 } from 'uuid';

import Header from './Header';
import Body from './Body';
import Footer from './Footer';
import users from '../../users';
import catalog from '../../catalog';

import "./App.css";

const App = () => {
    const dispatch = useDispatch();
    useEffect(() => {

        let memberId = sessionStorage.getItem('memberId');

        if (!memberId) {
            memberId = uuidv4(); 
            sessionStorage.setItem('memberId', memberId); 
        }

        dispatch(users.actions.tryLoginFromServiceToken(
            () => dispatch(users.actions.logout())));
        dispatch(catalog.actions.findAllCategories());
        dispatch(catalog.actions.subscribe());
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
