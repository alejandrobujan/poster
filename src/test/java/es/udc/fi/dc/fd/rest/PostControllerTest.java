package es.udc.fi.dc.fd.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
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
import es.udc.fi.dc.fd.rest.controllers.UserController;
import es.udc.fi.dc.fd.rest.dtos.AuthenticatedUserDto;
import es.udc.fi.dc.fd.rest.dtos.LoginParamsDto;
import es.udc.fi.dc.fd.rest.dtos.PostExpiredDto;
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

	/** The authenticated user */
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

	/** The user service. */
	@Autowired
	private UserService userService;

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
	 * Creates the offer.
	 *
	 * @param title    the title of the post
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the offer
	 */

	private Post createOffer(String title, User user, Category category) {
		return postDao
				.save(new Offer(title, "description", "url", new BigDecimal(10), LocalDateTime.now(), user, category));
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
	 * Set up
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
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
	 * Test post Create Post ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostCreatePost_Ok() throws Exception {
		PostParamsDto postParams = new PostParamsDto();
		postParams.setCategoryId(categoryDao.save(new Category("Meals")).getId());
		postParams.setDescription("Tarta de Santiago");
		postParams.setImages(new ArrayList<byte[]>());
		postParams.setPrice(new BigDecimal(10));
		postParams.setTitle("Tarta");
		postParams.setUrl("http://poster.com");
		postParams.setType("Coupon");
		postParams.setProperties(Map.of("code", "APP25"));

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post").header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
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
		PostParamsDto postParams = new PostParamsDto();
		postParams.setCategoryId(categoryDao.save(new Category("Meals")).getId());
		postParams.setDescription("Tarta de Santiago");
		postParams.setImages(new ArrayList<>());
		postParams.setPrice(new BigDecimal(10));
		postParams.setTitle("Tarta de Santiago");
		postParams.setUrl("http://poster.com");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post").header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postParams)))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Test post Create Post Not ok Whitespace Title.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostCreatePost_NotOk_WhitespaceTitle() throws Exception {

		AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);

		List<byte[]> image = new ArrayList<byte[]>();

		PostParamsDto postParams = new PostParamsDto();
		postParams.setCategoryId(categoryDao.save(new Category("Meals")).getId());
		postParams.setDescription("Tarta de Santiago");
		postParams.setImages(image);
		postParams.setPrice(new BigDecimal(10));
		postParams.setTitle(" ");
		postParams.setUrl("http://poster.com");
		postParams.setType("Coupon");
		postParams.setProperties(Map.of("code", "APP25"));

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post").header("Authorization", "Bearer " + user.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postParams)))
				.andExpect(status().isBadRequest());

	}

	/**
	 * Test post Create Post Not ok Whitespace Description.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostCreatePost_NotOk_WhitespaceDescription() throws Exception {

		AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);

		List<byte[]> image = new ArrayList<byte[]>();

		PostParamsDto postParams = new PostParamsDto();
		postParams.setCategoryId(categoryDao.save(new Category("Meals")).getId());
		postParams.setDescription(" ");
		postParams.setImages(image);
		postParams.setPrice(new BigDecimal(10));
		postParams.setTitle("Tarta de Santiago");
		postParams.setUrl("http://poster.com");
		postParams.setType("Coupon");
		postParams.setProperties(Map.of("code", "APP25"));

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post").header("Authorization", "Bearer " + user.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postParams)))
				.andExpect(status().isBadRequest());

	}

	/**
	 * Test post Delete Post ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDeleteDeletePost_Ok() throws Exception {
		mockMvc.perform(delete("/api/posts/post/" + offer.getId()).header("Authorization",
				"Bearer " + authenticatedUser.getServiceToken())).andExpect(status().isNoContent());
	}

	/**
	 * Test post Delete Post not ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDeleteDeletePost_NotOkForbidden() throws Exception {
		mockMvc.perform(delete("/api/posts/post/" + offer.getId()).header("Authorization",
				"Bearer " + createAuthenticatedUser("user2", RoleType.USER).getServiceToken()))
				.andExpect(status().isForbidden());

	}

	/**
	 * Test post Delete Post is not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDeleteDeletePost_NotOkNotFound() throws Exception {
		mockMvc.perform(delete("/api/posts/post/" + NON_EXISTENT_ID).header("Authorization",
				"Bearer " + createAuthenticatedUser("user2", RoleType.USER).getServiceToken()));
	}

	/**
	 * Test put Update Post ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPutUpdatePost_Ok() throws Exception {
		PostUpdateDto postUpdateParams = new PostUpdateDto();
		postUpdateParams.setAuthorId(authenticatedUser.getUserDto().getId());
		postUpdateParams.setCategoryId(categoryDao.save(new Category("Meals")).getId());
		postUpdateParams.setDescription("Tarta de Santiago");
		postUpdateParams.setImages(new ArrayList<byte[]>());
		postUpdateParams.setPrice(new BigDecimal(10));
		postUpdateParams.setTitle("Tarta");
		postUpdateParams.setUrl("http://poster.com");
		postUpdateParams.setType("Offer");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/posts/post/" + offer.getId())
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postUpdateParams)))
				.andExpect(status().isOk());
	}

	/**
	 * Test put Update Post Not ok Whitespace Title.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPutUpdatePost_NotOk_WhitespaceTitle() throws Exception {
		PostUpdateDto postUpdateParams = new PostUpdateDto();
		postUpdateParams.setAuthorId(authenticatedUser.getUserDto().getId());
		postUpdateParams.setCategoryId(categoryDao.save(new Category("Meals")).getId());
		postUpdateParams.setDescription("Tarta de Santiago");
		postUpdateParams.setImages(new ArrayList<byte[]>());
		postUpdateParams.setPrice(new BigDecimal(10));
		postUpdateParams.setTitle(" ");
		postUpdateParams.setUrl("http://poster.com");
		postUpdateParams.setType("Offer");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/posts/post/" + offer.getId())
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postUpdateParams)))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Test put Update Post Not ok Whitespace Description.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPutUpdatePost_NotOk_WhitespaceDescription() throws Exception {
		PostUpdateDto postUpdateParams = new PostUpdateDto();
		postUpdateParams.setAuthorId(authenticatedUser.getUserDto().getId());
		postUpdateParams.setCategoryId(categoryDao.save(new Category("Meals")).getId());
		postUpdateParams.setDescription(" ");
		postUpdateParams.setImages(new ArrayList<byte[]>());
		postUpdateParams.setPrice(new BigDecimal(10));
		postUpdateParams.setTitle("Tarta de Santiago");
		postUpdateParams.setUrl("http://poster.com");
		postUpdateParams.setType("Offer");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/posts/post/" + offer.getId())
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postUpdateParams)))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Test put Update Post Forbidden.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPutUpdatePost_Forbidden() throws Exception {
		PostUpdateDto postUpdateParams = new PostUpdateDto();
		postUpdateParams.setAuthorId(NON_EXISTENT_ID);
		postUpdateParams.setCategoryId(categoryDao.save(new Category("Meals")).getId());
		postUpdateParams.setDescription("Tarta de Santiago");
		postUpdateParams.setImages(new ArrayList<byte[]>());
		postUpdateParams.setPrice(new BigDecimal(10));
		postUpdateParams.setTitle("Tarta");
		postUpdateParams.setUrl("http://poster.com");
		postUpdateParams.setType("Offer");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/posts/post/" + offer.getId())
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
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
		PostUpdateDto postUpdateParams = new PostUpdateDto();
		postUpdateParams.setAuthorId(authenticatedUser.getUserDto().getId());
		postUpdateParams.setCategoryId(categoryDao.save(new Category("Meals")).getId());
		postUpdateParams.setDescription("Tarta de Santiago");
		postUpdateParams.setImages(new ArrayList<byte[]>());
		postUpdateParams.setPrice(new BigDecimal(10));
		postUpdateParams.setTitle("Tarta");
		postUpdateParams.setUrl("http://poster.com");
		postUpdateParams.setType("Offer");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/posts/post/" + NON_EXISTENT_ID)
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(postUpdateParams)))
				.andExpect(status().isNotFound());
	}

	/**
	 * Test post mark post as expired ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostMarkPostAsExpired_Ok() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post/" + offer.getId() + "/markAsExpired")
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(new PostExpiredDto(true))))
				.andExpect(status().isOk());
	}

	/**
	 * Test post mark post as expired forbidden.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostMarkPostAsExpired_NotOkForbidden() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post/" + offer.getId() + "/markAsExpired")
				.header("Authorization", "Bearer " + createAuthenticatedUser("user2", RoleType.USER).getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(new PostExpiredDto(true))))
				.andExpect(status().isForbidden());
	}

	/**
	 * Test post mark post as expired not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostMarkPostAsExpired_NotOkNotFound() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post/" + NON_EXISTENT_ID + "/markAsExpired")
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(new PostExpiredDto(true))))
				.andExpect(status().isNotFound());
	}

	/**
	 * Test post mark post as expired ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostMarkPostAsValid_Ok() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post/" + offer.getId() + "/markAsValid")
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(new PostExpiredDto(true))))
				.andExpect(status().isOk());
	}

	/**
	 * Test post mark post as expired not found.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostMarkPostAsValid_NotFound() throws Exception {
		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post/" + NON_EXISTENT_ID + "/markAsValid")
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(new PostExpiredDto(true))))
				.andExpect(status().isNotFound());
	}

}
