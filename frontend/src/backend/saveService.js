import {
	fetchConfig,
	appFetch
} from "./appFetch";

export const savePost = (postId, onSuccess, onErrors) => {
	appFetch(`/saves/post/${postId}`, fetchConfig("POST"), onSuccess, onErrors);
};

export const isPostSavedByUser = (postId, onSuccess, onErrors) => {
	appFetch(`/saves/post/${postId}/save`, fetchConfig("GET"), onSuccess, onErrors);
};

export const unSavePost = (postId, onSuccess, onErrors) => {
	appFetch(`/saves/post/${postId}`, fetchConfig("DELETE"), onSuccess, onErrors);
};
