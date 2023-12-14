package es.udc.fi.dc.fd.rest.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Notification;
import es.udc.fi.dc.fd.model.services.NotificationService;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;
import es.udc.fi.dc.fd.rest.dtos.NotificationConversor;
import es.udc.fi.dc.fd.rest.dtos.NotificationDto;

/**
 * The Class NotificationController.
 */
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

	/** The notification service. */
	@Autowired
	private NotificationService notificationService;

	/**
	 * Find unviewed notification
	 * 
	 * @param userId
	 * @return a list of notification dto
	 */
	@GetMapping("/notifications")
	public List<NotificationDto> findUnviewedNotifications(@RequestAttribute Long userId) {
		List<Notification> notifications = notificationService.findUnviewedNotifications(userId);

		return NotificationConversor.toNotificationsDto(notifications);
	}

	/**
	 * Mark as viewed
	 * 
	 * @param notificationId the notification id
	 * @param userId         the user id
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws PermissionException       the permission exception
	 */
	@PostMapping("/{id}/view")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void markAsViewed(@RequestAttribute Long userId, @PathVariable Long id)
			throws InstanceNotFoundException, PermissionException {
		notificationService.markAsViewed(id, userId);
	}

}
