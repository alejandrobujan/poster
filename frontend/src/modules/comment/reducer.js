import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    comments: {
        page: 0,
        elems: {
            items: [],
            existMoreItems: false
        }
    }
};

const comments = (state = initialState.comments, action) => {
    switch(action.type) {

        case actionTypes.FIND_COMMENTS_COMPLETED:
            return action.result;
            
        case actionTypes.CLEAR_COMMENTS:
            return initialState.comments;   

        default:
            return state;
    }
}



const reducer = combineReducers({
    comments
});

export default reducer;