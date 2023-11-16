package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * The class CommentPostParamsDto.
 */
@Getter
@Setter
@AllArgsConstructor
public class CommentParamsDto {

	/** The comment description. */
	@NotNull
	@Size(min = 1, max = 256)
	private String description;

	/** The comment parent id. */
	private Long commentParentId;

	/**
	 * Instantiates a new comment post params dto.
	 */
	public CommentParamsDto() {
	}
}
