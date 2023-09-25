import { useEffect } from 'react';
import { useDispatch } from 'react-redux';
import { Feed } from '../../post';
import post from '../../post';


const Home = () => {

	const dispatch = useDispatch();
	useEffect(() => {
		dispatch(post.actions.findPosts({ page: 0 }));
	});

	return (
		<div className="text-center">
			<Feed />
		</div>
	)
};

export default Home;
