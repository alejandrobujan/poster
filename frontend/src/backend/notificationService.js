import {
	fetchConfig,
	appFetch
} from "./appFetch";

export const findNotifications = (onSuccess) => {
	appFetch(`/notifications/notifications`, fetchConfig("GET"), onSuccess);
};

export const markAsViewed = (id, onSuccess) => {
	appFetch(`/notifications/${id}/view`, fetchConfig("POST"), onSuccess);
};