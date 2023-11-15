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