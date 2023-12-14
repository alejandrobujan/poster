package es.udc.fi.dc.fd.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.entities.Notification;
import es.udc.fi.dc.fd.model.entities.NotificationDao;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.Save;
import es.udc.fi.dc.fd.model.entities.SaveDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

@Service
public class NotificationServiceImpl implements NotificationService {

	/** The notification dao. */
	@Autowired
	private NotificationDao notificationDao;

	/** The save dao. */
	@Autowired
	private SaveDao saveDao;

	/** The permission checker. */
	@Autowired
	private PermissionChecker permissionChecker;

	/**
	 * Find unviewed notification
	 * 
	 * @param userId the user id
	 * @return a list of notification
	 */
	@Override
	public List<Notification> findUnviewedNotifications(Long userId) {
		return notificationDao.findByNotifiedUserIdAndViewedFalseOrderByCreationDateDesc(userId);
	}

	/**
	 * Notify an user
	 * 
	 * @param text         the text
	 * @param notifierUser the notifier user
	 * @param notifiedUser the notified user
	 * @param post         the post
	 * @param comment      the comment
	 */
	@Override
	public void notify(String text, User notifierUser, User notifiedUser, Post post, Comment comment) {
		if (!notifierUser.equals(notifiedUser)) {
			notificationDao
					.save(new Notification(text, false, comment.getDate(), notifierUser, notifiedUser, post, comment));
		}
	}

	/**
	 * Mark as viewed
	 * 
	 * @param notificationId the notification id
	 * @param userId         the user id
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws PermissionException       the permission exception
	 */
	@Override
	public void markAsViewed(Long notificationId, Long userId) throws InstanceNotFoundException, PermissionException {
		Notification notification = permissionChecker.checkNotificationExistsAndBelongsTo(notificationId, userId);
		notification.setViewed(true);

		notificationDao.save(notification);

	}

	/**
	 * Send notification
	 * 
	 * @param post    the post
	 * @param message the message
	 */
	@Override
	public void sendNotification(Post post, String message) {
		String text = "The post " + post.getTitle() + " has been " + message;
		List<Save> saves = saveDao.findSaveByPostId(post.getId());
		for (Save save : saves) {
			notificationDao.save(new Notification(text, null, save.getUser(), post));
		}
	}

	/**
	 * Send notification to an user
	 * 
	 * @param post    the post
	 * @param user    the user
	 * @param message the message
	 */
	@Override
	public void sendNotification(Post post, User user, String message) {
		String text = "The post " + post.getTitle() + " has been " + message;
		List<Save> saves = saveDao.findSaveByPostId(post.getId());
		for (Save save : saves) {
			if (!user.getId().equals(save.getUser().getId()))
				notificationDao.save(new Notification(text, null, save.getUser(), post));
		}
	}

}
