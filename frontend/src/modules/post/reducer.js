import * as actionTypes from './actionTypes';

const initialState = {
    postSearch: null
};

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

export default postSearch;