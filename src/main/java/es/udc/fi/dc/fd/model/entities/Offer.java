package es.udc.fi.dc.fd.model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;

/**
 * The Class Entity.
 */
@Entity
public class Offer extends Post {

	/**
	 * @param title
	 * @param description
	 * @param url
	 * @param price
	 * @param creationDate
	 * @param user
	 * @param category
	 */
	public Offer(String title, String description, String url, BigDecimal price, LocalDateTime creationDate, User user,
			Category category) {
		super(title, description, url, price, creationDate, user, category);
	}

	@Override
	public String toString() {
		return "Offer [id=" + getId() + ", title=" + getTitle() + ", description=" + getDescription() + ", url="
				+ getUrl() + ", price=" + getPrice() + ", creationDate=" + getCreationDate() + ", user=" + getUser()
				+ ", category=" + getCategory() + ", images=" + getImages() + ", positiveRatings="
				+ getPositiveRatings() + ", negativeRatings=" + getNegativeRatings() + ", expired=" + isExpired() + "]";
	}
}
