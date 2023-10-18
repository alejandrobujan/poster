package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;

/**
 * 
 */
public class SearchParamsDto {

	@NotNull
	private SearchFiltersDto filters;

	private String keywords;

	@NotNull
	private int page;

	/**
	 * @param filters
	 * @param keywords
	 * @param page
	 */
	public SearchParamsDto(SearchFiltersDto filters, String keywords, int page) {
		this.filters = filters;
		this.keywords = keywords;
		this.page = page;
	}

	/**
	 * @return the filters
	 */
	public SearchFiltersDto getFilters() {
		return filters;
	}

	/**
	 * @param filters the filters to set
	 */
	public void setFilters(SearchFiltersDto filters) {
		this.filters = filters;
	}

	/**
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

	@Override
	public String toString() {
		return "SearchParamsDto [filters=" + filters + ", keywords=" + keywords + ", page=" + page + "]";
	}

}
