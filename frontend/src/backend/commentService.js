import {
	fetchConfig,
	appFetch
} from "./appFetch";

export const findComments = (id, page, parentId, onSuccess) => {
	appFetch(`/comments/post/${id}`, fetchConfig("POST", { page, parentId }),
		onSuccess);
};

export const commentPost = (description, postId, commentParentId, onSuccess, onErrors) => {
	appFetch(`/comments/post/${postId}/comment`, fetchConfig("POST", { description, commentParentId}),
		onSuccess, onErrors)
};