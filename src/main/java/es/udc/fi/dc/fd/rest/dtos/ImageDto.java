/**
 * 
 */
package es.udc.fi.dc.fd.rest.dtos;

import es.udc.fi.dc.fd.model.entities.Post;

/**
 * The Class ImageDto
 */
public class ImageDto {
	
	/** The image id*/
	private Long id;
	/** The image binary data. */
	private byte[] data;
	/** The image post. */
	private Post post;
	
	
	/**
	 * Instantiates a new image dto. 
	 */
	public ImageDto() {}
	
	public ImageDto(Long id, byte[] data, Post post) {

		this.id = id;
		this.data = data;
		this.post = post;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
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
	 *
	 * @return the data
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(byte[] data) {
		this.data = data;
	}

	/**
	 * Gets the post.
	 * @return the post
	 */
	public Post getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(Post post) {
		this.post = post;
	}
	
	
}
