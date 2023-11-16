import * as actionTypes from './actionTypes';
import backend from '../../backend';

export const findNotifications = () => dispatch => {

    backend.notificationService.findNotifications(
        notifications => dispatch(findNotificationsCompleted(notifications)));

}
const findNotificationsCompleted = notifications => ({
    type: actionTypes.FIND_NOTIFICATIONS_COMPLETED,
    notifications
});

export const markAsViewed = id => dispatch => {
	backend.notificationService.markAsViewed(id, () => dispatch(setViewed(id)))
}

export const setViewed = notificationId => ({
    type: actionTypes.SET_VIEWED,
    notificationId
});