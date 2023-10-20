package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;

/**
 * The Class SearchParamsDto
 */
public class SearchParamsDto {

	/** the filters to search */
	@NotNull
	private SearchFiltersDto filters;

	/** the keywords to search */
	private String keywords;

	/** the page */
	@NotNull
	private int page;

	/**
	 * Instantiates a new search filters dto.
	 * 
	 * @param filters  the filters
	 * @param keywords the keywords
	 * @param page     the page
	 */
	public SearchParamsDto(SearchFiltersDto filters, String keywords, int page) {
		this.filters = filters;
		this.keywords = keywords;
		this.page = page;
	}

	/**
	 * Gets the filters
	 * 
	 * @return the filters for search
	 */
	public SearchFiltersDto getFilters() {
		return filters;
	}

	/**
	 * Sets the filters
	 * 
	 * @param filters the filters to set
	 */
	public void setFilters(SearchFiltersDto filters) {
		this.filters = filters;
	}

	/**
	 * Gets the keywords
	 * 
	 * @return the keywords
	 */
	public String getKeywords() {
		return keywords;
	}

	/**
	 * Sets the keywords
	 * 
	 * @param keywords the keywords to set
	 */
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	/**
	 * Gets the page
	 * 
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * Sets the page
	 * 
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
