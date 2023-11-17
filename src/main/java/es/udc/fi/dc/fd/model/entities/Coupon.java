package es.udc.fi.dc.fd.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;

/**
 * The Class Coupon.
 */
@Entity
public class Coupon extends Post {
	/** The code of the coupon. */
	private String code;

	/**
	 * Instantiates a new coupon.
	 */
	public Coupon() {
	}

	/**
	 * Instantiates a new coupon.
	 * 
	 * @param title        the coupon title
	 * @param description  the description of the coupon
	 * @param url          the url associated to the coupon
	 * @param price        the price
	 * @param creationDate the creation date
	 * @param user         the user associated to the coupon
	 * @param category     the category associated to the coupon
	 */
	public Coupon(String title, String description, String url, BigDecimal price, LocalDateTime creationDate,
			String code, User user, Category category, LocalDateTime expirationDate) {
		super(title, description, url, price, creationDate, user, category, expirationDate);
		this.code = code;
	}

	/**
	 * Gets the code of the coupon
	 * 
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code of the coupon
	 * 
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Coupon [code=" + code + ", getId()=" + getId() + ", getTitle()=" + getTitle() + ", getDescription()="
				+ getDescription() + ", getUrl()=" + getUrl() + ", getPrice()=" + getPrice() + ", getCreationDate()="
				+ getCreationDate() + ", getUser()=" + getUser() + ", getCategory()=" + getCategory() + ", getImages()="
				+ getImages() + ", getPositiveRatings()=" + getPositiveRatings() + ", getNegativeRatings()="
				+ getNegativeRatings() + ", expirationDate=" + getExpirationDate() + ", getValidationDate()="
				+ getValidationDate() + "]";
	}

}
