import {
	fetchConfig,
	appFetch
} from "./appFetch";

export const createPost = (title, description, url, price, categoryId, images, type, properties, onSuccess, onErrors) => {
	appFetch("/posts/post", fetchConfig("POST", { title, description, url, price, categoryId, images, type, properties }),
		onSuccess, onErrors);
};

export const deletePost = (id, onSuccess, onErrors) => {
	appFetch(`/posts/post/${id}`, fetchConfig("DELETE"), onSuccess, onErrors);
};

export const updatePost = (post, onSuccess, onErrors) => {
	appFetch(`/posts/post/${post.id}`, fetchConfig("PUT", post),
	onSuccess, onErrors);
};

export const maskPostAsExpired = (id, expired, onSuccess, onErrors) => {
	appFetch(`/posts/post/${id}/markAsExpired`, fetchConfig("POST", {expired}), onSuccess, onErrors);
};

