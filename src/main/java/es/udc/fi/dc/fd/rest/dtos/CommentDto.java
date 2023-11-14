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
	public CommentDto(Long id, String description, Long date, int answers, Long parentId, int level, UserSummaryDto userSummaryDto) {
		this.id = id;
		this.description = description;
		this.date = date;
		this.answers=answers;
		this.parentId=parentId;
		this.level=level;
		this.userSummaryDto = userSummaryDto;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
	}

	/**
	 * @return the answers
	 */
	public int getAnswers() {
		return answers;
	}

	/**
	 * @param answers the answerts to set
	 */
	public void setAnswers(int answers) {
		this.answers = answers;
	}

	/**
	 * @return the userSummaryDto
	 */
	public UserSummaryDto getUserSummaryDto() {
		return userSummaryDto;
	}

	/**
	 * @param userSummaryDto the userSummaryDto to set
	 */
	public void setUserSummaryDto(UserSummaryDto userSummaryDto) {
		this.userSummaryDto = userSummaryDto;
	}

	/**
	 * @return the parent id
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parent id to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param parentId the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

}
