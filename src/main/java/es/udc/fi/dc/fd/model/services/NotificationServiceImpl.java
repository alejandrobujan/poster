package es.udc.fi.dc.fd.model.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.entities.Notification;
import es.udc.fi.dc.fd.model.entities.NotificationDao;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.User;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationDao notificationDao;

	@Override
	public List<Notification> findUnviewedNotifications(Long userId) {
		return notificationDao.findByNotifiedUserIdAndViewedFalseOrderByCreationDateDesc(userId);
	}

	@Override
	public void notify(String text, User notifierUser, User notifiedUser, Post post, Comment comment) {
		if (!notifierUser.equals(notifiedUser)) {
			notificationDao
					.save(new Notification(text, false, comment.getDate(), notifierUser, notifiedUser, post, comment));
		}
	}
}
