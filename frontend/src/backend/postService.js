import {
  fetchConfig,
  appFetch
} from "./appFetch";

export const createPost = (title, description, url, price, categoryId, images, onSuccess, onErrors) => {
	appFetch("/posts/post", fetchConfig("POST", {title, description, url, price, categoryId, images}),
	onSuccess, onErrors);
};

export const findPosts = ({page}, onSuccess) => {
	appFetch(`/posts/feed?page=${page}`, fetchConfig("GET"), onSuccess);
};
