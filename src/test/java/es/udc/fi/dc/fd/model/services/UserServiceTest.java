package es.udc.fi.dc.fd.model.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.fi.dc.fd.model.common.exceptions.DuplicateInstanceException;
import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectLoginException;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectLoginUpdateException;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectPasswordException;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import jakarta.transaction.Transactional;

/**
 * The Class UserServiceTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

	private final static long NON_EXISTENT_ID = -1L;
	private final static int EXCEEDED_BYTE_SIZE = 1024001;

	private String clearPassword;

	private User user;

	/** The user service. */
	@Autowired
	private UserService userService;

	/**
	 * Creates the user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	private User createUser(String userName) {
		byte avatar[] = new byte[] { 50 };
		return new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com", avatar);
	}

	@Before
	public void setUp() throws DuplicateInstanceException, MaximumImageSizeExceededException {
		user = createUser("user");
		clearPassword = user.getPassword();
		userService.signUp(user);
	}

	/**
	 * Test sign up and login from id.
	 *
	 * @throws DuplicateInstanceException the duplicate instance exception
	 * @throws InstanceNotFoundException  the instance not found exception
	 */
	@Test
	public void testSignUpAndLoginFromId()
			throws DuplicateInstanceException, InstanceNotFoundException, MaximumImageSizeExceededException {
		User loggedInUser = userService.loginFromId(user.getId());

		assertEquals(user, loggedInUser);
		assertEquals(User.RoleType.USER, user.getRole());
	}

	/**
	 * Test sign up with duplicated user name.
	 *
	 * @throws DuplicateInstanceException the duplicate instance exception
	 */
	@Test
	public void testSignUpDuplicatedUserName() throws DuplicateInstanceException, MaximumImageSizeExceededException {
		assertThrows(DuplicateInstanceException.class, () -> userService.signUp(user));
	}

	/**
	 * Test sign up with an avatar that exceeds the maximum size.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 */
	@Test
	public void testSignUpWithMaxSizeAvatar() throws DuplicateInstanceException, MaximumImageSizeExceededException {
		User user = createUser("newuser");
		user.setAvatar(new byte[EXCEEDED_BYTE_SIZE]);

		assertThrows(MaximumImageSizeExceededException.class, () -> userService.signUp(user));
	}

	/**
	 * Test login with non existent Id.
	 */

	@Test
	public void testLoginFromNonExistentId() {
		assertThrows(InstanceNotFoundException.class, () -> userService.loginFromId(NON_EXISTENT_ID));
	}

	/**
	 * Test login.
	 *
	 * @throws DuplicateInstanceException the duplicate instance exception
	 * @throws IncorrectLoginException    the incorrect login exception
	 */
	@Test
	public void testLogin()
			throws DuplicateInstanceException, IncorrectLoginException, MaximumImageSizeExceededException {
		User loggedInUser = userService.login(user.getUserName(), clearPassword);

		assertEquals(user, loggedInUser);
	}

	/**
	 * Test login with an incorrect password.
	 *
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws MaximumImageSizeExceededException the maximum size exception
	 */
	@Test
	public void testLoginWithIncorrectPassword() throws DuplicateInstanceException, MaximumImageSizeExceededException {
		assertThrows(IncorrectLoginException.class, () -> userService.login(user.getUserName(), 'X' + clearPassword));
	}

	/**
	 * Test login with non existent username.
	 */
	@Test
	public void testLoginWithNonExistentUserName() {
		assertThrows(IncorrectLoginException.class, () -> userService.login("X", "Y"));
	}

	/**
	 * Test update profile.
	 *
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum image size exception
	 * @throws IncorrectLoginUpdateException
	 */
	@Test
	public void testUpdateProfile() throws InstanceNotFoundException, DuplicateInstanceException,
			MaximumImageSizeExceededException, IncorrectLoginUpdateException {
		user.setUserName('X' + user.getUserName());
		user.setFirstName('X' + user.getFirstName());
		user.setLastName('X' + user.getLastName());
		user.setEmail('X' + user.getEmail());
		user.setAvatar(user.getAvatar());

		userService.updateProfile(user.getId(), 'X' + user.getUserName(), 'X' + user.getFirstName(),
				'X' + user.getLastName(), 'X' + user.getEmail(), user.getAvatar());

		User updatedUser = userService.loginFromId(user.getId());

		assertEquals(user, updatedUser);
	}

	/**
	 * Test update profile.
	 *
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum image size exception
	 * @throws IncorrectLoginUpdateException
	 */
	@Test
	public void testUpdateProfileWithWhitespaceUser() throws InstanceNotFoundException, DuplicateInstanceException,
			MaximumImageSizeExceededException, IncorrectLoginUpdateException {
		user.setUserName('X' + user.getUserName());
		user.setFirstName('X' + user.getFirstName());
		user.setLastName('X' + user.getLastName());
		user.setEmail('X' + user.getEmail());
		user.setAvatar(user.getAvatar());

		assertThrows(IncorrectLoginUpdateException.class, () -> userService.updateProfile(user.getId(), " ",
				'X' + user.getFirstName(), 'X' + user.getLastName(), 'X' + user.getEmail(), user.getAvatar()));
	}

	/**
	 * Test update profile.
	 *
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum image size exception
	 * @throws IncorrectLoginUpdateException
	 */
	@Test
	public void testUpdateProfileDupicateLogin() throws InstanceNotFoundException, DuplicateInstanceException,
			MaximumImageSizeExceededException, IncorrectLoginUpdateException {
		User user2 = createUser("user2");

		userService.signUp(user2);
		user.setUserName('X' + user2.getUserName());
		user.setFirstName('X' + user.getFirstName());
		user.setLastName('X' + user.getLastName());
		user.setEmail('X' + user.getEmail());
		user.setAvatar(user.getAvatar());

		assertThrows(DuplicateInstanceException.class, () -> userService.updateProfile(user.getId(),
				user2.getUserName(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getAvatar()));

		userService.updateProfile(user.getId(), 'X' + user.getUserName(), 'X' + user.getFirstName(),
				'X' + user.getLastName(), 'X' + user.getEmail(), user.getAvatar());

		assertNotEquals(user2.getUserName(), user.getUserName());
	}

	/**
	 * Test update profile with non existent id.
	 *
	 * @throws DuplicateInstanceException the duplicate instance exception
	 * @throws InstanceNotFoundException  the instance not found exception
	 */
	@Test
	public void testUpdateProfileWithNonExistentId() {
		assertThrows(InstanceNotFoundException.class,
				() -> userService.updateProfile(NON_EXISTENT_ID, "X", "X", "X", "X", new byte[] { 50 }));
	}

	/**
	 * Test change password.
	 *
	 * @throws DuplicateInstanceException the duplicate instance exception
	 * @throws InstanceNotFoundException  the instance not found exception
	 * @throws IncorrectLoginException    the incorrect login exception
	 * @throws IncorrectPasswordException the incorrect password exception
	 */
	@Test
	public void testChangePassword() throws DuplicateInstanceException, InstanceNotFoundException,
			IncorrectPasswordException, IncorrectLoginException, MaximumImageSizeExceededException {
		String newPassword = 'X' + clearPassword;

		userService.changePassword(user.getId(), clearPassword, newPassword);
		userService.login(user.getUserName(), newPassword);
	}

	/**
	 * Test change password with non existent id.
	 */
	@Test
	public void testChangePasswordWithNonExistentId() {
		assertThrows(InstanceNotFoundException.class, () -> userService.changePassword(NON_EXISTENT_ID, "X", "Y"));
	}

	/**
	 * Test change password with incorrect password.
	 *
	 * @throws DuplicateInstanceException the duplicate instance exception
	 */
	@Test
	public void testChangePasswordWithIncorrectPassword()
			throws DuplicateInstanceException, MaximumImageSizeExceededException {
		assertThrows(IncorrectPasswordException.class,
				() -> userService.changePassword(user.getId(), 'Y' + clearPassword, 'X' + clearPassword));

	}
}
