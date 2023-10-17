import {
	fetchConfig,
	appFetch
} from "./appFetch";

export const ratePostPositive = (id, onSuccess, onErrors) => {
	appFetch(`/posts/post/${id}/ratePositive`, fetchConfig("POST"), onSuccess, onErrors);
};

export const ratePostNegative = (id, onSuccess, onErrors) => {
	appFetch(`/posts/post/${id}/rateNegative`, fetchConfig("POST"), onSuccess, onErrors);
};