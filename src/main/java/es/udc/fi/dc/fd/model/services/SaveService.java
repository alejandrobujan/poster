package es.udc.fi.dc.fd.model.services;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.services.exceptions.AlreadySavedException;
import es.udc.fi.dc.fd.model.services.exceptions.AlreadyUnsavedException;
import es.udc.fi.dc.fd.model.services.exceptions.SavePostUserCreatorException;

/**
 * The Interface SaveService.
 */
public interface SaveService {

	/**
	 * Save a post
	 * 
	 * @param postId the post id
	 * @param userId the user id
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 */
	void savePost(Long postId, Long userId)
			throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException;

	/**
	 * Unsave a post
	 * 
	 * @param postId the post id
	 * @param userId the user id
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws AlreadyUnsavedException   the already unsaved exception
	 */
	void unSavePost(Long postId, Long userId) throws InstanceNotFoundException, AlreadyUnsavedException;

	/**
	 * Returns if the post saved by user
	 * 
	 * @param postId the post id
	 * @param userId the user id
	 * @return if the post is saved by the user
	 */
	boolean isPostSavedByUser(Long postId, Long userId);

}
