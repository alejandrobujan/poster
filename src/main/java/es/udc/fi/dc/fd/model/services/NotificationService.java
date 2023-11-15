package es.udc.fi.dc.fd.model.services;

import java.util.List;

import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.entities.Notification;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.User;

public interface NotificationService {
	List<Notification> findUnviewedNotifications(Long userId);

	void notify(String text, User notifierUser, User notifiedUser, Post post, Comment comment);
}
