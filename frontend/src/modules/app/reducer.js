import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    error: null,
    firstSearch: true
};

const error = (state = initialState.error, action) => {

    switch (action.type) {

        case actionTypes.ERROR:
            return action.error;

        default:
            return state;

    }

};    

const firstSearch = (state = initialState.firstSearch, action) => {

    switch (action.type) {

        case actionTypes.SET_FIRST_SEARCH:
            return action.firstSearch;

        default:
            return state;

    }

};    

const reducer = combineReducers({
    error,
    firstSearch
});

export default reducer;