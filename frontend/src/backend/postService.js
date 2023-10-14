import {
	fetchConfig,
	appFetch
} from "./appFetch";

export const findAllCategories = (onSuccess) =>
	appFetch('/posts/categories', fetchConfig("GET"), onSuccess);

export const createPost = (title, description, url, price, categoryId, images, type, properties, onSuccess, onErrors) => {
	appFetch("/posts/post", fetchConfig("POST", { title, description, url, price, categoryId, images, type, properties }),
		onSuccess, onErrors);
};

export const findPosts = ({ page }, onSuccess) => {
	appFetch(`/posts/feed?page=${page}`, fetchConfig("GET"), onSuccess);
};

export const findPostById = (id, onSuccess) => {
    appFetch(`/posts/postDetail/${id}`, fetchConfig("GET"), onSuccess);
};
export const updatePost = (userId, id, title, description, url, price, categoryId, images, type, properties, onSuccess, onErrors) => {
	appFetch(`/posts/postUpdate/${id}`, fetchConfig("PUT", {userId, title, description, url, price, categoryId, images, type, properties}),
	onSuccess, onErrors);
};