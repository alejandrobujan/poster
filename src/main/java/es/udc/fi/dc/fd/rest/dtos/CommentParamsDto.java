package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * The class CommentPostParamsDto.
 */
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

	/**
	 * @param description
	 * @param commentParentid
	 */
	public CommentParamsDto(String description, Long commentParentId) {
		this.description = description;
		this.commentParentId = commentParentId;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the commentParentId
	 */
	public Long getCommentParentId() {
		return commentParentId;
	}

	/**
	 * @param commentParentId the commentParentid to set
	 */
	public void setCommentParentId(Long commentParentId) {
		this.commentParentId = commentParentId;
	}

}