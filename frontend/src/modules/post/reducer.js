import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    categories: null,
    postSearch: null,
    post: null
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

const post = (state = initialState.post, action) => {
    switch(action.type) {

        case actionTypes.FIND_POST_BY_ID_COMPLETED:
            return action.post;

        case actionTypes.CLEAR_POST:
            return initialState.post;    
            
        case actionTypes.MARK_POST_AS_EXPIRED_COMPLETED:
			return {...state, expired: action.expired };

        default:
            return state;
    }
}



const reducer = combineReducers({
    categories,
    postSearch,
    post
});

export default reducer;