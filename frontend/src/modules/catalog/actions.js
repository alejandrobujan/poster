import * as actionTypes from './actionTypes';
import backend from '../../backend';
import * as selectors from './selectors';

const findAllCategoriesCompleted = categories => ({
	type: actionTypes.FIND_ALL_CATEGORIES_COMPLETED,
	categories
});

export const findAllCategories = () => (dispatch, getState) => {

	const categories = selectors.getCategories(getState());

	if (!categories) {

		backend.catalogService.findAllCategories(
			categories => dispatch(findAllCategoriesCompleted(categories))
		);

	}

}
const findPostsCompleted = postSearch => ({
    type: actionTypes.FIND_POSTS_COMPLETED,
    postSearch
});

export const findPosts = criteria => dispatch => {

    dispatch(clearPostSearch());
    backend.catalogService.findPosts(criteria,
        result => dispatch(findPostsCompleted({criteria, result})));

}

const clearPostSearch = () => ({
    type: actionTypes.CLEAR_POST_SEARCH
});

export const previousFindPostsResultPage = criteria =>
	findPosts({...criteria, page: criteria.page - 1 });

export const nextFindPostsResultPage = criteria =>
	findPosts({...criteria, page: criteria.page + 1 });
	
export const setKeywords = keywords => ({
    type: actionTypes.SET_KEYWORDS, 
    keywords
});

export const setCategoryId = categoryId => ({
    type: actionTypes.SET_CATEGORY_ID, 
    categoryId
});

export const setType = postType => ({
    type: actionTypes.SET_TYPE, 
    postType
});

export const setMinPrice = minPrice => ({
    type: actionTypes.SET_MIN_PRICE, 
    minPrice
});

export const setMaxPrice = maxPrice => ({
    type: actionTypes.SET_MAX_PRICE, 
    maxPrice
});

export const setDate = date => ({
    type: actionTypes.SET_DATE, 
    date
});

export const setExpired = expired => ({
    type: actionTypes.SET_EXPIRED, 
    expired
});

export const setSortParam = sortParam => ({
    type: actionTypes.SET_SORT_PARAM, 
    sortParam
});

export const setSortOrder = sortOrder => ({
    type: actionTypes.SET_SORT_ORDER, 
    sortOrder
});

export const subscribe = () => dispatch => {
    backend.postService.subscribe(
        () => dispatch(setRequestRefresh(true))
    );
}

export const setRequestRefresh = requestRefresh => ({
    type: actionTypes.SET_REQUEST_REFRESH, 
    requestRefresh
});