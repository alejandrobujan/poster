package es.udc.fi.dc.fd.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * The Class Entity
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
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	 * @return the positive
	 */
	public boolean isPositive() {
		return positive;
	}

	/**
	 * @param positive the positive to set
	 */
	public void setPositive(boolean positive) {
		this.positive = positive;
	}

	/**
	 * @return the user
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "userId")
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the post
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	public Post getPost() {
		return post;
	}

	/**
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
