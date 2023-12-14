package es.udc.fi.dc.fd.model.services;

import java.util.List;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.entities.Notification;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

public interface NotificationService {

	/**
	 * Find unviewed notification
	 * 
	 * @param userId the user id
	 * @return a list of notification
	 */
	List<Notification> findUnviewedNotifications(Long userId);

	/**
	 * Notify an user
	 * 
	 * @param text         the text
	 * @param notifierUser the notifier user
	 * @param notifiedUser the notified user
	 * @param post         the post
	 * @param comment      the comment
	 */
	void notify(String text, User notifierUser, User notifiedUser, Post post, Comment comment);

	/**
	 * Mark as viewed
	 * 
	 * @param notificationId the notification id
	 * @param userId         the user id
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws PermissionException       the permission exception
	 */
	void markAsViewed(Long notificationId, Long userId) throws InstanceNotFoundException, PermissionException;

	/**
	 * Send notification
	 * 
	 * @param post    the post
	 * @param message the message
	 */
	void sendNotification(Post post, String message);

	/**
	 * Send notification to an user
	 * 
	 * @param post    the post
	 * @param user    the user
	 * @param message the message
	 */
	void sendNotification(Post post, User user, String message);
}
