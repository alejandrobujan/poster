package es.udc.fi.dc.fd.model.entities;

import java.math.BigDecimal;
import java.util.Map;

/**
 * The Class SearchFilters
 */
public class SearchFilters {

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
	private String sortingParameter;
	/** the sort order for returning the posts */
	private String sortingOrder;

	/**
	 * Instantiates a new SearchFilters.
	 * 
	 * @param categoryId       the category id
	 * @param type             the type of the post (coupon or offer)
	 * @param price            the price of the post
	 * @param date             the creation date of the post
	 * @param expired          if the post is expired or not
	 * @param sortingParameter the parameter for sorting posts
	 * @param sortingOrder     the order for sorting posts
	 */
	public SearchFilters(Long categoryId, String type, Map<String, BigDecimal> price, String date, boolean expired,
			String sortingParameter, String sortingOrder) {
		this.categoryId = categoryId;
		this.type = type;
		this.price = price;
		this.date = date;
		this.expired = expired;
		this.sortingParameter = sortingParameter;
		this.sortingOrder = sortingOrder;
	}

	/**
	 * Gets the category id
	 * 
	 * @return the categoryId
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * Sets the category id
	 * 
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * Gets the post type
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the post type
	 * 
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the price
	 * 
	 * @return the price
	 */
	public Map<String, BigDecimal> getPrice() {
		return price;
	}

	/**
	 * Sets the price
	 * 
	 * @param price the price to set
	 */
	public void setPrice(Map<String, BigDecimal> price) {
		this.price = price;
	}

	/**
	 * Gets the creation date
	 * 
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Sets the creation date
	 * 
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Gets if the post is expired or not
	 * 
	 * @return if the post is expired or not
	 */
	public boolean isExpired() {
		return expired;
	}

	/**
	 * Sets the expired attribute
	 * 
	 * @param expired value to set (if it is expired or not)
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	/**
	 * Gets the parameter for sorting posts
	 * 
	 * @return sortingParameter the parameter for sorting posts
	 */
	public String getSortingParameter() {
		return sortingParameter;
	}

	/**
	 * Sets the parameter for sorting posts
	 * 
	 * @param sortingParameter
	 */
	public void setSortingParameter(String sortingParameter) {
		this.sortingParameter = sortingParameter;
	}

	/**
	 * Gets the order for sorting posts
	 * 
	 * @return sortingOrder the order for sorting posts
	 */
	public String getSortingOrder() {
		return sortingOrder;
	}

	/**
	 * Sets the order for sorting posts
	 * 
	 * @param sortingOrder
	 */
	public void setSortingOrder(String sortingOrder) {
		this.sortingOrder = sortingOrder;
	}

	@Override
	public String toString() {
		return "SearchFilters [categoryId=" + categoryId + ", type=" + type + ", price=" + price + ", date=" + date
				+ ", expired=" + expired + ", sortingParameter=" + sortingParameter + ", sortingOrder=" + sortingOrder
				+ "]";
	}

}
