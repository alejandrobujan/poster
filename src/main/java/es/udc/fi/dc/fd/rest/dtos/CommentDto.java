package es.udc.fi.dc.fd.rest.dtos;

public class CommentDto {

	private Long id;

	private String description;

	private Long date;

	/** The post user */
	private UserSummaryDto userSummaryDto;

	/**
	 * @param id
	 * @param description
	 * @param date
	 * @param userSummaryDto
	 */
	public CommentDto(Long id, String description, Long date, UserSummaryDto userSummaryDto) {
		this.id = id;
		this.description = description;
		this.date = date;
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

}