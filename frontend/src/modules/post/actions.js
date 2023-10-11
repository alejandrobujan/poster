import backend from '../../backend';
import * as actionTypes from './actionTypes';
import * as selectors from './selectors';

const findAllCategoriesCompleted = categories => ({
	type: actionTypes.FIND_ALL_CATEGORIES_COMPLETED,
	categories
});

export const findAllCategories = () => (dispatch, getState) => {

	const categories = selectors.getCategories(getState());

	if (!categories) {

		backend.postService.findAllCategories(
			categories => dispatch(findAllCategoriesCompleted(categories))
		);

	}

}

export const createPost = (title, description, url, price, categoryId, images, type, properties,
	onSuccess, onErrors) => dispatch =>
		backend.postService.createPost(title, description, url, price, categoryId, images, type, properties, onSuccess, onErrors);

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
		dispatch(findPostsCompleted({ criteria, result }))
	);

};

export const previousFindPostsResultPage = criteria =>
	findPosts({ page: criteria.page - 1 });

export const nextFindPostsResultPage = criteria =>
	findPosts({ page: criteria.page + 1 });
