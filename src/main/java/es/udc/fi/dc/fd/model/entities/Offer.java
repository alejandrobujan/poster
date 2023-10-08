package es.udc.fi.dc.fd.model.entities;

import jakarta.persistence.Entity;

/**
 * The Class Entity.
 */
@Entity
public class Offer extends Post {

	@Override
	public String toString() {
		return "Offer [id=" + getId() + ", title=" + getTitle() + ", description=" + getDescription() + ", url="
				+ getUrl() + ", price=" + getPrice() + ", creationDate=" + getCreationDate() + ", user=" + getUser()
				+ ", category=" + getCategory() + ", images=" + getImages() + ", positiveRatings=" + getPositiveRatings()
				+ ", negativeRatings=" + getNegativeRatings() + ", expired=" + isExpired() + "]";
	}
}
