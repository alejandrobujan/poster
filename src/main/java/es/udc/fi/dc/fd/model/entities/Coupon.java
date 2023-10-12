package es.udc.fi.dc.fd.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;

/**
 * The Class Entity.
 */
@Entity
public class Coupon extends Post {
	private String code;

	public Coupon() {}

	/**
	 * @param title
	 * @param description
	 * @param url
	 * @param price
	 * @param creationDate
	 * @param user
	 * @param category
	 */
	public Coupon(String title, String description, String url, BigDecimal price, LocalDateTime creationDate,
			String code, User user, Category category) {
		super(title, description, url, price, creationDate, user, category);
		this.code = code;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "Coupon [id=" + getId() + ", title=" + getTitle() + ", description=" + getDescription() + ", url="
				+ getUrl() + ", price=" + getPrice() + ", creationDate=" + getCreationDate() + ", user=" + getUser()
				+ ", category=" + getCategory() + ", images=" + getImages() + ", positiveRatings=" + getPositiveRatings()
				+ ", negativeRatings=" + getNegativeRatings() + ", expired=" + isExpired() + ", code="
				+ code + "]";
	}

}
