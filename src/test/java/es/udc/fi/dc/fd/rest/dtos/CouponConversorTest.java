package es.udc.fi.dc.fd.rest.dtos;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.Coupon;
import es.udc.fi.dc.fd.model.entities.User;

/**
 * The class CouponConversorTest
 */
public class CouponConversorTest {

	/**
	 * Test to post dto
	 */
	@Test
	public void testToPostDtoValidationDateNotNull() {
		Category category = new Category(1L, "Electronics");
		User user = new User("user1", "password", "User", "One", "user1@udc.es", new byte[] {});
		Coupon coupon = new Coupon("Coupon Title", "Coupon Description", "http://example.com", new BigDecimal("10.99"),
				LocalDateTime.now(), "COUPONCODE", user, category, LocalDateTime.now());
		CouponConversor couponConversor = new CouponConversor();

		LocalDateTime validationDate = LocalDateTime.now();
		coupon.setValidationDate(validationDate);

		PostDto postDto = couponConversor.toPostDto(coupon);

		assertNotNull(postDto.getValidationDate());
	}
}
