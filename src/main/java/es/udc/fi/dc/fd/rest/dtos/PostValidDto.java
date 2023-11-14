package es.udc.fi.dc.fd.rest.dtos;

import java.time.LocalDateTime;

/**
 * The class PostValidDto
 */
public class PostValidDto {

	/** The validation date */
	private LocalDateTime validationDate;

	/**
	 * Instantiates a post valid dto.
	 */
	public PostValidDto() {
	}

	/**
	 * Instantiates a post valid dto.
	 * 
	 * @param valid the validation date
	 */
	public PostValidDto(LocalDateTime validationDate) {
		this.validationDate = validationDate;
	}

	/**
	 * @return the validation date
	 */
	public LocalDateTime getValidationDate() {
		return validationDate;
	}

	/**
	 * @param valid the validation date to set
	 */
	public void setValidationDate(LocalDateTime validationDate) {
		this.validationDate = validationDate;
	}

}
