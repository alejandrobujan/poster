import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Feed } from '../../catalog';
import catalog from '../../catalog';
import * as selectors from '../selectors';
import * as actions from '../actions';


const Home = () => {

	const dispatch = useDispatch();
	const firstSearch = useSelector(selectors.getFirstSearch);

	useEffect(() => {
		if (firstSearch) {
			dispatch(catalog.actions.findPosts({ keywords: '', filters: {}, page: 0 }));
			dispatch(actions.setFirstSearch(false));
		}
	}, [dispatch, firstSearch]);

	return (
		<div className="text-center">
			<Feed />
		</div>
	)
};

export default Home;
