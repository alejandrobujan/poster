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
 * The Interface PostHandler.
 */
public interface PostHandler {

	/**
	 * The handle create post
	 * 
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url associated to the post
	 * @param price       the price of the post
	 * @param userId      the user id who create the post
	 * @param categoryId  the category id associated to the post
	 * @param imageList   the images associated to the post
	 * @param properties  the properties of the post
	 * @return the new post
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 * 
	 */
	Post handleCreate(String title, String description, String url, BigDecimal price, Long userId, Long categoryId,
			List<byte[]> imageList, Map<String, String> properties)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException;

	/**
	 * The handle update post
	 * 
	 * @param postId      the post id
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url associated to the post
	 * @param price       the price of the post
	 * @param userId      the user id who create the post
	 * @param categoryId  the category id associated to the post
	 * @param imageList   the images associated to the post
	 * @param properties  the properties of the post
	 * @return the updated post
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum image size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 * @throws PermissionException               the permission exception
	 * 
	 */
	Post handleUpdate(Long postId, String title, String description, String url, BigDecimal price, Long userId,
			Long categoryId, List<byte[]> imageList, Map<String, String> properties) throws InstanceNotFoundException,
			MaximumImageSizeExceededException, MissingRequiredParameterException, PermissionException;

}