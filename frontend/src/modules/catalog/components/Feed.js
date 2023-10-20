import { useSelector, useDispatch } from 'react-redux';
import Posts from './Posts';
import * as selectors from '../selectors';
import { Pager } from '../../common';
import * as actions from '../actions';
import {SearchBar} from '../../catalog';

const Feed = () => {
	const posts = useSelector(selectors.getPostSearch);
	const dispatch = useDispatch();
	
	if (!posts) {
		return null;
	}

	return (
		<div>
			<SearchBar />
			<Posts posts={posts.result.items} />
			<Pager
				back={{
					enabled: posts.criteria.page >= 1,
					onClick: () => dispatch(actions.previousFindPostsResultPage(posts.criteria))
				}}
				next={{
					enabled: posts.result.existMoreItems,
					onClick: () => dispatch(actions.nextFindPostsResultPage(posts.criteria))
				}} />
		</div>
	);

}

export default Feed;