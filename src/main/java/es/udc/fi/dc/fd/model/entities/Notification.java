package es.udc.fi.dc.fd.model.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * The Class Notification.
 */
@Entity
public class Notification {
	/** The id. */
	private Long id;
	/** If the notification text. */
	private String text;
	/** If the notification is viewed. */
	private boolean viewed;
	/** The creation date. */
	private LocalDateTime creationDate;
	/** The notifier user. */
	private User notifierUser;
	/** The notified user. */
	private User notifiedUser;
	/** The notifier post. */
	private Post post;
	/** The notifier comment. */
	private Comment comment;

	/**
	 * Instantiates a new image.
	 */
	public Notification() {

	}

	/**
	 * Instantiates a new notification.
	 * 
	 * @param text         the text
	 * @param viewed       if the notification is viewed
	 * @param creationDate the creation date
	 * @param notifierUser the notifier user
	 * @param notifiedUser the notified user
	 * @param post         the post
	 * @param comment      the comment
	 */
	public Notification(String text, boolean viewed, LocalDateTime creationDate, User notifierUser, User notifiedUser,
			Post post, Comment comment) {
		this.text = text;
		this.viewed = viewed;
		this.creationDate = creationDate;
		this.notifierUser = notifierUser;
		this.notifiedUser = notifiedUser;
		this.post = post;
		this.comment = comment;
	}

	/**
	 * @param text         the text
	 * @param notifierUser the notifier user
	 * @param notifiedUser the notified user
	 * @param post         the post
	 */
	public Notification(String text, User notifierUser, User notifiedUser, Post post) {
		this.text = text;
		this.notifierUser = notifierUser;
		this.notifiedUser = notifiedUser;
		this.post = post;
		this.creationDate = LocalDateTime.now();
		this.viewed = false;
	}

	/**
	 * Gets the id
	 * 
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the text
	 * 
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * Sets the text
	 * 
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * Gets the viewed param
	 * 
	 * @return the viewed
	 */
	public boolean isViewed() {
		return viewed;
	}

	/**
	 * Sets the viewed param
	 * 
	 * @param viewed the viewed to set
	 */
	public void setViewed(boolean viewed) {
		this.viewed = viewed;
	}

	/**
	 * Gets the creation date.
	 * 
	 * @return the creationDate
	 */
	@Column(columnDefinition = "TIMESTAMP")
	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	/**
	 * Sets the creation date.
	 * 
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * Gets the post.
	 * 
	 * @return the post
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	public Post getPost() {
		return post;
	}

	/**
	 * Gets the notifier user.
	 * 
	 * @return the notifierUser
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "notifierUserId")
	public User getNotifierUser() {
		return notifierUser;
	}

	/**
	 * Sets the notified user.
	 * 
	 * @param notifierUser the notifierUser to set
	 */
	public void setNotifierUser(User notifierUser) {
		this.notifierUser = notifierUser;
	}

	/**
	 * Gets the notified user.
	 * 
	 * @return the notifiedUser
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "notifiedUserId")
	public User getNotifiedUser() {
		return notifiedUser;
	}

	/**
	 * Sets the notified user.
	 * 
	 * @param notifiedUser the notifiedUser to set
	 */
	public void setNotifiedUser(User notifiedUser) {
		this.notifiedUser = notifiedUser;
	}

	/**
	 * Sets the post.
	 * 
	 * @param post the post to set
	 */
	public void setPost(Post post) {
		this.post = post;
	}

	/**
	 * Gets the comment.
	 * 
	 * @return the comment
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "commentId")
	public Comment getComment() {
		return comment;
	}

	/**
	 * Sets the comment.
	 * 
	 * @param comment the comment to set
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}

}
