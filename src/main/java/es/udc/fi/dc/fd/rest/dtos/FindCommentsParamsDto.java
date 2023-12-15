/**
 * 
 */
package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Class FindCommentsParamsDto.
 */
@Getter
@AllArgsConstructor
public class FindCommentsParamsDto {

	/** The page. */
	@NotNull
	private int page;

	/** The parent id. */
	private Long parentId;

}
