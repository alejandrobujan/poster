package es.udc.fi.dc.fd.rest.dtos;

import java.time.LocalDateTime;

public class NotificationDto {
	/** The id. */
	private Long id;
	/** If the notification text. */
	private String text;
	/** If the notification is viewed. */
	private boolean viewed;
	/** The creation date. */
	private LocalDateTime creationDate;
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
	 * @param id
	 * @param text
	 * @param viewed
	 * @param creationDate
	 * @param postId
	 * @param notifierUserDto
	 */
	public NotificationDto(Long id, String text, boolean viewed, LocalDateTime creationDate, Long postId,
			UserSummaryDto notifierUserDto) {
		this.id = id;
		this.text = text;
		this.viewed = viewed;
		this.creationDate = creationDate;
		this.postId = postId;
		this.notifierUserDto = notifierUserDto;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the viewed
	 */
	public boolean isViewed() {
		return viewed;
	}

	/**
	 * @param viewed the viewed to set
	 */
	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}

	/**
	 * @return the creationDate
	 */
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
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
