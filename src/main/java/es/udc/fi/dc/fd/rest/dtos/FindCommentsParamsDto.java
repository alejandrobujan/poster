/**
 * 
 */
package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 */
@Getter
@AllArgsConstructor
public class FindCommentsParamsDto {

	@NotNull
	private int page;

	private Long parentId;

}
