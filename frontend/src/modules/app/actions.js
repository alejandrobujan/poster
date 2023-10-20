import * as actionTypes from './actionTypes';

export const error = error => ({
    type: actionTypes.ERROR,
    error
});

export const setFirstSearch = firstSearch => ({
    type: actionTypes.SET_FIRST_SEARCH,
    firstSearch
});