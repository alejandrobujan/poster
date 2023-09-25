import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    categories: null,
    postSearch: null
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

const reducer = combineReducers({
    categories,
    postSearch
});

export default reducer;