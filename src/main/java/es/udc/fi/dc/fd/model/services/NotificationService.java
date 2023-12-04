package es.udc.fi.dc.fd.model.services;

import java.util.List;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.entities.Notification;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

public interface NotificationService {
	List<Notification> findUnviewedNotifications(Long userId);

	void notify(String text, User notifierUser, User notifiedUser, Post post, Comment comment);

	void markAsViewed(Long notificationId, Long userId) throws InstanceNotFoundException, PermissionException;

	void sendNotification(Post post, String message);

	void sendNotification(Post post, User user, String message);
}
