package es.udc.fi.dc.fd.model.services;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Notification;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

/**
 * The Interface PermissionChecker.
 */
public interface PermissionChecker {

	/**
	 * Check user exists.
	 *
	 * @param userId the user id
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	public void checkUserExists(Long userId) throws InstanceNotFoundException;

	/**
	 * Check user.
	 *
	 * @param userId the user id
	 * @return the user
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	public User checkUser(Long userId) throws InstanceNotFoundException;

	/**
	 * Check post.
	 * 
	 * @param postId the post id
	 * @return the post
	 * @throws InstanceNotFoundExcepcion he instance not found exception
	 */
	public Post checkPost(Long postId) throws InstanceNotFoundException;

	/**
	 * Check post exists and belongs to user.
	 * 
	 * @param postId the post id
	 * @param userId the user id
	 * @return the post
	 * @throws PermissionException       the permission exception
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	public Post checkPostExistsAndBelongsTo(Long postId, Long userId)
			throws PermissionException, InstanceNotFoundException;

	public Notification checkNotificationExistsAndBelongsTo(Long notificationId, Long userId)
			throws PermissionException, InstanceNotFoundException;
}
