import { combineReducers } from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
	notifications: null,
};

const notifications = (state = initialState.notifications, action) => {

	switch (action.type) {

		case actionTypes.FIND_NOTIFICATIONS_COMPLETED:
			return action.notifications;
			
		default:
			return state;

	}

}


const reducer = combineReducers({
	notifications
});

export default reducer;