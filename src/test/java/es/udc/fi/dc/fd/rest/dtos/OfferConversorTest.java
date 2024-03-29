package es.udc.fi.dc.fd.rest.dtos;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import es.udc.fi.dc.fd.model.entities.Offer;
import es.udc.fi.dc.fd.model.entities.User;

/**
 * The class OfferConversorTest
 */
public class OfferConversorTest {

	/**
	 * Test to post dto
	 */
	@Test
	public void testToPostDtoValidationDateNotNull() {
		User user = new User("user1", "password", "User", "One", "user1@udc.es", new byte[] {});
		Offer offer = new Offer("Title1", "Description1", null, new BigDecimal(10), LocalDateTime.now(), user, null,
				LocalDateTime.now());
		OfferConversor offerConversor = new OfferConversor();

		LocalDateTime validationDate = LocalDateTime.now();
		offer.setValidationDate(validationDate);

		PostDto postDto = offerConversor.toPostDto(offer);

		assertNotNull(postDto.getValidationDate());
	}
}