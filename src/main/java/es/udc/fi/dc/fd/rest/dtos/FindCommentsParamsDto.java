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
	private int page;

	private Long parentId;

	/**
	 * @param id
	 * @param page
	 */
	public FindCommentsParamsDto(@NotNull int page, Long parentId) {
		this.page = page;
		this.parentId=parentId;
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
