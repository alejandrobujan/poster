package es.udc.fi.dc.fd.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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
import es.udc.fi.dc.fd.model.entities.Coupon;
import es.udc.fi.dc.fd.model.entities.Offer;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.SearchFilters;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import jakarta.transaction.Transactional;

/**
 * The Class PostServiceTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CatalogServiceTest {

	private final static Long NON_EXISTENT_ID = -1L;

	private List<User> users;

	private List<Category> categories;

	private List<Post> posts;

	/** The user service. */
	@Autowired
	private CatalogService catalogService;

	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	private PostDao postDao;

	@Autowired
	private UserService userService;

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
	 * Creates the coupon.
	 *
	 * @param title    the title of the post
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
	 */
	private Post createCoupon(String title, User user, Category category) {
		return postDao.save(new Coupon(title, "description", "url", new BigDecimal(10), LocalDateTime.now(), "EXTRA25",
				user, category));
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

	/**
	 * @param actualBlock
	 * @param expectedPosts
	 * @param expectedExistMoreItems
	 */
	private void assertPostBlockEquals(Block<Post> actualBlock, List<Post> expectedPosts,
			boolean expectedExistMoreItems) {
		assertTrue(expectedPosts.containsAll(actualBlock.getItems()));
		assertTrue(actualBlock.getItems().containsAll(expectedPosts));
		assertEquals(expectedExistMoreItems, actualBlock.getExistMoreItems());
	}

	@Before
	public void setUp() throws DuplicateInstanceException, MaximumImageSizeExceededException {
		users = List.of(signUpUser("user"), signUpUser("user2"));
		categories = List.of(createCategory("Meals"), createCategory("Motor"), createCategory("Home"),
				createCategory("Toys"), createCategory("Tech"), createCategory("Leisure"));
		posts = List.of(createOffer("offer1", users.get(0), categories.get(0)),
				createCoupon("coupon2", users.get(1), categories.get(1)),
				createCoupon("coupon3", users.get(0), categories.get(2)));
	}

	/**
	 * Test find all categories.
	 *
	 */
	@Test
	public void testFindAllCategories() {
		List<Category> actualCategories = catalogService.findAllCategories();

		IntStream.range(0, categories.size()).forEach(i -> {
			assertEquals(categories.get(i).getId(), actualCategories.get(i).getId());
			assertEquals(categories.get(i).getName(), actualCategories.get(i).getName());
		});
	}

	/**
	 * Test find no categories.
	 *
	 */
	@Test
	public void testFindNoCategories() {
		categoryDao.deleteAll();

		assertTrue(catalogService.findAllCategories().isEmpty());
	}

	/**
	 * Test find all posts.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPosts() throws MaximumImageSizeExceededException, DuplicateInstanceException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false);

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(posts.get(2), posts.get(1)),
				true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 3),
				List.of(posts.get(2), posts.get(1), posts.get(0)), false);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 2), List.of(posts.get(0)), false);
	}

	/**
	 * Test find no posts.
	 *
	 */
	@Test
	public void testFindNoPosts() {
		postDao.deleteAll();

		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false);

		assertTrue(catalogService.findPosts(searchFilters, null, 0, 1).getItems().isEmpty());
		assertFalse(catalogService.findPosts(searchFilters, null, 0, 1).getExistMoreItems());
	}

	@Test
	public void testFindPostById()
			throws InstanceNotFoundException, DuplicateInstanceException, MaximumImageSizeExceededException {
		Post post = posts.get(0);
		Post foundPost = catalogService.findPostById(post.getId());

		assertEquals(post.getTitle(), foundPost.getTitle());
		assertEquals(post.getDescription(), foundPost.getDescription());
		assertEquals(post.getUrl(), foundPost.getUrl());
		assertEquals(post.getPrice(), foundPost.getPrice());
		assertEquals(post.getCreationDate(), foundPost.getCreationDate());
		assertEquals(post.getPositiveRatings(), foundPost.getPositiveRatings());
		assertEquals(post.getNegativeRatings(), foundPost.getNegativeRatings());
		assertEquals(post.isExpired(), foundPost.isExpired());
		assertEquals(post.getUser(), foundPost.getUser());
		assertEquals(post.getCategory(), foundPost.getCategory());
		assertEquals(post.getImages(), foundPost.getImages());
	}

	/**
	 * Test find no categories.
	 *
	 */
	@Test
	public void testFindNoPostById() {
		assertThrows(InstanceNotFoundException.class, () -> catalogService.findPostById(NON_EXISTENT_ID));
	}

}
