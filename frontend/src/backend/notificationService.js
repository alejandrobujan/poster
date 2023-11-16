import {
	fetchConfig,
	appFetch
} from "./appFetch";

export const findNotifications = (onSuccess) => {
	appFetch(`/notifications/notifications`, fetchConfig("GET"), onSuccess);
};
