import {
	fetchConfig,
	appFetch
} from "./appFetch";

export const findComments = (id, page, parentId, onSuccess) => {
	appFetch("/comments/findComments", fetchConfig("POST", { id, page, parentId }),
		onSuccess);
};

export const commentPost = (description, postId, commentParentId, onSuccess, onErrors) => {
	appFetch(`/comments/post/${postId}/comment`, fetchConfig("POST", { description, commentParentId}),
		onSuccess, onErrors)
};

export const findAnswersComment = (id, page, onSuccess) => {
	appFetch("/comments/findCommentResponses", fetchConfig("POST", { id, page }),
		onSuccess);
};