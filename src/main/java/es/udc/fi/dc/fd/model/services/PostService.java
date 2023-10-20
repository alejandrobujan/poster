package es.udc.fi.dc.fd.model.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

/**
 * The Interface PostService.
 */
public interface PostService {

	/**
	 * Create post.
	 * 
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url associated to the post
	 * @param price       the price of the post
	 * @param userId      the user id who create the post
	 * @param categoryId  the category id associated to the post
	 * @param imageList   the images associated to the post
	 * @param type        the post type
	 * @param properties  the properties of the post
	 * @return the new post
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 */
	Post createPost(String title, String description, String url, BigDecimal price, Long userId, Long categoryId,
			List<byte[]> imageList, String type, Map<String, String> properties)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException;

	/**
	 * Delete post.
	 * 
	 * @param userId the user id who delete the post
	 * @param postId the post id associated to the post
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws PermissionException       the permission exception
	 */
	void deletePost(Long userId, Long postId) throws InstanceNotFoundException, PermissionException;

	/**
	 * Mark or unmark a post as expired.
	 * 
	 * @param userId  the user id associated to the post
	 * @param postId  the post id
	 * @param expired if the post is expired or not
	 * @return if the post is marked as expired or not
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws PermissionException       the permission exception
	 */
	boolean markAsExpired(Long userId, Long postId, boolean expired)
			throws InstanceNotFoundException, PermissionException;

	/**
	 * Update post.
	 * 
	 * @param postId      the post id
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url associated to the post
	 * @param price       the price of the post
	 * @param userId      the user id who create the post
	 * @param categoryId  the category id associated to the post
	 * @param imageList   the images associated to the post
	 * @param type        the post type
	 * @param properties  the properties of the post
	 * @return the updated post
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum image size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 * @throws PermissionException               the permission exception
	 */
	Post updatePost(Long postId, String title, String description, String url, BigDecimal price, Long userId,
			Long categoryId, List<byte[]> imageList, String type, Map<String, String> properties)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException,
			PermissionException;

}