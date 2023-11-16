import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    post: null
};

const post = (state = initialState.post, action) => {
    switch(action.type) {

        case actionTypes.FIND_POST_BY_ID_COMPLETED:
            return action.post;

        case actionTypes.CLEAR_POST:
            return initialState.post;    
            
        case actionTypes.MARK_POST_AS_EXPIRED_COMPLETED:
			return {...state, expired: action.expired };
			
		case actionTypes.RATE_POST_COMPLETED:
			return { ...state, positiveRatings: action.ratingCount.positiveCount, negativeRatings: action.ratingCount.negativeCount };	
		
		case actionTypes.UPDATE_POST_COMPLETED:
			return action.post;
			
		case actionTypes.MARK_POST_AS_VALID_COMPLETED:
			return {...state, validationDate: action.validationDate };

        default:
            return state;
    }
}



const reducer = combineReducers({
    post
});

export default reducer;