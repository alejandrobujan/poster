package es.udc.fi.dc.fd.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * The Class Rating
 */
@Entity
public class Rating {
	/** The id. */
	private Long id;
	/** If the rating is positive or not. */
	private boolean positive;
	/** The rating user. */
	private User user;
	/** The rating post. */
	private Post post;

	/**
	 * Instantiates a new rating.
	 */
	public Rating() {
	}

	/**
	 * Instantiates a new rating.
	 *
	 * @param positive
	 * @param user
	 * @param post
	 */
	public Rating(boolean positive, User user, Post post) {
		this.positive = positive;
		this.user = user;
		this.post = post;
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
	 * Gets if the rating is positive or negative
	 * 
	 * @return if the rating is positive or negative
	 */
	public boolean isPositive() {
		return positive;
	}

	/**
	 * Sets if the rating is positive or negative
	 * 
	 * @param positive the value of rating to set (positive or negative)
	 */
	public void setPositive(boolean positive) {
		this.positive = positive;
	}

	/**
	 * Gets the user who rated
	 * 
	 * @return the user
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}

	/**
	 * Sets the user who rated
	 * 
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Gets the post that is rated
	 * 
	 * @return the post
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	public Post getPost() {
		return post;
	}

	/**
	 * Sets the post that is rated
	 * 
	 * @param post the post to set
	 */
	public void setPost(Post post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "Rating [id=" + id + ", positive=" + positive + ", user=" + user + ", post=" + post + "]";
	}

}
