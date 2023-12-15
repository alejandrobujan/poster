package es.udc.fi.dc.fd.model.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * The Class Comment.
 */

@Entity
public class Comment {

	/** The comment id. */
	private Long id;

	/** The comment description. */
	private String description;

	/** The comment date. */
	private LocalDateTime date;

	/** The comment user. */
	private User user;

	/** The comment post. */
	private Post post;

	/** The parent comment. */
	private Comment comment;

	/** The comment level. */
	private int level;

	/** The comment answers. */
	private int answers;

	/**
	 * Instantiates a new comment.
	 */
	public Comment() {

	}

	/**
	 * Instantiates a new comment.
	 * 
	 * @param description
	 * @param date
	 * @param user
	 * @param post
	 * @param comment
	 * @param level
	 */
	public Comment(String description, LocalDateTime date, User user, Post post, Comment comment, int level,
			int answers) {
		this.description = description;
		this.date = date;
		this.user = user;
		this.post = post;
		this.comment = comment;
		this.level = level;
		this.answers = answers;
	}

	/**
	 * Gets the comment id.
	 * 
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	/**
	 * Sets the comment id.
	 * 
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the comment description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the comment description.
	 * 
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the comment date.
	 * 
	 * @return the date
	 */
	public LocalDateTime getDate() {
		return date;
	}

	/**
	 * Sets the comment date.
	 * 
	 * @param date the date to set
	 */
	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	/**
	 * Gets the user.
	 * 
	 * @return the user
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 * 
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the comment id.
	 * 
	 * @return the post
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	public Post getPost() {
		return post;
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
	@JoinColumn(name = "parentId")
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

	/**
	 * Gets the comment level.
	 * 
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * Sets the comment level.
	 * 
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * Gets the number of answers.
	 * 
	 * @return the answers
	 */
	public int getAnswers() {
		return answers;
	}

	/**
	 * Sets the number of answers.
	 * 
	 * @param answers the level to set
	 */
	public void setAnswers(int answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", description=" + description + ", date=" + date + ", user=" + user + ", post="
				+ post + ", comment=" + comment + ", level=" + level + ", answers=" + answers + "]";
	}

}