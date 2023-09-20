package es.udc.fi.dc.fd.model.services;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
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
	void createPost(String title, String description, String url, BigDecimal price, Long userId, 
			Long categoryId, List<MultipartFile> images) throws InstanceNotFoundException, IOException;
	
	/**
	 * Find all posts.
	 * 
	 * @param page
	 * @param size
	 * @return the posts.
	 */
	Block<Post> findAllPosts(int page, int size);
	
	
	/**
	 * Find all categories.
	 * 
	 * @return the categories.
	 */
	List<Category> findAllCategories();
	
}