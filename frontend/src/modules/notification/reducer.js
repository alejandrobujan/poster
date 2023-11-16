import { combineReducers } from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
	notifications: [],
};

const notifications = (state = initialState.notifications, action) => {

	switch (action.type) {

		case actionTypes.FIND_NOTIFICATIONS_COMPLETED:
			return action.notifications;

		case actionTypes.SET_VIEWED:
			return [...(state).map(n => (n.notificationId === action.notificationId ? { ...n, notificationViewed: true } : n))];

		default:
			return state;

	}

}


const reducer = combineReducers({
	notifications
});

export default reducer;