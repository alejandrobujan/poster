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

	@Autowired
	private NotificationDao notificationDao;

	@Autowired
	private SaveDao saveDao;

	/** The post dao. */
	@Autowired
	private PermissionChecker permissionChecker;

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

	@Override
	public void markAsViewed(Long notificationId, Long userId) throws InstanceNotFoundException, PermissionException {
		Notification notification = permissionChecker.checkNotificationExistsAndBelongsTo(notificationId, userId);
		notification.setViewed(true);

		notificationDao.save(notification);

	}

	private void sendNotificationAux(Post post, String text) {
		List<Save> saves = saveDao.findSaveByPostId(post.getId());
		for (Save save : saves) {
			notificationDao.save(new Notification(text, post.getUser(), save.getUser(), post));
		}
	}

	@Override
	public void sendNotification(Post post) {
		String text = "The post " + post.getTitle() + " has been modified";
		sendNotificationAux(post, text);
	}

	@Override
	public void sendNotification(Post post, String message) {
		String text = "The post " + post.getTitle() + " has been " + message;
		sendNotificationAux(post, text);
	}

}
