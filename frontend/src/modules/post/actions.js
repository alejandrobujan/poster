import backend from '../../backend';

export const createPost = (title, description, url, price, categoryId, images, 
							onSuccess, onErrors) => dispatch => 
	backend.postService.createPost(title, description, url, price, categoryId, images, onSuccess, onErrors);
