/**
 * 
 */
package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public class FindCommentsParamsDto {

	@NotNull
	private Long id;

	@NotNull
	private int page;

	private Long parentId;

	/**
	 * @param id
	 * @param page
	 */
	public FindCommentsParamsDto(@NotNull Long id, @NotNull int page, Long parentId) {
		this.id = id;
		this.page = page;
		this.parentId=parentId;
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
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

}
