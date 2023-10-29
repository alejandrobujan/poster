import backend from '../../backend';
import * as actionTypes from './actionTypes';

export const createPost = (title, description, url, price, categoryId, images, type, properties,
	onSuccess, onErrors) => dispatch =>
		backend.postService.createPost(title, description, url, price, categoryId, images, type, properties, onSuccess, onErrors);

export const findPostById = id => dispatch => {
	backend.catalogService.findPostById(id,
		post => dispatch(findPostByIdCompleted(post)));
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

const markPostAsExpiredCompleted = ({expired}) => ({
	type: actionTypes.MARK_POST_AS_EXPIRED_COMPLETED,
	expired
});

export const markPostAsExpired = (id, expired, onErrors) => dispatch => {
	backend.postService.maskPostAsExpired(id, expired, e => dispatch(markPostAsExpiredCompleted(e)), onErrors);
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
