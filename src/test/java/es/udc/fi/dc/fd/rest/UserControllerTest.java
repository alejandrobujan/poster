package es.udc.fi.dc.fd.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import es.udc.fi.dc.fd.model.entities.User.RoleType;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectLoginException;
import es.udc.fi.dc.fd.rest.controllers.UserController;
import es.udc.fi.dc.fd.rest.dtos.AuthenticatedUserDto;
import es.udc.fi.dc.fd.rest.dtos.LoginParamsDto;
import es.udc.fi.dc.fd.rest.dtos.UserDto;

/**
 * The Class UserControllerTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class UserControllerTest {

	/** The Constant PASSWORD. */
	private final static String PASSWORD = "password";

	/** The mock mvc. */
	@Autowired
	private MockMvc mockMvc;

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
	 * Test post login ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostLogin_Ok() throws Exception {

		AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);

		LoginParamsDto loginParams = new LoginParamsDto();
		loginParams.setUserName(user.getUserDto().getUserName());
		loginParams.setPassword(PASSWORD);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/users/login").header("Authorization", "Bearer " + user.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(loginParams)))
				.andExpect(status().isOk());

	}

	/**
	 * Test post login ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostLogin_NotOk() throws Exception {

		AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);

		LoginParamsDto loginParams = new LoginParamsDto();
		loginParams.setUserName("Dani");
		loginParams.setPassword(PASSWORD);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/users/login").header("Authorization", "Bearer " + user.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(loginParams)))
				.andExpect(status().isNotFound());

	}

	/**
	 * Test post sign up ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostSignUp_Ok() throws Exception {

		byte avatar[] = new byte[] { 50 };
		UserDto userParams = new UserDto();
		userParams.setAvatar(avatar);
		userParams.setEmail("d.jueguen@udc.es");
		userParams.setFirstName("Dani");
		userParams.setLastName("Jueguen");
		userParams.setPassword("passwd");
		userParams.setUserName("DaniJueguen");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/users/signUp").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userParams))).andExpect(status().isCreated());

	}

	/**
	 * Test post sign up ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostSignUp_NotOk() throws Exception {

		byte avatar[] = new byte[] { 50 };
		UserDto userParams = new UserDto();
		userParams.setAvatar(avatar);
		userParams.setEmail("d.jueguen@udc.es");
		userParams.setFirstName("Dani");
		userParams.setLastName("Jueguen");
		userParams.setUserName("DaniJueguen");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/users/signUp").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userParams))).andExpect(status().isBadRequest());

	}
	/*
	 * /** Test post Update Profile ok.
	 *
	 * @throws Exception the exception
	 */

	/*
	 * @Test public void testPostUpdateProfile_Ok() throws Exception {
	 * 
	 * AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);
	 * byte avatar[] = new byte[] { 50 }; UserDto userParams = new UserDto();
	 * 
	 * userParams.setId(user.getUserDto().getId());
	 * userParams.setPassword(PASSWORD); userParams.setAvatar(avatar);
	 * userParams.setEmail("perico@udc.es"); userParams.setFirstName("Perico");
	 * userParams.setLastName("Perez"); userParams.setUserName("PericoPerez");
	 * 
	 * ObjectMapper mapper = new ObjectMapper();
	 * 
	 * mockMvc.perform(put("/api/users/{id}",
	 * user.getUserDto().getId()).requestAttr("userId", mapper)
	 * .contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(
	 * userParams))).andReturn() .getResolvedException().getMessage();
	 * 
	 * // .andExpect(status().isOk()); }
	 */

}
