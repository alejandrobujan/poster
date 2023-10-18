package es.udc.fi.dc.fd.rest.dtos;

import es.udc.fi.dc.fd.model.entities.SearchFilters;

public class SearchConversor {
	public static final SearchFilters toSearchFilters(SearchFiltersDto searchFiltersDto) {

		return new SearchFilters(searchFiltersDto.getCategoryId(), searchFiltersDto.getType(),
				searchFiltersDto.getPrice(), searchFiltersDto.getDate(), searchFiltersDto.isExpired());
	}
}
