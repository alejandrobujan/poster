package es.udc.fi.dc.fd.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Notification;
import es.udc.fi.dc.fd.model.entities.NotificationDao;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

/**
 * The Class PermissionCheckerImpl.
 */
@Service
@Transactional(readOnly = true)
public class PermissionCheckerImpl implements PermissionChecker {

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The notification dao. */
	@Autowired
	private NotificationDao notificationDao;

	/**
	 * Check user exists.
	 *
	 * @param userId the user id
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@Override
	public void checkUserExists(Long userId) throws InstanceNotFoundException {

		if (!userDao.existsById(userId)) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}

	}

	/**
	 * Check user.
	 *
	 * @param userId the user id
	 * @return the user
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@Override
	public User checkUser(Long userId) throws InstanceNotFoundException {

		Optional<User> user = userDao.findById(userId);

		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}

		return user.get();

	}

	/**
	 * Check post.
	 * 
	 * @param postId the post id
	 * @return the post
	 * @throws InstanceNotFoundExcepcion he instance not found exception
	 */
	public Post checkPost(Long postId) throws InstanceNotFoundException {

		Optional<Post> post = postDao.findById(postId);

		if (!post.isPresent()) {
			throw new InstanceNotFoundException("project.entities.post", postId);
		}

		return post.get();

	}

	/**
	 * Check post exists and belongs to user.
	 * 
	 * @param postId the post id
	 * @param userId the user id
	 * @return the post
	 * @throws PermissionException       the permission exception
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@Override
	public Post checkPostExistsAndBelongsTo(Long postId, Long userId)
			throws PermissionException, InstanceNotFoundException {
		Optional<Post> post = postDao.findById(postId);

		if (!post.isPresent()) {
			throw new InstanceNotFoundException("project.entities.post", postId);
		}

		if (!post.get().getUser().getId().equals(userId)) {
			throw new PermissionException();
		}

		return post.get();
	}

	@Override
	public Notification checkNotificationExistsAndBelongsTo(Long notificationId, Long userId)
			throws PermissionException, InstanceNotFoundException {
		Optional<Notification> notification = notificationDao.findById(notificationId);

		if (!notification.isPresent()) {
			throw new InstanceNotFoundException("project.entities.notification", notificationId);
		}

		if (!notification.get().getNotifiedUser().getId().equals(userId)) {
			throw new PermissionException();
		}

		return notification.get();
	}

}