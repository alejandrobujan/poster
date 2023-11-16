package es.udc.fi.dc.fd.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.model.entities.Notification;
import es.udc.fi.dc.fd.model.services.NotificationService;
import es.udc.fi.dc.fd.rest.dtos.NotificationConversor;
import es.udc.fi.dc.fd.rest.dtos.NotificationDto;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	@Autowired
	private NotificationService notificationService;

	@GetMapping("/notifications")
	public List<NotificationDto> findUnviewedNotifications(@RequestAttribute Long userId) {
		List<Notification> notifications = notificationService.findUnviewedNotifications(userId);

		return NotificationConversor.toNotificationsDto(notifications);
	}

}
