package es.udc.fi.dc.fd.model.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectFormValuesException;
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
	 * @param post
	 * @throws IncorrectFormValuesException
	 */
	Post createPost(String title, String description, String url, BigDecimal price, Long userId, Long categoryId,
			List<byte[]> imageList, String type, Map<String, String> properties) throws InstanceNotFoundException,
			MaximumImageSizeExceededException, MissingRequiredParameterException, IncorrectFormValuesException;

	/**
	 * @param userId
	 * @param postId
	 * @throws InstanceNotFoundException
	 * @throws PermissionException
	 */
	void deletePost(Long userId, Long postId) throws InstanceNotFoundException, PermissionException;

	/**
	 * Mark or unmark a post as expired.
	 * 
	 * @param postId
	 * @return the post.
	 */
	boolean markAsExpired(Long userId, Long postId, boolean expired)
			throws InstanceNotFoundException, PermissionException;

	/**
	 * @param postId
	 * @param title
	 * @param description
	 * @param url
	 * @param price
	 * @param userId
	 * @param categoryId
	 * @param imageList
	 * @param type
	 * @param properties
	 * @return
	 * @throws InstanceNotFoundException
	 * @throws MaximumImageSizeExceededException
	 * @throws MissingRequiredParameterException
	 * @throws PermissionException
	 * @throws IncorrectFormValuesException
	 */
	Post updatePost(Long postId, String title, String description, String url, BigDecimal price, Long userId,
			Long categoryId, List<byte[]> imageList, String type, Map<String, String> properties)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException,
			PermissionException, IncorrectFormValuesException;

}