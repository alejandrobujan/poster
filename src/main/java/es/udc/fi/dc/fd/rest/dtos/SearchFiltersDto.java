package es.udc.fi.dc.fd.rest.dtos;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class SearchFiltersDto
 */
@Getter
@ToString
public class SearchFiltersDto {

	/** the category id */
	@Setter
	private Long categoryId;
	/** the post type */
	@Setter
	private String type;
	/** the price */
	private Map<String, BigDecimal> price;
	/** the creation date of the post */
	@Setter
	private String date;
	/** if the post is expired or not */
	@Setter
	private boolean expired;
	/** the sort parameter for returning the posts */
	@Setter
	private String sortParam;
	/** the sort order for returning the posts */
	@Setter
	private String sortOrder;

	/**
	 * Instantiates a new search filters dto.
	 * 
	 * @param categoryId the category id
	 * @param type       the type of the post (coupon or offer)
	 * @param price      the price of the post
	 * @param date       the creation date of the post
	 * @param expired    if the post is expired or not
	 * @param sortParam  t
	 * @param sortOrder
	 */
	public SearchFiltersDto(Long categoryId, String type, Map<String, BigDecimal> price, String date, boolean expired,
			String sortParam, String sortOrder) {
		this.categoryId = categoryId;
		this.type = type;
		this.price = new HashMap<>(price);
		this.date = date;
		this.expired = expired;
		this.sortParam = sortParam;
		this.sortOrder = sortOrder;
	}

	/**
	 * Sets the price
	 * 
	 * @param price the price to set
	 */
	public void setPrice(Map<String, BigDecimal> price) {
		this.price = new HashMap<>(price);
	}

}
