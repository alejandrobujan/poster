package es.udc.fi.dc.fd.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
	@Autowired
	private PostService postService;

	@Autowired
	private RatingService ratingService;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private UserService userService;

	/**
	 * Creates the post.
	 *
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
	 */
	private Post createPost(User user, Category category) {
		return postDao
				.save(new Post("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user, category));
	}

	private Category createCategory(String name) {
		return categoryDao.save(new Category(name));
	}

	/**
	 * @param userName the user name
	 * @return the registered user
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 */
	private User signUpUser(String userName) throws MaximumImageSizeExceededException, DuplicateInstanceException {

		byte avatar[] = new byte[] { 50 };
		User user = new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com", avatar);

		userService.signUp(user);

		return user;

	}

	@Test
	public void testRatePostPositive()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		Post foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostPositive(u.getId(), post.getId());

		foundPost = postService.findPostById(post.getId());

		assertEquals(1, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

	}

	@Test
	public void testRatePostNegative()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		Post foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostNegative(u.getId(), post.getId());

		foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(1, foundPost.getNegativeRatings());

	}

	@Test
	public void testRatePostPositiveTwice()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		Post foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostPositive(u.getId(), post.getId());

		foundPost = postService.findPostById(post.getId());

		assertEquals(1, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostPositive(u.getId(), post.getId());

		foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

	}

	@Test
	public void testRatePostNegativeTwice()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		Post foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostNegative(u.getId(), post.getId());

		foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(1, foundPost.getNegativeRatings());

		ratingService.ratePostNegative(u.getId(), post.getId());

		foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

	}

	@Test
	public void testRatePostNegativeToPositive()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		Post foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostNegative(u.getId(), post.getId());

		foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(1, foundPost.getNegativeRatings());

		ratingService.ratePostPositive(u.getId(), post.getId());

		foundPost = postService.findPostById(post.getId());

		assertEquals(1, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

	}

	@Test
	public void testRatePostPositiveToNegative()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		Post foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostPositive(u.getId(), post.getId());

		foundPost = postService.findPostById(post.getId());

		assertEquals(1, foundPost.getPositiveRatings());

		assertEquals(0, foundPost.getNegativeRatings());

		ratingService.ratePostNegative(u.getId(), post.getId());

		foundPost = postService.findPostById(post.getId());

		assertEquals(0, foundPost.getPositiveRatings());

		assertEquals(1, foundPost.getNegativeRatings());

	}

	@Test
	public void testRatePostNoPost()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");
		long nonExistentId = -1L;
		assertThrows(InstanceNotFoundException.class, () -> ratingService.ratePostNegative(u.getId(), nonExistentId));
	}

	@Test
	public void testRatePostNonExistentUser()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		long nonExistentId = -1L;

		assertThrows(InstanceNotFoundException.class,
				() -> ratingService.ratePostPositive(nonExistentId, post.getId()));
	}

}
