package es.udc.fi.dc.fd.model.services;

import java.util.List;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.SearchFilters;

/**
 * The Interface CatalogService.
 */
public interface CatalogService {

	/**
	 * Find posts with the possibility of applying filters.
	 * 
	 * @param searchFilters the search filters
	 * @param keywords      the keywords to search
	 * @param page          the page
	 * @param size          the size
	 * @return the posts.
	 */
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
	 * @param postId the post id
	 * @return the post.
	 */
	Post findPostById(Long postId) throws InstanceNotFoundException;
}
