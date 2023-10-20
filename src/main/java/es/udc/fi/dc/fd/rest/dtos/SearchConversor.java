package es.udc.fi.dc.fd.rest.dtos;

import es.udc.fi.dc.fd.model.entities.SearchFilters;

/**
 * The Class SearchConversor.
 */
public class SearchConversor {

	/**
	 * To searchfilters.
	 *
	 * @param searchFiltersDto the search filters dto
	 * @return the search filters
	 */
	public static final SearchFilters toSearchFilters(SearchFiltersDto searchFiltersDto) {

		return new SearchFilters(searchFiltersDto.getCategoryId(), searchFiltersDto.getType(),
				searchFiltersDto.getPrice(), searchFiltersDto.getDate(), searchFiltersDto.isExpired());
	}
}
