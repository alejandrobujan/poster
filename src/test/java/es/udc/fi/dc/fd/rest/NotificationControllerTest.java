package es.udc.fi.dc.fd.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.DuplicateInstanceException;
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
import es.udc.fi.dc.fd.model.entities.User.RoleType;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.model.services.NotificationService;
import es.udc.fi.dc.fd.model.services.UserService;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectLoginException;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.rest.controllers.UserController;
import es.udc.fi.dc.fd.rest.dtos.AuthenticatedUserDto;
import es.udc.fi.dc.fd.rest.dtos.LoginParamsDto;

/**
 * The Class NotificationControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class NotificationControllerTest {

	private List<AuthenticatedUserDto> users;
	private List<Post> posts;
	private List<Category> categories;
	private List<Comment> comments;

	private static Long INEXISTENT_ID = 1L;

	/** The user service. */
	@Autowired
	private UserService userService;

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

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

	/** The password encoder. */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/** The user controller. */
	@Autowired
	private UserController userController;

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/** The comment dao. */
	@Autowired
	private CommentDao commentDao;

	/**
	 * Creates the authenticated user.
	 *
	 * @param userName the user name
	 * @param roleType the role type
	 * @return the authenticated user dto
	 * @throws IncorrectLoginException the incorrect login exception
	 */
	private AuthenticatedUserDto createAuthenticatedUser(String userName) throws IncorrectLoginException {
		User user = new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com", null);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(RoleType.USER);

		userDao.save(user);

		LoginParamsDto loginParams = new LoginParamsDto();
		loginParams.setUserName(user.getUserName());
		loginParams.setPassword("password");

		return userController.login(loginParams);
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
		return postDao.save(new Offer(title, "description", "url", new BigDecimal(10), LocalDateTime.now(), user,
				category, LocalDateTime.now()));
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
	public void setUp() throws DuplicateInstanceException, MaximumImageSizeExceededException, IncorrectLoginException {
		users = List.of(createAuthenticatedUser("user"), createAuthenticatedUser("user2"));
		categories = List.of(createCategory("Meals"), createCategory("Motor"), createCategory("Home"),
				createCategory("Toys"), createCategory("Tech"), createCategory("Leisure"));
		posts = List.of(
				createOffer("offer1", userDao.getReferenceById(users.get(0).getUserDto().getId()), categories.get(0)),
				createCoupon("coupon2", userDao.getReferenceById(users.get(1).getUserDto().getId()), categories.get(1)),
				createCoupon("coupon3", userDao.getReferenceById(users.get(0).getUserDto().getId()),
						categories.get(2)));
		comments = List.of(
				createComment("Amazing!!", userDao.getReferenceById(users.get(1).getUserDto().getId()), posts.get(0)),
				createComment("Great!!", userDao.getReferenceById(users.get(1).getUserDto().getId()), posts.get(0)));
	}

	/**
	 * Test get find all posts ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostFindUnviewedNotifications_Ok() throws Exception {
		createNotification("notification1", userDao.getReferenceById(users.get(1).getUserDto().getId()),
				userDao.getReferenceById(users.get(0).getUserDto().getId()), posts.get(0), comments.get(0));
		createNotification("notification2", userDao.getReferenceById(users.get(1).getUserDto().getId()),
				userDao.getReferenceById(users.get(0).getUserDto().getId()), posts.get(0), comments.get(1));

		mockMvc.perform(get("/api/notifications/notifications").header("Authorization",
				"Bearer " + users.get(0).getServiceToken())).andExpect(status().isOk());
	}

	@Test
	public void testPostMarkAsViewedNotification_Ok() throws Exception {
		Notification notification = createNotification("notification1",
				userDao.getReferenceById(users.get(1).getUserDto().getId()),
				userDao.getReferenceById(users.get(0).getUserDto().getId()), posts.get(0), comments.get(0));

		mockMvc.perform(post("/api/notifications/" + notification.getId() + "/view").header("Authorization",
				"Bearer " + users.get(0).getServiceToken())).andExpect(status().isNoContent());
	}

	@Test
	public void testPostMarkAsViewedNotification_NoNotification() throws Exception {

		mockMvc.perform(post("/api/notifications/" + INEXISTENT_ID + "/view").header("Authorization",
				"Bearer " + users.get(0).getServiceToken())).andExpect(status().isNotFound());
	}

	@Test
	public void testPostMarkAsViewedNotification_NoUser() throws Exception {
		Notification notification = createNotification("notification1",
				userDao.getReferenceById(users.get(1).getUserDto().getId()),
				userDao.getReferenceById(users.get(0).getUserDto().getId()), posts.get(0), comments.get(0));

		mockMvc.perform(post("/api/notifications/" + notification.getId() + "/view").header("Authorization",
				"Bearer " + users.get(1).getServiceToken())).andExpect(status().isForbidden());
	}

}
