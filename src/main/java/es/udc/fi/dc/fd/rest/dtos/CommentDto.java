package es.udc.fi.dc.fd.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The class CommentDto
 */
@Getter
@AllArgsConstructor
public class CommentDto {

	/** The id. */
	private Long id;

	/** The description. */
	private String description;

	/** The date. */
	private Long date;

	/** The answers. */
	private int answers;

	/** The comment parent id. */
	private Long parentId;

	/** The comment level. */
	private int level;

	/** The comment user */
	private UserSummaryDto userSummaryDto;

}
