package es.udc.fi.dc.fd.model.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

	/**
	 * Test sign up and login from id.
	 *
	 * @throws DuplicateInstanceException the duplicate instance exception
	 * @throws InstanceNotFoundException  the instance not found exception
	 */
	@Test
	public void testSignUpAndLoginFromId()
			throws DuplicateInstanceException, InstanceNotFoundException, MaximumImageSizeExceededException {

		User user = createUser("user");

		userService.signUp(user);

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

		User user = createUser("user");

		userService.signUp(user);
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
		User user = createUser("user");
		byte[] maxSizeImageBytes = new byte[1024001];
		user.setAvatar(maxSizeImageBytes);

		assertThrows(MaximumImageSizeExceededException.class, () -> userService.signUp(user));
	}

	/**
	 * Test login with non existent Id.
	 */

	@Test
	public void testLoginFromNonExistentId() {
		long nonExistentId = -1L;

		assertThrows(InstanceNotFoundException.class, () -> userService.loginFromId(nonExistentId));
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

		User user = createUser("user");
		String clearPassword = user.getPassword();

		userService.signUp(user);

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

		User user = createUser("user");
		String clearPassword = user.getPassword();

		userService.signUp(user);
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
	 */
	@Test
	public void testUpdateProfile()
			throws InstanceNotFoundException, DuplicateInstanceException, MaximumImageSizeExceededException {

		User user = createUser("user");

		userService.signUp(user);

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
	 */
	@Test
	public void testUpdateProfileDupicateLogin()
			throws InstanceNotFoundException, DuplicateInstanceException, MaximumImageSizeExceededException {

		User user1 = createUser("user1");

		userService.signUp(user1);

		User user2 = createUser("user2");

		userService.signUp(user2);

		user1.setUserName('X' + user2.getUserName());
		user1.setFirstName('X' + user1.getFirstName());
		user1.setLastName('X' + user1.getLastName());
		user1.setEmail('X' + user1.getEmail());
		user1.setAvatar(user1.getAvatar());

		assertThrows(DuplicateInstanceException.class, () -> userService.updateProfile(user1.getId(),
				user2.getUserName(), user1.getFirstName(), user1.getLastName(), user1.getEmail(), user1.getAvatar()));

		userService.updateProfile(user1.getId(), 'X' + user1.getUserName(), 'X' + user1.getFirstName(),
				'X' + user1.getLastName(), 'X' + user1.getEmail(), user1.getAvatar());

		assertNotEquals(user2.getUserName(), user1.getUserName());

	}

	/**
	 * Test update profile with non existent id.
	 *
	 * @throws DuplicateInstanceException the duplicate instance exception
	 * @throws InstanceNotFoundException  the instance not found exception
	 */
	@Test
	public void testUpdateProfileWithNonExistentId() {
		long nonExistentId = -1L;
		byte avatar[] = new byte[] { 50 };

		assertThrows(InstanceNotFoundException.class,
				() -> userService.updateProfile(nonExistentId, "X", "X", "X", "X", avatar));
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

		User user = createUser("user");
		String oldPassword = user.getPassword();
		String newPassword = 'X' + oldPassword;

		userService.signUp(user);
		userService.changePassword(user.getId(), oldPassword, newPassword);
		userService.login(user.getUserName(), newPassword);

	}

	/**
	 * Test change password with non existent id.
	 */
	@Test
	public void testChangePasswordWithNonExistentId() {
		long nonExistentId = -1L;

		assertThrows(InstanceNotFoundException.class, () -> userService.changePassword(nonExistentId, "X", "Y"));
	}

	/**
	 * Test change password with incorrect password.
	 *
	 * @throws DuplicateInstanceException the duplicate instance exception
	 */
	@Test
	public void testChangePasswordWithIncorrectPassword()
			throws DuplicateInstanceException, MaximumImageSizeExceededException {

		User user = createUser("user");
		String oldPassword = user.getPassword();
		String newPassword = 'X' + oldPassword;

		userService.signUp(user);
		assertThrows(IncorrectPasswordException.class,
				() -> userService.changePassword(user.getId(), 'Y' + oldPassword, newPassword));

	}
}
