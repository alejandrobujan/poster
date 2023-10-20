package es.udc.fi.dc.fd.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Offer;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.entities.User.RoleType;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.rest.common.JwtGenerator;
import es.udc.fi.dc.fd.rest.common.JwtInfo;

/**
 * The Class RatingControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class RatingControllerTest {

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

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

	/** The jwt generator. */
	@Autowired
	private JwtGenerator jwtGenerator;

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
	 * Creates the offer.
	 *
	 * @param user the user of the post
	 * @return the offer
	 */
	private Offer createOffer(User user) {
		return postDao.save(new Offer("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user,
				createCategory("Hola")));
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
	 * Test post rate positive ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRatePositive_Ok() throws Exception {

		User user = createUser("admin");
		Offer offer = createOffer(user);

		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/ratePositive").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isOk());

	}

	/**
	 * Test post rate negative ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRateNegative_Ok() throws Exception {

		User user = createUser("admin");
		Offer offer = createOffer(user);

		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/rateNegative").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isOk());

	}

	/**
	 * Test post rate negative twice ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRateNegativeTwice_Ok() throws Exception {

		User user = createUser("admin");
		Offer offer = createOffer(user);

		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/rateNegative").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isOk());
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/rateNegative").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isOk());

	}

	/**
	 * Test post rate positive twice ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRatePositiveTwice_Ok() throws Exception {

		User user = createUser("admin");
		Offer offer = createOffer(user);

		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/ratePositive").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isOk());
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/ratePositive").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isOk());

	}

	/**
	 * Test post rate positive to negative ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRatePositiveToNegative_Ok() throws Exception {

		User user = createUser("admin");
		Offer offer = createOffer(user);

		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/ratePositive").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isOk());
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/rateNegative").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isOk());

	}

	/**
	 * Test post rate negative to positive ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRateNegativeToPositive_Ok() throws Exception {

		User user = createUser("admin");
		Offer offer = createOffer(user);

		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/rateNegative").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isOk());
		mockMvc.perform(post("/api/rating/post/" + offer.getId() + "/ratePositive").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isOk());

	}

	/**
	 * Test post rate positive not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRatePositive_NotOkNotFound() throws Exception {

		User user = createUser("admin");

		mockMvc.perform(post("/api/rating/post/-1/ratePositive").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isNotFound());

	}

	/**
	 * Test post rate negative not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRateNegative_NotOkNotFound() throws Exception {

		User user = createUser("admin");

		mockMvc.perform(post("/api/rating/post/-1/rateNegative").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isNotFound());

	}

	/**
	 * Test post rate positive user not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRatePositive_NoUser() throws Exception {

		User user = createUser("admin");

		user.setId(-1L);

		mockMvc.perform(post("/api/rating/post/-1/ratePositive").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isNotFound());

	}

	/**
	 * Test post rate negative user not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostRateNegative_NoUser() throws Exception {

		User user = createUser("admin");

		user.setId(-1L);

		mockMvc.perform(post("/api/rating/post/-1/rateNegative").header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isNotFound());

	}

	/**
	 * Generate service token.
	 *
	 * @param user the user
	 * @return the string
	 */
	private String generateServiceToken(User user) {

		JwtInfo jwtInfo = new JwtInfo(user.getId(), user.getUserName(), user.getRole().toString());

		return jwtGenerator.generate(jwtInfo);

	}
}
