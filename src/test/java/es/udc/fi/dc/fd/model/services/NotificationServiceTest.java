package es.udc.fi.dc.fd.model.services;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.fi.dc.fd.model.common.exceptions.DuplicateInstanceException;
import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.entities.CommentDao;
import es.udc.fi.dc.fd.model.entities.Coupon;
import es.udc.fi.dc.fd.model.entities.Notification;
import es.udc.fi.dc.fd.model.entities.NotificationDao;
import es.udc.fi.dc.fd.model.entities.Offer;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;
import jakarta.transaction.Transactional;

/**
 * The Class NotificationServiceTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class NotificationServiceTest {

	private List<User> users;
	private List<Post> posts;
	private List<Category> categories;
	private List<Comment> comments;

	private static Long INEXISTENT_ID = 1L;

	/** The user service. */
	@Autowired
	private UserService userService;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The category dao. */
	@Autowired
	private CategoryDao categoryDao;

	/** The notification dao. */
	@Autowired
	private NotificationDao notificationDao;

	/** The notification service. */
	@Autowired
	private NotificationService notificationService;

	/** The comment dao. */
	@Autowired
	private CommentDao commentDao;

	/**
	 * Sign up user
	 * 
	 * @param userName the user name
	 * @return the registered user
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 */
	private User signUpUser(String userName) throws MaximumImageSizeExceededException, DuplicateInstanceException {

		byte avatar[] = new byte[] { 50 };
		User user = new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com", avatar);

		userService.signUp(user);

		return user;
	}

	/**
	 * Creates the offer.
	 *
	 * @param title    the title of the post
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the offer
	 */
	private Post createOffer(String title, User user, Category category) {
		return postDao
				.save(new Offer(title, "description", "url", new BigDecimal(10), LocalDateTime.now(), user, category, LocalDateTime.now()));
	}

	/**
	 * Creates the coupon.
	 *
	 * @param title    the title of the post
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
	 */
	private Post createCoupon(String title, User user, Category category) {
		return postDao.save(new Coupon(title, "description", "url", new BigDecimal(10), LocalDateTime.now(), "EXTRA25",
				user, category, LocalDateTime.now()));
	}

	/**
	 * Creates the category.
	 *
	 * @param name the name of the category
	 * @return the category
	 */
	private Category createCategory(String name) {
		return categoryDao.save(new Category(name));
	}

	private Comment createComment(String description, User user, Post post) {
		return commentDao.save(new Comment(description, LocalDateTime.now(), user, post, null, 1, 0));
	}

	private Notification createNotification(String text, User notifier, User notified, Post post, Comment comment) {
		return notificationDao
				.save(new Notification(text, false, LocalDateTime.now(), notifier, notified, post, comment));
	}

	@Before
	public void setUp() throws DuplicateInstanceException, MaximumImageSizeExceededException {
		users = List.of(signUpUser("user"), signUpUser("user2"));
		categories = List.of(createCategory("Meals"), createCategory("Motor"), createCategory("Home"),
				createCategory("Toys"), createCategory("Tech"), createCategory("Leisure"));
		posts = List.of(createOffer("offer1", users.get(0), categories.get(0)),
				createCoupon("coupon2", users.get(1), categories.get(1)),
				createCoupon("coupon3", users.get(0), categories.get(2)));
		comments = List.of(createComment("Great!!", users.get(1), posts.get(0)),
				createComment("Amazing!!", users.get(1), posts.get(0)));

	}

	@Test
	public void testFindNotifications() {
		Notification notification1 = createNotification("notification1", users.get(1), users.get(0), posts.get(0),
				comments.get(0));
		Notification notification2 = createNotification("notification1", users.get(1), users.get(0), posts.get(0),
				comments.get(1));

		List<Notification> notifications = notificationService.findUnviewedNotifications(users.get(0).getId());

		assertTrue(notifications.contains(notification1));
		assertTrue(notifications.contains(notification2));
		assertEquals(2, notifications.size());
	}

	@Test
	public void testFindNotificationsWithViewed() {
		Notification notification1 = createNotification("notification1", users.get(1), users.get(0), posts.get(0),
				comments.get(0));
		Notification notification2 = createNotification("notification1", users.get(1), users.get(0), posts.get(0),
				comments.get(1));
		notification2.setViewed(true);

		List<Notification> notifications = notificationService.findUnviewedNotifications(users.get(0).getId());

		assertTrue(notifications.contains(notification1));
		assertFalse(notifications.contains(notification2));
		assertEquals(1, notifications.size());
	}

	@Test
	public void testFindNotificationsEmpty() {
		createNotification("notification1", users.get(1), users.get(0), posts.get(0), comments.get(0));
		createNotification("notification1", users.get(1), users.get(0), posts.get(0), comments.get(1));

		List<Notification> notifications = notificationService.findUnviewedNotifications(users.get(1).getId());

		assertTrue(notifications.isEmpty());
	}

	@Test
	public void testMarkNotificationAsViewed() throws InstanceNotFoundException, PermissionException {
		Notification notification = createNotification("notification", users.get(1), users.get(0), posts.get(0),
				comments.get(0));

		assertFalse(notification.isViewed());

		notificationService.markAsViewed(notification.getId(), users.get(0).getId());

		Notification notificationFound = notificationDao.findById(notification.getId()).get();

		assertEquals(notification.getId(), notificationFound.getId());
		assertEquals(notification.getNotifiedUser(), notificationFound.getNotifiedUser());
		assertEquals(notification.getNotifierUser(), notificationFound.getNotifierUser());
		assertEquals(notification.getComment(), notificationFound.getComment());
		assertEquals(notification.getCreationDate(), notificationFound.getCreationDate());
		assertEquals(notification.getPost(), notificationFound.getPost());
		assertEquals(notification.getText(), notificationFound.getText());

		assertTrue(notificationFound.isViewed());

	}

	@Test
	public void testMarkNotificationAsViewedInexistentNotification()
			throws InstanceNotFoundException, PermissionException {
		Notification notification = createNotification("notification", users.get(1), users.get(0), posts.get(0),
				comments.get(0));

		assertFalse(notification.isViewed());

		assertThrows(InstanceNotFoundException.class,
				() -> notificationService.markAsViewed(INEXISTENT_ID, users.get(0).getId()));

	}

	@Test
	public void testMarkNotificationAsViewedNoUserNotification() throws InstanceNotFoundException, PermissionException {
		Notification notification = createNotification("notification", users.get(1), users.get(0), posts.get(0),
				comments.get(0));

		assertFalse(notification.isViewed());

		assertThrows(PermissionException.class,
				() -> notificationService.markAsViewed(notification.getId(), users.get(1).getId()));

	}

	@Test
	public void testConstructor() {
		Notification notification = new Notification("notification1", users.get(1), users.get(0), posts.get(0));

		assertEquals("notification1", notification.getText());
		assertEquals(users.get(1), notification.getNotifierUser());
		assertEquals(users.get(0), notification.getNotifiedUser());
		assertEquals(posts.get(0), notification.getPost());
		assertFalse(notification.isViewed());

	}

}
