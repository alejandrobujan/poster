package es.udc.fi.dc.fd.rest.dtos;

import java.time.LocalDateTime;

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

	/**
	 * @param notificationId
	 * @param notificationText
	 * @param notificationViewed
	 * @param notificationCreationDate
	 * @param postId
	 * @param notifierUserDto
	 */
	public NotificationDto(Long notificationId, String notificationText, boolean notificationViewed,
			LocalDateTime notificationCreationDate, Long postId, UserSummaryDto notifierUserDto) {
		this.notificationId = notificationId;
		this.notificationText = notificationText;
		this.notificationViewed = notificationViewed;
		this.notificationCreationDate = notificationCreationDate;
		this.postId = postId;
		this.notifierUserDto = notifierUserDto;
	}

	/**
	 * @return the id
	 */
	public Long getNotificationId() {
		return notificationId;
	}

	/**
	 * @param notificationId the id to set
	 */
	public void setNotificationId(Long notificationId) {
		this.notificationId = notificationId;
	}

	/**
	 * @return the text
	 */
	public String getNotificationText() {
		return notificationText;
	}

	/**
	 * @param notificationText the text to set
	 */
	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	/**
	 * @return the viewed
	 */
	public boolean isNotificationViewed() {
		return notificationViewed;
	}

	/**
	 * @param notificationViewed the viewed to set
	 */
	public void setNotificationViewed(boolean notificationViewed) {
		this.notificationViewed = notificationViewed;
	}

	/**
	 * @return the creationDate
	 */
	public LocalDateTime getNotificationCreationDate() {
		return notificationCreationDate;
	}

	/**
	 * @param notificationCreationDate the creationDate to set
	 */
	public void setNotificationCreationDate(LocalDateTime notificationCreationDate) {
		this.notificationCreationDate = notificationCreationDate;
	}

	/**
	 * @return the postId
	 */
	public Long getPostId() {
		return postId;
	}

	/**
	 * @param postId the postId to set
	 */
	public void setPostId(Long postId) {
		this.postId = postId;
	}

	/**
	 * @return the notifierUserDto
	 */
	public UserSummaryDto getNotifierUserDto() {
		return notifierUserDto;
	}

	/**
	 * @param notifierUserDto the notifierUserDto to set
	 */
	public void setNotifierUserDto(UserSummaryDto notifierUserDto) {
		this.notifierUserDto = notifierUserDto;
	}

}
