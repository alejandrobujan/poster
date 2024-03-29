package es.udc.fi.dc.fd.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Offer;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.entities.User.RoleType;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.model.services.UserService;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectLoginException;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.rest.common.JwtGenerator;
import es.udc.fi.dc.fd.rest.common.JwtInfo;
import es.udc.fi.dc.fd.rest.dtos.AuthenticatedUserDto;
import es.udc.fi.dc.fd.rest.dtos.LoginParamsDto;

/**
 * The Class RatingControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RatingControllerTest {

	/** The authenticated user dto */
	private AuthenticatedUserDto authenticatedUser;

	/** The offer */
	private Post offer;

	/** The category */
	private Category category;

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** The Constant NON_EXISTENT_ID. */
	private final static Long NON_EXISTENT_ID = -1L;

	/** The Constant PASSWORD. */
	private final static String PASSWORD = "password";

	/** The password encoder. */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The category dao. */
	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private UserService userService;

	/** The user controller. */
	@Autowired
	private UserController userController;

	/** The jwt generator. */
	@Autowired
	private JwtGenerator jwtGenerator;

	/**
	 * Creates the authenticated user.
	 * 
	 * @param userName the user name
	 * @param roleType the role type
	 * @return the authenticated user dto
	 * @throws IncorrectLoginException the incorrect login exception
	 */
	private AuthenticatedUserDto createAuthenticatedUser(String userName, RoleType roleType)
			throws IncorrectLoginException {
		User user = new User(userName, PASSWORD, "newUser", "user", "user@test.com", null);

		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(roleType);

		userDao.save(user);

		LoginParamsDto loginParams = new LoginParamsDto();
		loginParams.setUserName(user.getUserName());
		loginParams.setPassword(PASSWORD);

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
		return postDao
				.save(new Offer(title, "description", "url", new BigDecimal(10), LocalDateTime.now(), user, category, LocalDateTime.of(2025, 2, 3, 0, 0, 0)));
	}

	/**
	 * Creates the user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	private User createUser(String userName) {
		User user = new User(userName, PASSWORD, "newUser", "user", "user@test.com", null);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setRole(RoleType.USER);

		return userDao.save(user);
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

	/**
	 *
	 * Generate service token.
	 * 
	 * @param user the user
	 * @return the string
	 */
	private String generateServiceToken(User user) {

		JwtInfo jwtInfo = new JwtInfo(user.getId(), user.getUserName(), user.getRole().toString());

		return jwtGenerator.generate(jwtInfo);

	}

	/**
	 * Set up
	 * 
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws IncorrectLoginException           the incorrect login exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 */
	@Before
	public void setUp() throws DuplicateInstanceException, MaximumImageSizeExceededException, IncorrectLoginException,
			InstanceNotFoundException {
		authenticatedUser = createAuthenticatedUser("user", RoleType.USER);
		category = createCategory("category");
		offer = createOffer("offer", userService.loginFromId(authenticatedUser.getUserDto().getId()), category);
	}

	/**
	 * Test post rate positive ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRatePositive_Ok() throws Exception {
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/ratePositive").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isOk());

	}

	/**
	 * Test post rate negative ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRateNegative_Ok() throws Exception {
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/rateNegative").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isOk());
	}

	/**
	 * Test post rate negative twice ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRateNegativeTwice_Ok() throws Exception {
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/rateNegative").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isOk());
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/rateNegative").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isOk());
	}

	/**
	 * Test post rate positive twice ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRatePositiveTwice_Ok() throws Exception {
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/ratePositive").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isOk());
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/ratePositive").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isOk());
	}

	/**
	 * Test post rate positive to negative ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRatePositiveToNegative_Ok() throws Exception {
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/ratePositive").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isOk());
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/rateNegative").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isOk());
	}

	/**
	 * Test post rate negative to positive ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRateNegativeToPositive_Ok() throws Exception {
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/rateNegative").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isOk());
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/ratePositive").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isOk());
	}

	/**
	 * Test post rate positive not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRatePositive_NotOkNotFound() throws Exception {
		mockMvc.perform(post("/api/rating/post/" + NON_EXISTENT_ID + "/ratePositive").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isNotFound());
	}

	/**
	 * Test post rate negative not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRateNegative_NotOkNotFound() throws Exception {
		mockMvc.perform(post("/api/rating/post/" + NON_EXISTENT_ID + "/rateNegative").header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isNotFound());
	}

	/**
	 * Test post rate positive user not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRatePositive_NoUser() throws Exception {
		User nobody = createUser("nobody");
		nobody.setId(NON_EXISTENT_ID);

		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/ratePositive").header("Authorization",
				"Bearer " + generateServiceToken(nobody))).andExpect(status().isNotFound());

	}

	/**
	 * Test post rate negative user not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRateNegative_NoUser() throws Exception {
		User nobody = createUser("nobody");
		nobody.setId(NON_EXISTENT_ID);

		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/rateNegative").header("Authorization",
				"Bearer " + generateServiceToken(nobody))).andExpect(status().isNotFound());

	}

}
