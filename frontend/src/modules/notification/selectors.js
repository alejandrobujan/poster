const getModuleState = state => state.notification;

export const getNotifications = state =>
    getModuleState(state).notifications;