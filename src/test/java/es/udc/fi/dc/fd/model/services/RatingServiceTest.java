package es.udc.fi.dc.fd.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import jakarta.transaction.Transactional;

/**
 * The Class RatingServiceTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class RatingServiceTest {

	/** The non existent id */
	private final static long NON_EXISTENT_ID = -1L;

	/** The user */
	private User user;

	/** The category */
	private Category category;

	/** The post */
	private Post post;

	/** The catalog service. */
	@Autowired
	private CatalogService catalogService;

	/** The rating service. */
	@Autowired
	private RatingService ratingService;

	/** The user service. */
	@Autowired
	private UserService userService;

	/** The category dao. */
	@Autowired
	private CategoryDao categoryDao;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/**
	 * Creates the post.
	 *
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
	 */
	private Post createPost(User user, Category category) {
		return postDao
				.save(new Post("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user, category, LocalDateTime.of(2025, 2, 3, 0, 0, 0)));
	}

	/**
	 * Creates the category.
	 *
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
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
		category = createCategory("category");
		post = createPost(user, category);
	}

	/**
	 * Test rate post positive.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 */
	@Test
	public void testRatePostPositive()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		Post foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostPositive(user.getId(), post.getId());
		foundPost = catalogService.findPostById(post.getId());

		assertEquals(1, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());
	}

	/**
	 * Test rate post negative.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 */
	@Test
	public void testRatePostNegative()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		Post foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostNegative(user.getId(), post.getId());
		foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(1, foundPost.getNegativeRatings());
	}

	/**
	 * Test rate post positive twice.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 */
	@Test
	public void testRatePostPositiveTwice()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		Post foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostPositive(user.getId(), post.getId());
		foundPost = catalogService.findPostById(post.getId());

		assertEquals(1, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostPositive(user.getId(), post.getId());
		foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());
	}

	/**
	 * Test rate post negative twice.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 */
	@Test
	public void testRatePostNegativeTwice()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		Post foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostNegative(user.getId(), post.getId());
		foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(1, foundPost.getNegativeRatings());

		ratingService.ratePostNegative(user.getId(), post.getId());
		foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());
	}

	/**
	 * Test rate post negative to positive.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 */
	@Test
	public void testRatePostNegativeToPositive()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		Post foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostNegative(user.getId(), post.getId());
		foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(1, foundPost.getNegativeRatings());

		ratingService.ratePostPositive(user.getId(), post.getId());
		foundPost = catalogService.findPostById(post.getId());

		assertEquals(1, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());
	}

	/**
	 * Test rate post positive to negative.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 */
	@Test
	public void testRatePostPositiveToNegative()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		Post foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostPositive(user.getId(), post.getId());
		foundPost = catalogService.findPostById(post.getId());

		assertEquals(1, foundPost.getPositiveRatings());
		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostNegative(user.getId(), post.getId());
		foundPost = catalogService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());
		assertEquals(1, foundPost.getNegativeRatings());
	}

	/**
	 * Test rate post no post.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 */
	@Test
	public void testRatePostNoPost()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		assertThrows(InstanceNotFoundException.class,
				() -> ratingService.ratePostNegative(user.getId(), NON_EXISTENT_ID));
	}

	/**
	 * Test rate post no existent user.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 */
	@Test
	public void testRatePostNonExistentUser()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		assertThrows(InstanceNotFoundException.class,
				() -> ratingService.ratePostPositive(NON_EXISTENT_ID, post.getId()));
	}

}
