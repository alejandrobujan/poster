package es.udc.fi.dc.fd.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Image;
import es.udc.fi.dc.fd.model.entities.Offer;
import es.udc.fi.dc.fd.model.entities.Coupon;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.entities.User.RoleType;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.rest.dtos.SearchFiltersDto;
import es.udc.fi.dc.fd.rest.dtos.SearchParamsDto;

/**
 * The Class CatalogControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CatalogControllerTest {

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
	 * Creates the category.
	 *
	 * @param name the name of the category
	 * @return the category
	 */
	private Category createCategory(String name) {
		return categoryDao.save(new Category(name));
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
	 * Test get find all posts ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostFindAllPosts_Ok() throws Exception {
		SearchParamsDto searchParamsDto = new SearchParamsDto(
				new SearchFiltersDto(null, null, Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")),
						null, false, "creationDate", "DESC"),
				"", 0);
		
		User user = createUser("admin");
		postDao.save(new Coupon("Title", "Lorem Ipsum dolor sit amet", "url", new BigDecimal(10), LocalDateTime.now(), "EXTRA", user, createCategory("Misc"), LocalDateTime.of(2025, 2, 3, 0, 0, 0)));
		Post offer = postDao.save(new Offer("Title", "Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. ", "url", new BigDecimal(10), LocalDateTime.now(), user, createCategory("Misc"), LocalDateTime.of(2025, 2, 3, 0, 0, 0)));
		Set<Image> images = new HashSet<>();
		images.add(new Image(new byte[]{1, 2, 3}, offer));
		images.add(new Image(new byte[]{4, 5, 6}, offer));
		offer.setImages(images);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/catalog/feed").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(searchParamsDto))).andExpect(status().isOk());
	}

	/**
	 * Test post find all categories ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetFindAllCategories_Ok() throws Exception {
		mockMvc.perform(get("/api/catalog/categories")).andExpect(status().isOk());
	}

	/**
	 * Test get find post by id ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetFindPostById_Ok() throws Exception {
		User user = createUser("admin");
		Post offer = createOffer("offer", user, createCategory("category"));

		mockMvc.perform(get("/api/catalog/postDetail/" + offer.getId())).andExpect(status().isOk());

	}

	/**
	 * Test get find alternative post by id ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetFindAlternativePostById_Ok() throws Exception {
		User user = createUser("admin");
		Post offer = postDao.save(new Offer("Title", "Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. Lorem Ipsum dolor sit amet. ", "url", new BigDecimal(10), LocalDateTime.now(), user, createCategory("Misc"), LocalDateTime.of(2025, 2, 3, 0, 0, 0)));
		Set<Image> images = new HashSet<>();
		images.add(new Image(new byte[]{1, 2, 3}, offer));
		images.add(new Image(new byte[]{4, 5, 6}, offer));
		offer.setImages(images);

		mockMvc.perform(get("/api/catalog/postDetail/" + offer.getId())).andExpect(status().isOk());

	}

	/**
	 * Test get find post by id not ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetFindPostById_NotOk() throws Exception {
		mockMvc.perform(get("/api/catalog/postDetail/" + NON_EXISTENT_ID)).andExpect(status().isNotFound());
	}
}

