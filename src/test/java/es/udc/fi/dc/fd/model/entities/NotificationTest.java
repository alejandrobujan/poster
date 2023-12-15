package es.udc.fi.dc.fd.model.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NotificationTest {

	/** List of users */
	List<User> users;
	/** List of posts */
	List<Post> posts;
	/** List of comments */
	List<Comment> comments;
	/** Notification */
	Notification notification;

	/**
	 * Set up.
	 */
	@BeforeEach
	public void setUp() {
		users = List.of(new User("user1", "password", "User", "One", "user1@udc.es", new byte[] {}),
				new User("user2", "password", "User", "Two", "user2@udc.es", new byte[] {}),
				new User("user3", "password", "User", "Three", "user3@udc.es", new byte[] {}));
		posts = List.of(
				new Offer("Title1", "Description1", null, new BigDecimal(10), LocalDateTime.now(), users.get(1), null,
						LocalDateTime.now()),
				new Coupon("Title2", "Description2", null, new BigDecimal(10), LocalDateTime.now(), "CODE",
						users.get(1), null, LocalDateTime.now()));
		comments = List.of(new Comment("Comment1", LocalDateTime.now(), users.get(0), posts.get(0), null, 0, 0),
				new Comment("Comment2", LocalDateTime.now(), users.get(0), posts.get(1), null, 0, 0));
		notification = new Notification("Notification", false, LocalDateTime.of(2025, 2, 4, 0, 0, 0), users.get(0),
				users.get(1), posts.get(0), comments.get(0));
	}

	/**
	 * Test default constructor not null.
	 */
	@Test
	public void testDefaultConstructorNotNull() {
		Notification notification = new Notification();
		assertNotNull(notification);
	}

	/**
	 * Test parameterized constructor not null.
	 */
	@Test
	public void testParameterizedConstructorNotNull() {
		LocalDateTime creationDate = LocalDateTime.now();
		User notifierUser = users.get(0);
		User notifiedUser = users.get(1);
		Post post = posts.get(0);
		Comment comment = comments.get(0);

		Notification notification = new Notification("text", true, creationDate, notifierUser, notifiedUser, post,
				comment);

		assertNotNull(notification);
	}

	/**
	 * Test set id.
	 */
	@Test
	public void testSetId() {
		notification.setId(123L);
		assertEquals(123L, notification.getId());
	}

	/**
	 * Test get text.
	 */
	@Test
	public void testGetText() {
		assertEquals("Notification", notification.getText());
	}

	/**
	 * Test set text.
	 */
	@Test
	public void testSetText() {
		notification.setText("Test");
		assertEquals("Test", notification.getText());
	}

	/**
	 * Test notification is viewed.
	 */
	@Test
	public void testIsViewed() {
		assertFalse(notification.isViewed());
	}

	/**
	 * Test set viewed.
	 */
	@Test
	public void testSetViewed() {
		notification.setViewed(true);
		assertTrue(notification.isViewed());
	}

	/**
	 * Test get creation date.
	 */
	@Test
	public void testGetCreationDate() {
		LocalDateTime expectedCreationDate = LocalDateTime.of(2025, 2, 4, 0, 0, 0);
		assertEquals(expectedCreationDate, notification.getCreationDate());
	}

	/**
	 * Test set creation date.
	 */
	@Test
	public void testSetCreationDate() {
		LocalDateTime newCreationDate = LocalDateTime.now();
		notification.setCreationDate(newCreationDate);
		assertEquals(newCreationDate, notification.getCreationDate());
	}

	/**
	 * Test get notifier user.
	 */
	@Test
	public void testGetNotifierUser() {
		assertEquals(users.get(0), notification.getNotifierUser());
	}

	/**
	 * Test set notifier user.
	 */
	@Test
	public void testSetNotifierUser() {
		User newUser = users.get(2);
		notification.setNotifierUser(newUser);
		assertEquals(newUser, notification.getNotifierUser());
	}

	/**
	 * Test get notified user.
	 */
	@Test
	public void testGetNotifiedUser() {
		assertEquals(users.get(1), notification.getNotifiedUser());
	}

	/**
	 * Test set notified user.
	 */
	@Test
	public void testSetNotifiedUser() {
		User newUser = users.get(2);
		notification.setNotifiedUser(newUser);
		assertEquals(newUser, notification.getNotifiedUser());
	}

	/**
	 * Test get post.
	 */
	@Test
	public void testGetPost() {
		assertEquals(posts.get(0), notification.getPost());
	}

	/**
	 * Test set post.
	 */
	@Test
	public void testSetPost() {
		Post newPost = posts.get(1);
		notification.setPost(newPost);
		assertEquals(newPost, notification.getPost());
	}

	/**
	 * Test get comment.
	 */
	@Test
	public void testGetComment() {
		assertEquals(comments.get(0), notification.getComment());
	}

	/**
	 * Test set comment.
	 */
	@Test
	public void testSetComment() {
		Comment newComment = comments.get(1);
		notification.setComment(newComment);
		assertEquals(newComment, notification.getComment());
	}

}