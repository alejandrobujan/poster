import { combineReducers } from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
	categories: null,
	postSearch: null,
	searchParams: {
		keywords: "",
		filters: {
			categoryId: "",
			type: "",
			price: {
				gte: 0,
				lte: 1000000
			},
			date: "",
			expired: false,
			sortParam: "creationDate",
			sortOrder: "DESC"
		}
	},
	requestRefresh: false
};

const categories = (state = initialState.categories, action) => {

	switch (action.type) {

		case actionTypes.FIND_ALL_CATEGORIES_COMPLETED:
			return action.categories;

		default:
			return state;

	}

}

const postSearch = (state = initialState.postSearch, action) => {

	switch (action.type) {

		case actionTypes.FIND_POSTS_COMPLETED:
			return action.postSearch;

		case actionTypes.CLEAR_POST_SEARCH:
			return initialState.postSearch;

		default:
			return state;

	}

}

const searchParams = (state = initialState.searchParams, action) => {

	switch (action.type) {

		case actionTypes.SET_KEYWORDS:
			return { ...state, keywords: action.keywords };

		case actionTypes.SET_CATEGORY_ID:
			return { ...state, filters: { ...state.filters, categoryId: action.categoryId } };

		case actionTypes.SET_TYPE:
			return { ...state, filters: { ...state.filters, type: action.postType } };

		case actionTypes.SET_MIN_PRICE:
			return { ...state, filters: { ...state.filters, price: { ...state.filters.price, gte: action.minPrice } } };

		case actionTypes.SET_MAX_PRICE:
			return { ...state, filters: { ...state.filters, price: { ...state.filters.price, lte: action.maxPrice } } };

		case actionTypes.SET_DATE:
			return { ...state, filters: { ...state.filters, date: action.date } };

		case actionTypes.SET_EXPIRED:
			return { ...state, filters: { ...state.filters, expired: action.expired } };

		case actionTypes.SET_SORT_PARAM:
			return { ...state, filters: { ...state.filters, sortParam: action.sortParam } };

		case actionTypes.SET_SORT_ORDER:
			return { ...state, filters: { ...state.filters, sortOrder: action.sortOrder } };

		default:
			return state;

	}

}

const requestRefresh = (state = initialState.requestRefresh, action) => {

	switch (action.type) {

		case actionTypes.SET_REQUEST_REFRESH:
			return action.requestRefresh;

		default:
			return state;

	}

}

const reducer = combineReducers({
	categories,
	postSearch,
	searchParams,
	requestRefresh
});

export default reducer;