package es.udc.fi.dc.fd.rest.dtos;

public class CommentDto {

	private Long id;

	private String description;

	private Long date;

	private int answers;

	private Long parentId;

	private int level;

	/** The comment user */
	private UserSummaryDto userSummaryDto;

	/**
	 * @param id
	 * @param description
	 * @param date
	 * @param answers
	 * @param parentId
	 * @param level
	 * @param userSummaryDto
	 */
	public CommentDto(Long id, String description, Long date, int answers, Long parentId, int level,
			UserSummaryDto userSummaryDto) {
		this.id = id;
		this.description = description;
		this.date = date;
		this.answers = answers;
		this.parentId = parentId;
		this.level = level;
		this.userSummaryDto = userSummaryDto;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}

	/**
	 * @return the answers
	 */
	public int getAnswers() {
		return answers;
	}

	/**
	 * @return the userSummaryDto
	 */
	public UserSummaryDto getUserSummaryDto() {
		return userSummaryDto;
	}

	/**
	 * @return the parent id
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

}
