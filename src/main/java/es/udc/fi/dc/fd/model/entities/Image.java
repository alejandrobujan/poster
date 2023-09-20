package es.udc.fi.dc.fd.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * The Class Image.
 */

@Entity
public class Image {
	
	/** The image id. */
	private Long id;
	
	/** The movie url. */
	private String url;
	
	/** The id of the post. */
	private Post post;
	
	/**
	 * Instantiates a new image.
	 */
	public Image() {
		
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the post.
	 *
	 * @return the post
	 */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	public Post getpost() {
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

	
	
	
}
