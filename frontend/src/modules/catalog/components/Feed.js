import { useSelector, useDispatch } from 'react-redux';
import Posts from './Posts';
import * as selectors from '../selectors';
import { Pager } from '../../common';
import * as actions from '../actions';
import {SearchBar} from '../../catalog';
import RefreshFeed from './RefreshFeed';

const Feed = () => {
	const posts = useSelector(selectors.getPostSearch);
	const requestRefresh = useSelector(selectors.getRequestRefresh);
	const dispatch = useDispatch();
	
	if (!posts) {
		return null;
	}

	return (
		<div>
			<SearchBar />
			{requestRefresh && 
				<RefreshFeed/>
			}
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