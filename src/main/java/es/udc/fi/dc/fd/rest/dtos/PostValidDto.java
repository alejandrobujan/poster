package es.udc.fi.dc.fd.rest.dtos;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The class PostValidDto
 */
@Getter
@AllArgsConstructor
public class PostValidDto {

	/** The validation date */
	private LocalDateTime validationDate;

	/**
	 * Instantiates a post valid dto.
	 */
	public PostValidDto() {
	}
}
