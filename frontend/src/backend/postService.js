import {
	fetchConfig,
	appFetch
} from "./appFetch";
import { config } from "../config/constants"; 

let eventSource;

export const createPost = (title, description, url, price, categoryId, images, type, properties, expirationDate, onSuccess, onErrors) => {
	appFetch("/posts/post", fetchConfig("POST", { title, description, url, price, categoryId, images, type, properties, expirationDate }),
		onSuccess, onErrors);
};

export const deletePost = (id, onSuccess, onErrors) => {
	appFetch(`/posts/post/${id}`, fetchConfig("DELETE"), onSuccess, onErrors);
};

export const updatePost = (post, onSuccess, onErrors) => {
	appFetch(`/posts/post/${post.id}`, fetchConfig("PUT", post),
	onSuccess, onErrors);
};

export const maskPostAsExpired = (id, onSuccess, onErrors) => {
	appFetch(`/posts/post/${id}/markAsExpired`, fetchConfig("POST"), onSuccess, onErrors);
};

export const markPostAsValid = (id, onSuccess, onErrors) => {
	appFetch(`/posts/post/${id}/markAsValid`, fetchConfig("POST"), onSuccess, onErrors);
};

export const subscribe = (onUpdate) => {
	eventSource = new EventSource(`${config.BASE_PATH}/posts/subscribe?member=${sessionStorage.getItem("memberId")}`);

    eventSource.addEventListener("postCreation", (event) => {
      const eventData = JSON.parse(event.data);
      if (eventData && eventData.data === "posts.newPost") {
		eventSource.close();
        onUpdate(eventData.data);
      }
    });
    eventSource.onerror = (error) => {
      console.error(error);
    };
}