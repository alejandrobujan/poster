package es.udc.fi.dc.fd.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;

/**
 * The Class Offer.
 */
@Entity
public class Offer extends Post {

	/**
	 * Instantiates a new offer.
	 */
	public Offer() {
	}

	/**
	 * Instantiates a new offer.
	 * 
	 * @param title        the coupon title
	 * @param description  the description of the offer
	 * @param url          the url associated to the offer
	 * @param price        the price
	 * @param creationDate the creation date
	 * @param user         the user associated to the offer
	 * @param category     the category associated to the offer
	 */
	public Offer(String title, String description, String url, BigDecimal price, LocalDateTime creationDate, User user,
			Category category) {
		super(title, description, url, price, creationDate, user, category);
	}

	@Override
	public String toString() {
		return "Offer [getId()=" + getId() + ", getTitle()=" + getTitle() + ", getDescription()=" + getDescription()
				+ ", getUrl()=" + getUrl() + ", getPrice()=" + getPrice() + ", getCreationDate()=" + getCreationDate()
				+ ", getUser()=" + getUser() + ", getCategory()=" + getCategory() + ", getImages()=" + getImages()
				+ ", getPositiveRatings()=" + getPositiveRatings() + ", getNegativeRatings()=" + getNegativeRatings()
				+ ", isExpired()=" + isExpired() + ", getValidationDate()=" + getValidationDate() + ", toString()="
				+ "]";
	}

}
