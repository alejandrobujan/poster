import backend from '../../backend';
import * as actionTypes from './actionTypes';

const findCommentsCompleted = result => ({
    type: actionTypes.FIND_COMMENTS_COMPLETED,
    result
});

export const findComments = (postId, parentId, page) => dispatch => {
	backend.commentService.findComments(postId, page, parentId,
		elems => dispatch(findCommentsCompleted({page, elems})));
}

export const commentPost = (comment, postId, parentId, page, onErrors) => dispatch => {
	backend.commentService.commentPost(comment, postId, parentId,
		() => dispatch(findComments(postId, parentId, page)), onErrors);
}

export const clearComments = () => ({
	type: actionTypes.CLEAR_COMMENTS
});

export const previousFindCommentsResultPage = (postId, parentId, page) =>
	findComments(postId, parentId, page-1);

export const nextFindCommentsResultPage = (postId, parentId, page) =>
	findComments(postId, parentId, page+1);

export const findAnswersComment = (commentId, page, onSuccess) => dispatch => {
	backend.commentService.findAnswersComment(commentId, page,
		onSuccess);
}

export const commentAnswer = (comment, postId, id, page, onSuccess, onErrors) => dispatch => {
	backend.commentService.commentPost(comment, postId, id,
		() => dispatch(findAnswersComment(id, page, onSuccess)), onErrors);
}

export const nextFindAnswersResultPage = (parentId, page, onSuccess) =>
	findAnswersComment(parentId, page+1, onSuccess);
	



