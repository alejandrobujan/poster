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
		this.parentId = parentId;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @return the parentId
	 */
	public Long getParentId() {
		return parentId;
	}

}
