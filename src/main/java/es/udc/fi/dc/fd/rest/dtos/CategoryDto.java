package es.udc.fi.dc.fd.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The class CategoryDto
 */
@Getter
@AllArgsConstructor
public class CategoryDto {

	/** The id. */
	private Long id;
	/** The category name. */
	private String name;

	/**
	 * Instantiates a new category dto.
	 */
	public CategoryDto() {
	}

}
