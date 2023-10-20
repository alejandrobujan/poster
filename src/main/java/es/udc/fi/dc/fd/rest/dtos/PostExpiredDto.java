package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;

/**
 * The class PostExpiredDto
 */
public class PostExpiredDto {

	/** If is expired or not */
	@NotNull
	private boolean expired;

	/**
	 * Instantiates a post expired dto.
	 */
	public PostExpiredDto() {
	}

	/**
	 * Instantiates a post expired dto.
	 * 
	 * @param expired if the post is expired or not
	 */
	public PostExpiredDto(boolean expired) {
		this.expired = expired;
	}

	/**
	 * Gets if the post is expired or not
	 * 
	 * @return if the post is expired or not
	 */
	public boolean isExpired() {
		return expired;
	}

	/**
	 * Sets the expired attribute
	 * 
	 * @param expired value to set (if it is expired or not)
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}

}
