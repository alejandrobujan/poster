package es.udc.fi.dc.fd.rest.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class SearchParamsDto
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class SearchParamsDto {

	/** the filters to search */
	@NotNull
	private SearchFiltersDto filters;

	/** the keywords to search */
	private String keywords;

	/** the page */
	@NotNull
	private int page;
}
