package es.udc.fi.dc.fd.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.Test;

/**
 * The Class PostTest.
 */
public class PostTest {
	/**
	 * Test add image to post.
	 */
	@Test
	public void testAddImage() {
		Post post = new Post();
		Image image = new Image();

		post.addImage(image);

		assertTrue(post.getImages().contains(image));
		assertEquals(image.getPost(), post);
	}

	/**
	 * Test to string.
	 */
	@Test
	public void testToString() {
		Long id = 1L;
		String title = "title";
		String description = "description";
		String url = "url";
		BigDecimal price = new BigDecimal(10);
		LocalDateTime now = LocalDateTime.now();
		User user = new User("user1", "password", "User", "One", "user1@udc.es", new byte[] {});
		Category category = new Category("Misc");
		LocalDateTime expirationDate = LocalDateTime.of(2025, 3, 1, 0, 0, 0);

		Post post = new Post(title, description, url, price, now, user, category, expirationDate);
		post.setId(id);
		String expected = "Post [id=" + id + ", title=" + title + ", description=" + description + ", url=" + url
				+ ", price=" + price + ", creationDate=" + now + ", positiveRatings=" + 0 + ", negativeRatings=" + 0
				+ ", expirationDate=" + expirationDate + ", validationDate=" + null + ", user=" + user + ", category="
				+ category + ", images=" + (new HashSet<Image>()) + "]";

		assertEquals(expected, post.toString());
	}
}