package es.udc.fi.dc.fd.model.services;

import java.util.List;

import es.udc.fi.dc.fd.model.entities.Notification;

public interface NotificationService {
	List<Notification> findUnviewedNotifications(Long userId);
}
