package es.udc.fi.dc.fd.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;

/**
 * The Class OfferTest.
 */
public class OfferTest {
	/**
	 * Test to string.
	 */
	@Test
	public void testToString() {
		User user = new User("user1", "password", "User", "One", "user1@udc.es", new byte[] {});
		Offer offer = new Offer("Title1", "Description1", null, new BigDecimal(10), LocalDateTime.now(), user, null);

		String expectedToString = "Offer [getId()=null, getTitle()=Title1, getDescription()=Description1, "
				+ "getUrl()=null, getPrice()=10, getCreationDate()=" + offer.getCreationDate() + ", getUser()="
				+ offer.getUser() + ", getCategory()=null, getImages()=[], "
				+ "getPositiveRatings()=0, getNegativeRatings()=0, isExpired()=false, getValidationDate()=null, toString()=]";

		assertEquals(expectedToString, offer.toString());

	}
}