import { fetchConfig, appFetch } from './appFetch';

export const findPosts = (params, onSuccess) => 
	appFetch('/catalog/feed', fetchConfig("POST", params), onSuccess);


export const findAllCategories = (onSuccess) => 
	appFetch('/catalog/categories', fetchConfig("GET"), onSuccess);
	

export const findPostById = (id, onSuccess) => 
	appFetch(`/catalog/postDetail/${id}`, fetchConfig("GET"), onSuccess);

