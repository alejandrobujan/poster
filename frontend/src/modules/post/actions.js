import backend from '../../backend';
import * as actionTypes from './actionTypes';

export const createPost = (title, description, url, price, categoryId, images, 
							onSuccess, onErrors) => dispatch => 
	backend.postService.createPost(title, description, url, price, categoryId, images, onSuccess, onErrors);


const findPostsCompleted = postSearch => ({
	type: actionTypes.FIND_POSTS_COMPLETED,
	postSearch
});


const clearPostSearch = () => ({
	type: actionTypes.CLEAR_POST_SEARCH
});
	
	
export const findPosts = criteria => dispatch => {
	
	dispatch(clearPostSearch());
	backend.postService.findPosts(criteria, result =>
		dispatch(findPostsCompleted({criteria, result}))
	);
	
};

export const previousFindPostsResultPage = criteria =>
    findPosts({page: criteria.page-1});

export const nextFindPostsResultPage = criteria =>
    findPosts({page: criteria.page+1});
