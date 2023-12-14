package es.udc.fi.dc.fd.rest.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Class NotificationDto.
 */
@Getter
@AllArgsConstructor
public class NotificationDto {
	/** The id. */
	private Long notificationId;
	/** If the notification text. */
	private String notificationText;
	/** If the notification is viewed. */
	private boolean notificationViewed;
	/** The creation date. */
	private LocalDateTime notificationCreationDate;
	/** The notifier post. */
	private Long postId;
	/** The notifier user. */
	private UserSummaryDto notifierUserDto;

	/**
	 * Instantiates a new notification
	 */
	public NotificationDto() {
	}
}
