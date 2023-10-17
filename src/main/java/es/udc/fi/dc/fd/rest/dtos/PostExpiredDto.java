package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;

public class PostExpiredDto {
	
	@NotNull
	private boolean expired;

	public PostExpiredDto() {}

	public PostExpiredDto(boolean expired) {
		this.expired = expired;
	}

	/**
	 * @return the expired
	 */
	public boolean isExpired() {
		return expired;
	}

	/**
	 * @param expired the expired to set
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}
	
	

}
