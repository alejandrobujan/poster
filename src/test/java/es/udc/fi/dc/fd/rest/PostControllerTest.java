package es.udc.fi.dc.fd.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import es.udc.fi.dc.fd.model.entities.Offer;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.entities.User.RoleType;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectLoginException;
import es.udc.fi.dc.fd.rest.common.JwtGenerator;
import es.udc.fi.dc.fd.rest.common.JwtInfo;
import es.udc.fi.dc.fd.rest.controllers.UserController;
import es.udc.fi.dc.fd.rest.dtos.AuthenticatedUserDto;
import es.udc.fi.dc.fd.rest.dtos.LoginParamsDto;
import es.udc.fi.dc.fd.rest.dtos.PostParamsDto;
import es.udc.fi.dc.fd.rest.dtos.PostUpdateDto;

/**
 * The Class PostControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PostControllerTest {

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

	/** The Constant PASSWORD. */
	private final static String PASSWORD = "password";

	/** The password encoder. */
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	/** The jwt generator. */
	@Autowired
	private JwtGenerator jwtGenerator;

	/** The user dao. */
	@Autowired
	private UserDao userDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private CategoryDao categoryDao;

	/** The user controller. */
	@Autowired
	private UserController userController;

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
	 * Generate service token.*
	 * 
	 * @param user the user
	 * @return the string
	 */
	private String generateServiceToken(User user) {

		JwtInfo jwtInfo = new JwtInfo(user.getId(), user.getUserName(), user.getRole().toString());

		return jwtGenerator.generate(jwtInfo);

	}

	private Offer createOffer(User user) {
		return postDao.save(new Offer("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user,
				createCategory("Hola")));
	}

	private Category createCategory(String name) {
		return categoryDao.save(new Category(name));
	}

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
	public void testGetFindAllPosts_Ok() throws Exception {

		mockMvc.perform(get("/api/posts/feed")).andExpect(status().isOk());

	}

	/**
	 * Test post find all categories ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testGetFindAllCategories_Ok() throws Exception {

		mockMvc.perform(get("/api/posts/categories")).andExpect(status().isOk());

	}

	/**
	 * Test post Create Post ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostCreatePost_Ok() throws Exception {

		AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);

		List<byte[]> image = new ArrayList<byte[]>();

		PostParamsDto postParams = new PostParamsDto();
		postParams.setCategoryId(1L);
		postParams.setDescription("Tarta de Santiago");
		postParams.setImages(image);
		postParams.setPrice(new BigDecimal(10));
		postParams.setTitle("Tarta");
		postParams.setUrl("http://poster.com");
		postParams.setType("Coupon");
		postParams.setProperties(Map.of("code", "APP25"));

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post").header("Authorization", "Bearer " + user.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postParams)))
				.andExpect(status().isOk());

	}

	/**
	 * Test post Create Post Not ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostCreatePost_NotOk() throws Exception {

		AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);

		PostParamsDto postParams = new PostParamsDto();
		postParams.setCategoryId(1L);
		postParams.setDescription("Tarta de Santiago");
		postParams.setImages(null);
		postParams.setPrice(new BigDecimal(10));
		postParams.setTitle("Tarta de Santiago");
		postParams.setUrl("http://poster.com");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post").header("Authorization", "Bearer " + user.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postParams)))
				.andExpect(status().isBadRequest());

	}

	@Test
	public void testGetFindPostById_Ok() throws Exception {

		User user = createUser("admin");

		Offer offer = createOffer(user);

		mockMvc.perform(get("/api/posts/postDetail/" + offer.getId())).andExpect(status().isOk());

	}

	@Test
	public void testGetFindPostById_NotOk() throws Exception {
		mockMvc.perform(get("/api/posts/postDetail/-10")).andExpect(status().isNotFound());

	}

	@Test
	public void testDeleteDeletePost_Ok() throws Exception {

		User user = createUser("admin");
		Offer offer = createOffer(user);

		mockMvc.perform(delete("/api/posts/post/" + offer.getId()).header("Authorization",
				"Bearer " + generateServiceToken(user))).andExpect(status().isNoContent());

	}

	@Test
	public void testDeleteDeletePost_NotOkForbidden() throws Exception {

		User user = createUser("admin");
		Offer offer = createOffer(user);

		User user2 = createUser("pepe");

		mockMvc.perform(delete("/api/posts/post/" + offer.getId()).header("Authorization",
				"Bearer " + generateServiceToken(user2))).andExpect(status().isForbidden());

	}

	@Test
	public void testDeleteDeletePost_NotOkNotFound() throws Exception {

		User user = createUser("admin");

		mockMvc.perform(delete("/api/posts/post/-1").header("Authorization", "Bearer " + generateServiceToken(user)));
	}

	/**
	 * Test put Update Post ok.
	 *
	 */
	@Test
	public void testPutUpdatePost_Ok() throws Exception {

		AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);

		Offer offer = createOffer(userDao.findById(user.getUserDto().getId()).get());

		List<byte[]> image = new ArrayList<byte[]>();

		PostUpdateDto postUpdateParams = new PostUpdateDto();
		postUpdateParams.setAuthorId(user.getUserDto().getId());
		postUpdateParams.setCategoryId(1L);
		postUpdateParams.setDescription("Tarta de Santiago");
		postUpdateParams.setImages(image);
		postUpdateParams.setPrice(new BigDecimal(10));
		postUpdateParams.setTitle("Tarta");
		postUpdateParams.setUrl("http://poster.com");
		postUpdateParams.setType("Offer");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(
				put("/api/posts/post/" + offer.getId()).header("Authorization", "Bearer " + user.getServiceToken())
						.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postUpdateParams)))
				.andExpect(status().isOk());
	}

	/**
	 * Test put Update Post Forbidden.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPutUpdatePost_Forbidden() throws Exception {

		AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);

		Offer offer = createOffer(userDao.findById(user.getUserDto().getId()).get());

		List<byte[]> image = new ArrayList<byte[]>();

		PostUpdateDto postUpdateParams = new PostUpdateDto();
		postUpdateParams.setAuthorId(-1L);
		postUpdateParams.setCategoryId(1L);
		postUpdateParams.setDescription("Tarta de Santiago");
		postUpdateParams.setImages(image);
		postUpdateParams.setPrice(new BigDecimal(10));
		postUpdateParams.setTitle("Tarta");
		postUpdateParams.setUrl("http://poster.com");
		postUpdateParams.setType("Offer");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(
				put("/api/posts/post/" + offer.getId()).header("Authorization", "Bearer " + user.getServiceToken())
						.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postUpdateParams)))
				.andExpect(status().isForbidden());

	}

	/**
	 * Test put Update Post NotFound Post.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPutUpdatePost_isNotFoundPost() throws Exception {

		AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);

		createOffer(userDao.findById(user.getUserDto().getId()).get());

		List<byte[]> image = new ArrayList<byte[]>();

		PostUpdateDto postUpdateParams = new PostUpdateDto();
		postUpdateParams.setAuthorId(user.getUserDto().getId());
		postUpdateParams.setCategoryId(1L);
		postUpdateParams.setDescription("Tarta de Santiago");
		postUpdateParams.setImages(image);
		postUpdateParams.setPrice(new BigDecimal(10));
		postUpdateParams.setTitle("Tarta");
		postUpdateParams.setUrl("http://poster.com");
		postUpdateParams.setType("Offer");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/posts/post/-1").header("Authorization", "Bearer " + user.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postUpdateParams)))
				.andExpect(status().isNotFound());

	}

}
