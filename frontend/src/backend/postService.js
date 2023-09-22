import {
  fetchConfig,
  appFetch,
  setServiceToken,
  getServiceToken,
  removeServiceToken,
  setReauthenticationCallback,
} from "./appFetch";

export const createPost = (title, description, url, price, categoryId, images, onSuccess, onErrors) => {
	appFetch("/posts/post", fetchConfig("POST", {title, description, url, price, categoryId, images}),
	onSuccess, onErrors);
};
