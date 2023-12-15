package es.udc.fi.dc.fd.model.services;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import es.udc.fi.dc.fd.model.common.exceptions.DuplicateInstanceException;
import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.SaveDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.AlreadySavedException;
import es.udc.fi.dc.fd.model.services.exceptions.AlreadyUnsavedException;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.SavePostUserCreatorException;
import jakarta.transaction.Transactional;

/**
 * The Class SaveServiceTest.
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class SaveServiceTest {

	/** The non existent id */
	private final static long NON_EXISTENT_ID = -1L;

	/** The user */
	private User user;

	/** The user2 */
	private User user2;

	/** The category */
	private Category category;

	/** The post */
	private Post post;

	/** The category dao. */
	@Autowired
	private CategoryDao categoryDao;

	/** The save dao. */
	@Autowired
	private SaveDao saveDao;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The user service. */
	@Autowired
	private UserService userService;

	@Autowired
	private SaveService saveService;

	/**
	 * Creates the post.
	 *
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
	 */
	private Post createPost(User user, Category category) {
		return postDao.save(new Post("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user,
				category, LocalDateTime.of(2025, 2, 3, 0, 0, 0)));
	}

	/**
	 * Creates the category.
	 *
	 * @param name the name of the category
	 * 
	 * @return the category
	 */
	private Category createCategory(String name) {
		return categoryDao.save(new Category(name));
	}

	/**
	 * Sign up user
	 * 
	 * @param userName the user name
	 * @return the registered user
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 */
	private User signUpUser(String userName) throws DuplicateInstanceException, MaximumImageSizeExceededException {

		byte avatar[] = new byte[] { 50 };
		User user = new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com", avatar);

		userService.signUp(user);

		return user;

	}

	/**
	 * The set up
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 */
	@Before
	public void setUp() throws DuplicateInstanceException, MaximumImageSizeExceededException {
		user = signUpUser("user");
		user2 = signUpUser("user2");
		category = createCategory("category");
		post = createPost(user, category);
	}

	/**
	 * Test save post
	 * 
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 */
	@Test
	public void testSavePost() throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException {
		assertFalse(saveDao.existsSaveByPostIdAndUserId(post.getId(), user2.getId()));
		saveService.savePost(post.getId(), user2.getId());
		assertTrue(saveDao.existsSaveByPostIdAndUserId(post.getId(), user2.getId()));
	}

	/**
	 * Test save no post
	 * 
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 */
	@Test
	public void testSavePostNoPost()
			throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException {
		assertThrows(InstanceNotFoundException.class, () -> saveService.savePost(NON_EXISTENT_ID, user.getId()));
	}

	/**
	 * Test save post no user
	 * 
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 */
	@Test
	public void testSavePostNoUser()
			throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException {
		assertThrows(InstanceNotFoundException.class, () -> saveService.savePost(post.getId(), NON_EXISTENT_ID));
	}

	/**
	 * Test save post already saved
	 * 
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 */
	@Test
	public void testSavePostAlreadySaved()
			throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException {
		assertFalse(saveDao.existsSaveByPostIdAndUserId(post.getId(), user2.getId()));
		saveService.savePost(post.getId(), user2.getId());
		assertTrue(saveDao.existsSaveByPostIdAndUserId(post.getId(), user2.getId()));
		assertThrows(AlreadySavedException.class, () -> saveService.savePost(post.getId(), user2.getId()));
	}

	/**
	 * Test save post user creator
	 * 
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 */
	@Test
	public void testSavePostUserCreator()
			throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException {
		assertThrows(SavePostUserCreatorException.class, () -> saveService.savePost(post.getId(), user.getId()));
	}

	/**
	 * Test is post saved by user
	 * 
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 */
	@Test
	public void testisPostSavedByUser()
			throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException {
		assertFalse(saveService.isPostSavedByUser(post.getId(), user2.getId()));
		saveService.savePost(post.getId(), user2.getId());
		assertTrue(saveService.isPostSavedByUser(post.getId(), user2.getId()));
	}

	/**
	 * Test unsave post
	 * 
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 */
	@Test
	public void testUnsavePost() throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException,
			AlreadyUnsavedException {
		saveService.savePost(post.getId(), user2.getId());
		saveService.unSavePost(post.getId(), user2.getId());
		assertFalse(saveService.isPostSavedByUser(post.getId(), user2.getId()));
	}

	/**
	 * Test unsave no post
	 * 
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 */
	@Test
	public void testUnsavePostNoPost()
			throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException {
		assertThrows(InstanceNotFoundException.class, () -> saveService.unSavePost(NON_EXISTENT_ID, user.getId()));
	}

	/**
	 * Test unsave post no user
	 * 
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 */
	@Test
	public void testUnsavePostNoUser()
			throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException {
		assertThrows(InstanceNotFoundException.class, () -> saveService.unSavePost(post.getId(), NON_EXISTENT_ID));
	}

	/**
	 * Test unsave post twice
	 * 
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 * @throws AlreadyUnsavedException      the already unsaved exception
	 */
	@Test
	public void testUnsavePostTwice() throws InstanceNotFoundException, AlreadySavedException,
			SavePostUserCreatorException, AlreadyUnsavedException {
		saveService.savePost(post.getId(), user2.getId());
		saveService.unSavePost(post.getId(), user2.getId());
		assertThrows(AlreadyUnsavedException.class, () -> saveService.unSavePost(post.getId(), user2.getId()));
	}

}
