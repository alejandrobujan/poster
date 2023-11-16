package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The class PostExpiredDto
 */
@Getter
@AllArgsConstructor
public class PostExpiredDto {

	/** If is expired or not */
	@NotNull
	private boolean expired;

	/**
	 * Instantiates a post expired dto.
	 */
	public PostExpiredDto() {
	}

}
