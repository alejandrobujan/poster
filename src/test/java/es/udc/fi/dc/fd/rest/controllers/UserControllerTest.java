package es.udc.fi.dc.fd.rest.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.entities.User.RoleType;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectLoginException;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.rest.dtos.AuthenticatedUserDto;
import es.udc.fi.dc.fd.rest.dtos.ChangePasswordParamsDto;
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

	/** The authenticated user */
	private AuthenticatedUserDto authenticatedUser;

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
	 * Set up
	 * 
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws MaximumImageSizeExceededException the maximum image size exceeded
	 *                                           exception
	 * @throws IncorrectLoginException           the incorrect login exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 */
	@Before
	public void setUp() throws DuplicateInstanceException, MaximumImageSizeExceededException, IncorrectLoginException,
			InstanceNotFoundException {
		authenticatedUser = createAuthenticatedUser("user", RoleType.USER);
	}

	/**
	 * Test post login ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostLogin_Ok() throws Exception {
		LoginParamsDto loginParams = new LoginParamsDto();
		loginParams.setUserName(authenticatedUser.getUserDto().getUserName());
		loginParams.setPassword(PASSWORD);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(loginParams))).andExpect(status().isOk());

	}

	/**
	 * Test post login not ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostLogin_NotOk() throws Exception {
		LoginParamsDto loginParams = new LoginParamsDto();
		loginParams.setUserName("nobody");
		loginParams.setPassword(PASSWORD);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/users/login").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(loginParams))).andExpect(status().isNotFound());

	}

	/**
	 * Test post sign up ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostSignUp_Ok() throws Exception {
		UserDto userParams = new UserDto();
		userParams.setAvatar(new byte[] { 50 });
		userParams.setEmail("nobody@udc.es");
		userParams.setFirstName("No");
		userParams.setLastName("Body");
		userParams.setPassword(PASSWORD);
		userParams.setUserName("nobody");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/users/signUp").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userParams))).andExpect(status().isCreated());

	}

	/**
	 * Test post sign up not ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostSignUp_NotOk() throws Exception {
		UserDto userParams = new UserDto();
		userParams.setAvatar(new byte[] { 50 });
		userParams.setEmail("nobody@udc.es");
		userParams.setFirstName("No");
		userParams.setLastName("Body");
		userParams.setUserName("nobody");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/users/signUp").contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userParams))).andExpect(status().isBadRequest());

	}

	/**
	 * Test post Update Profile ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostUpdateProfile_Ok() throws Exception {
		UserDto userParams = new UserDto();
		userParams.setAvatar(new byte[] { 50 });
		userParams.setEmail("perico@udc.es");
		userParams.setFirstName("Perico");
		userParams.setLastName("Perez");
		userParams.setUserName("PericoPerez");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/users/" + authenticatedUser.getUserDto().getId())
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(userParams)))
				.andExpect(status().isOk());
	}

	/**
	 * Test post Update Profile Not Ok Whitespace Login.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostUpdateProfile_NotOk_WhitespaceLogin() throws Exception {

		AuthenticatedUserDto user = createAuthenticatedUser("admin", RoleType.USER);
		byte avatar[] = new byte[] { 50 };
		UserDto userParams = new UserDto();

		userParams.setAvatar(avatar);
		userParams.setEmail("perico@udc.es");
		userParams.setFirstName("Perico");
		userParams.setLastName("Perez");
		userParams.setUserName(" ");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/users/" + user.getUserDto().getId())
				.header("Authorization", "Bearer " + user.getServiceToken()).contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsBytes(userParams))).andExpect(status().isBadRequest());
	}

	/**
	 * Test post Update Profile Not ok.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostUpdateProfile_NotOkForbidden() throws Exception {
		UserDto userParams = new UserDto();
		userParams.setAvatar(new byte[] { 50 });
		userParams.setEmail("perico@udc.es");
		userParams.setFirstName("Perico");
		userParams.setLastName("Perez");
		userParams.setUserName("PericoPerez");

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/users/5000").header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(userParams)))
				.andExpect(status().isForbidden());
	}

	/**
	 * Test post Update Profile Not ok Duplicate Login.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testPostUpdateProfile_NotOkDuplicateLogin() throws Exception {
		AuthenticatedUserDto authenticatedUser2 = createAuthenticatedUser("user2", RoleType.USER);

		UserDto userParams = new UserDto();
		userParams.setAvatar(new byte[] { 50 });
		userParams.setEmail("perico@udc.es");
		userParams.setFirstName("Perico");
		userParams.setLastName("Perez");
		userParams.setUserName(authenticatedUser2.getUserDto().getUserName());

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(put("/api/users/" + authenticatedUser.getUserDto().getId())
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(userParams)))
				.andExpect(status().isBadRequest());
	}

	/**
	 * Test Change Password ok
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testChangePassword_Ok() throws Exception {
		ChangePasswordParamsDto passwordParams = new ChangePasswordParamsDto();

		passwordParams.setOldPassword(PASSWORD);
		passwordParams.setNewPassword('Y' + PASSWORD);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/users/" + authenticatedUser.getUserDto().getId() + "/changePassword")
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(passwordParams)))
				.andExpect(status().isNoContent());
	}

	/**
	 * Test Change Password not ok
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testChangePassword_NotOk() throws Exception {
		ChangePasswordParamsDto passwordParams = new ChangePasswordParamsDto();

		passwordParams.setOldPassword('X' + PASSWORD);
		passwordParams.setNewPassword('Y' + PASSWORD);

		ObjectMapper mapper = new ObjectMapper();

		mockMvc.perform(post("/api/users/" + authenticatedUser.getUserDto().getId() + "/changePassword")
				.header("Authorization", "Bearer " + authenticatedUser.getServiceToken())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsBytes(passwordParams)))
				.andExpect(status().isNotFound());
	}
}
