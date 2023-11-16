package es.udc.fi.dc.fd.rest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {

	private Long id;

	private String description;

	private Long date;

	private int answers;

	private Long parentId;

	private int level;

	/** The comment user */
	private UserSummaryDto userSummaryDto;

}
