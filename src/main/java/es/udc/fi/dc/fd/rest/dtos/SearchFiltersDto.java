package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class SearchFiltersDto
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
public class SearchFiltersDto {

	/** the category id */
	private Long categoryId;
	/** the post type */
	private String type;
	/** the price */
	private Map<String, BigDecimal> price;
	/** the creation date of the post */
	private String date;
	/** if the post is expired or not */
	private boolean expired;
	/** the sort parameter for returning the posts */
	private String sortParam;
	/** the sort order for returning the posts */
	private String sortOrder;

}
