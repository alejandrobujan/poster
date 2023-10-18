package es.udc.fi.dc.fd.model.entities;

import java.math.BigDecimal;
import java.util.Map;

public class SearchFilters {
	private Long categoryId;
	private String type;
	private Map<String, BigDecimal> price;
	private String date;
	private boolean expired;

	/**
	 * @param categoryId
	 * @param type
	 * @param price
	 * @param date
	 * @param expired
	 */
	public SearchFilters(Long categoryId, String type, Map<String, BigDecimal> price, String date, boolean expired) {
		this.categoryId = categoryId;
		this.type = type;
		this.price = price;
		this.date = date;
		this.expired = expired;
	}

	/**
	 * @return the categoryId
	 */
	public Long getCategoryId() {
		return categoryId;
	}

	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the price
	 */
	public Map<String, BigDecimal> getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Map<String, BigDecimal> price) {
		this.price = price;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the expired
	 */
	public boolean isExpired() {
		return expired;
	}

	/**
	 * @param expired the expired to set
	 */
	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	@Override
	public String toString() {
		return "SearchFilters [categoryId=" + categoryId + ", type=" + type + ", price=" + price + ", date=" + date
				+ ", expired=" + expired + "]";
	}

}
