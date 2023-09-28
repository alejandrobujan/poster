package es.udc.fi.dc.fd.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.model.entities.User.RoleType;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectLoginException;
import es.udc.fi.dc.fd.rest.controllers.UserController;
import es.udc.fi.dc.fd.rest.dtos.AuthenticatedUserDto;
import es.udc.fi.dc.fd.rest.dtos.LoginParamsDto;
import es.udc.fi.dc.fd.rest.dtos.PostParamsDto;

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

	/** The user dao. */
	@Autowired
	private UserDao userDao;

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

		List <byte []> image = new ArrayList<byte []>();
		
		PostParamsDto PostParams = new PostParamsDto();
		PostParams.setCategoryId(1L);
		PostParams.setDescription("Tarta de Santiago");
		PostParams.setImages(image);
		PostParams.setPrice(new BigDecimal(10));
		PostParams.setTitle("Tarta de Santiago");
		PostParams.setUrl("http://poster.com");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post").header("Authorization", "Bearer " + user.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(PostParams)))
				.andExpect(status().isNoContent());

	}
	
	/**
	 * Test post Create Post Not ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostCreatePost_NotOk() throws Exception {

		AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);
		
		PostParamsDto PostParams = new PostParamsDto();
		PostParams.setCategoryId(1L);
		PostParams.setDescription("Tarta de Santiago");
		PostParams.setImages(null);
		PostParams.setPrice(new BigDecimal(10));
		PostParams.setTitle("Tarta de Santiago");
		PostParams.setUrl("http://poster.com");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/posts/post").header("Authorization", "Bearer " + user.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(PostParams)))
				.andExpect(status().isBadRequest());

	}
}
