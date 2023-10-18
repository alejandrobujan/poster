package es.udc.fi.dc.fd.model.services;

import java.util.List;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.SearchFilters;

public interface CatalogService {
	Block<Post> findPosts(SearchFilters searchFilters, String keywords, int page, int size);

	/**
	 * Find all categories.
	 * 
	 * @return the categories.
	 */
	List<Category> findAllCategories();

	/**
	 * Find a post.
	 * 
	 * @param postId
	 * @return the post.
	 */
	Post findPostById(Long postId) throws InstanceNotFoundException;
}
