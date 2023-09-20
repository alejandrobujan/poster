package es.udc.fi.dc.fd.model.services;

import es.udc.fi.dc.fd.model.entities.Post;

/**
 * The Interface PostService.
 */
public interface PostService {
	
	/**
	 * Create post.
	 * 
	 * @param post
	 */
	void createPost(Post post);
	
	/**
	 * Find all posts.
	 * 
	 * @param page
	 * @param size
	 * @return the posts.
	 */
	Block<Post> findAllPosts(int page, int size);
	
}