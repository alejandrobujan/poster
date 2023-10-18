import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { Feed } from '../../catalog';
import catalog from '../../catalog';


const Home = () => {

	const dispatch = useDispatch();
	useEffect(() => {
		dispatch(catalog.actions.findPosts({ keywords: '', filters: {}, page: 0 }));
	});

	return (
		<div className="text-center">
			<Feed />
		</div>
	)
};

export default Home;
