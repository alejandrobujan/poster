package es.udc.fi.dc.fd.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RatingTest {
	private List<User> users;
	private List<Post> posts;
	private Rating rating;

	@Before
	public void setUp() {
		users = List.of(new User("user1", "password", "User", "One", "user1@udc.es", new byte[] {}),
				new User("user2", "password", "User", "Two", "user2@udc.es", new byte[] {}),
				new User("user3", "password", "User", "Three", "user3@udc.es", new byte[] {}));
		posts = List.of(
				new Offer("Title1", "Description1", null, new BigDecimal(10), LocalDateTime.now(), users.get(1), null, LocalDateTime.of(2025, 3, 1, 0, 0, 0)),
				new Coupon("Title2", "Description2", null, new BigDecimal(10), LocalDateTime.now(), "CODE",
						users.get(1), null, LocalDateTime.of(2025, 3, 1, 0, 0, 0)));
		rating = new Rating(true, users.get(0), posts.get(0));
	}

	@Test
	public void testConstructorWithoutParameters() {
		assertNotNull(new Rating());
	}

	@Test
	public void testParametrizedConstructor() {
		rating = new Rating(true, users.get(0), posts.get(0));
		assertNotNull(rating);
	}

	@Test
	public void testGetUser() {
		assertEquals(users.get(0), rating.getUser());
	}

	@Test
	public void testIsPositive() {
		assertTrue(rating.isPositive());
	}

	@Test
	public void testGetPost() {
		assertEquals(posts.get(0), rating.getPost());
	}

	@Test
	public void testSetId() {
		rating.setId(100L);
		assertEquals(100L, rating.getId());
	}

	@Test
	public void testSetUser() {
		rating.setUser(users.get(1));
		assertEquals(users.get(1), rating.getUser());
	}

	@Test
	public void testSetPositive() {
		rating.setPositive(false);
		assertFalse(rating.isPositive());
	}

	@Test
	public void testSetPost() {
		rating.setPost(posts.get(1));
		assertEquals(posts.get(1), rating.getPost());
	}

	@Test
	public void testToString() {
		assertEquals("Rating [id=" + rating.getId() + ", positive=" + rating.isPositive() + ", user=" + rating.getUser()
				+ ", post=" + rating.getPost() + "]", rating.toString());
	}
}
