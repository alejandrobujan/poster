package es.udc.fi.dc.fd.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Test;

/**
 * The Class CouponTest.
 */
public class CouponTest {
	/**
	 * Test to string.
	 */
	@Test
	public void testToString() {
		User user = new User("user1", "password", "User", "One", "user1@udc.es", new byte[] {});
		Coupon coupon = new Coupon("Title2", "Description2", null, new BigDecimal(10), LocalDateTime.now(), "CODE",
				user, null);

		String expectedToString = "Coupon [code=CODE, getId()=" + coupon.getId() + ", getTitle()=" + coupon.getTitle()
				+ ", getDescription()=" + coupon.getDescription() + ", getUrl()=" + coupon.getUrl() + ", getPrice()="
				+ coupon.getPrice() + ", getCreationDate()=" + coupon.getCreationDate() + ", getUser()="
				+ coupon.getUser() + ", getCategory()=" + coupon.getCategory() + ", getImages()=" + coupon.getImages()
				+ ", getPositiveRatings()=" + coupon.getPositiveRatings() + ", getNegativeRatings()="
				+ coupon.getNegativeRatings() + ", isExpired()=" + coupon.isExpired() + ", getValidationDate()="
				+ coupon.getValidationDate() + ", toString()=" + "]";

		assertEquals(expectedToString, coupon.toString());

	}
}