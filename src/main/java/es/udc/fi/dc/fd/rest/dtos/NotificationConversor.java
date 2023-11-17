package es.udc.fi.dc.fd.rest.dtos;

import java.util.List;
import java.util.stream.Collectors;

import es.udc.fi.dc.fd.model.entities.Notification;

public class NotificationConversor {

	public final static NotificationDto toNotificationDto(Notification notification) {
		return new NotificationDto(notification.getId(), notification.getText(), notification.isViewed(),
				notification.getCreationDate(), notification.getPost().getId(),
				new UserSummaryDto(notification.getNotifierUser().getId(), notification.getNotifierUser().getUserName(),
						notification.getNotifierUser().getFirstName(), notification.getNotifierUser().getLastName(),
						notification.getNotifierUser().getAvatar()));
	}

	public final static List<NotificationDto> toNotificationsDto(List<Notification> notifications) {
		return notifications.stream().map(n -> toNotificationDto(n)).collect(Collectors.toList());
	}

}
