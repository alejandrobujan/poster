import backend from '../../backend';
import * as actionTypes from './actionTypes';

export const createPost = (title, description, url, price, categoryId, images, type, properties, expirationDate,
	onSuccess, onErrors) => dispatch =>
		backend.postService.createPost(title, description, url, price, categoryId, images, type, properties, expirationDate, onSuccess, onErrors);

export const findPostById = (id, onErrors) => dispatch => {
	backend.catalogService.findPostById(id,
		post => dispatch(findPostByIdCompleted(post)), onErrors);
}

const findPostByIdCompleted = post => ({
	type: actionTypes.FIND_POST_BY_ID_COMPLETED,
	post
});

export const clearPost = () => ({
	type: actionTypes.CLEAR_POST
}); 	

export const deletePost = (id, onSuccess, onErrors) => dispatch => {
	backend.postService.deletePost(id, onSuccess, onErrors);
};


export const updatePostCompleted = post => ({
    type: actionTypes.UPDATE_POST_COMPLETED,
    post
})
    
export const updatePost = (post, onSuccess, onErrors) => dispatch =>
	backend.postService.updatePost(post,
		post => {
			dispatch(updatePostCompleted(post));
			onSuccess();
		}, 
		onErrors);

const markPostAsExpiredCompleted = (expirationDate) => ({
	type: actionTypes.MARK_POST_AS_EXPIRED_COMPLETED,
	expirationDate
});

export const markPostAsExpired = (id, onErrors) => dispatch => {
	backend.postService.maskPostAsExpired(id, e => dispatch(markPostAsExpiredCompleted(e)), onErrors);
};	

const markPostAsValidCompleted = ({validationDate}) => ({
	type: actionTypes.MARK_POST_AS_VALID_COMPLETED,
	validationDate
});

export const markPostAsValid = (id, onErrors) => dispatch => {
	backend.postService.markPostAsValid(id, e => dispatch(markPostAsValidCompleted(e)), onErrors);
};

const ratePostCompleted = ratingCount => ({
	type: actionTypes.RATE_POST_COMPLETED,
	ratingCount
});

export const ratePostPositive = (id, onErrors) => dispatch => {
	backend.ratingService.ratePostPositive(id, ratingCount => dispatch(ratePostCompleted(ratingCount)), onErrors);
};


export const ratePostNegative = (id, onErrors) => dispatch => {
	backend.ratingService.ratePostNegative(id, ratingCount => dispatch(ratePostCompleted(ratingCount)), onErrors);
};

export const savePost = (postId, onSuccess, onErrors) => dispatch => {
	backend.saveService.savePost(postId, onSuccess, onErrors);
};

export const isPostSavedByUser = (postId, onSuccess, onErrors) => dispatch => {
	backend.saveService.isPostSavedByUser(postId, onSuccess, onErrors);
};

export const unSavePost = (postId, onSuccess, onErrors) => dispatch => {
	backend.saveService.unSavePost(postId, onSuccess, onErrors);
};
