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
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;
import jakarta.transaction.Transactional;

/**
 * The Class PostServiceTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CatalogServiceTest {

	/** The non existent id. */
	private final static Long NON_EXISTENT_ID = -1L;

	/** The list of users. */
	private List<User> users;

	/** The list of categories. */
	private List<Category> categories;

	/** The list of posts. */
	private List<Post> posts;

	/** The search filters. */
	private SearchFilters searchFilters;

	/** The catalog service. */
	@Autowired
	private CatalogService catalogService;

	/** The post service. */
	@Autowired
	private PostService postService;

	/** The rating service. */
	@Autowired
	private RatingService ratingService;

	/** The category dao. */
	@Autowired
	private CategoryDao categoryDao;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The user service. */
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
	private Post createOffer(String title, User user, Category category, BigDecimal price) {
		return postDao.save(new Offer(title, "description", "url", price, LocalDateTime.now(), user, category));
	}

	/**
	 * Creates the coupon.
	 *
	 * @param title    the title of the post
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
	 */
	private Post createCoupon(String title, User user, Category category, BigDecimal price) {
		return postDao
				.save(new Coupon(title, "description", "url", price, LocalDateTime.now(), "EXTRA25", user, category));
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
	 * Sign up user
	 * 
	 * @param userName the user name
	 * @return the registered user
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 */
	private User signUpUser(String userName) throws MaximumImageSizeExceededException, DuplicateInstanceException {

		byte avatar[] = new byte[] { 50 };
		User user = new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com", avatar);

		userService.signUp(user);

		return user;
	}

	/**
	 * Assert Post Block Equals
	 * 
	 * @param actualBlock            the actual block
	 * @param expectedPosts          the expected block
	 * @param expectedExistMoreItems if there are expected to exist more items
	 */
	private void assertPostBlockEquals(Block<Post> actualBlock, List<Post> expectedPosts,
			boolean expectedExistMoreItems) {
		assertTrue(expectedPosts.containsAll(actualBlock.getItems()));
		assertTrue(actualBlock.getItems().containsAll(expectedPosts));
		assertEquals(expectedExistMoreItems, actualBlock.getExistMoreItems());
	}

	/**
	 * Set up
	 * 
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws MaximumImageSizeExceededException the maximum image size exceeded
	 *                                           exception
	 */
	@Before
	public void setUp() throws DuplicateInstanceException, MaximumImageSizeExceededException {
		users = List.of(signUpUser("user"), signUpUser("user2"));
		categories = List.of(createCategory("Meals"), createCategory("Motor"), createCategory("Home"),
				createCategory("Toys"), createCategory("Tech"), createCategory("Leisure"));
		posts = List.of(createOffer("offer1", users.get(0), categories.get(0), new BigDecimal(5)),
				createCoupon("coupon2", users.get(1), categories.get(1), new BigDecimal(15)),
				createCoupon("coupon3", users.get(0), categories.get(2), new BigDecimal(10)));
		searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "creationDate",
				"DESC");
	}

	/**
	 * Test find all categories.
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
	 */
	@Test
	public void testFindNoCategories() {
		categoryDao.deleteAll();

		assertTrue(catalogService.findAllCategories().isEmpty());
	}

	/**
	 * Test find all posts.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 *
	 */
	@Test
	public void testFindAllPosts() throws MaximumImageSizeExceededException, DuplicateInstanceException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "creationDate",
				"DESC");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(posts.get(2), posts.get(1)),
				true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 3),
				List.of(posts.get(2), posts.get(1), posts.get(0)), false);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 2), List.of(posts.get(0)), false);
	}

	/**
	 * Test find all posts with keyword.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPostsWithKeyword() throws MaximumImageSizeExceededException, DuplicateInstanceException {
		posts.get(1).setTitle("pepe");
		postDao.save(posts.get(1));

		assertPostBlockEquals(catalogService.findPosts(searchFilters, "pepe", 0, 2), List.of(posts.get(1)), false);
	}

	/**
	 * Test find all posts with a specific category.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPostsWithCategoryFilter()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		searchFilters.setCategoryId(categories.get(0).getId());

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(posts.get(0)), false);
	}

	/**
	 * Test find all posts with a specific type.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPostsWithTypeFilter() throws MaximumImageSizeExceededException, DuplicateInstanceException {
		searchFilters.setType("Coupon");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(posts.get(2), posts.get(1)),
				false);
	}

	/**
	 * Test find all posts with a specific min and max price.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPostsWithMinAndMaxPriceFilter()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		posts.get(0).setPrice(new BigDecimal(100));

		postDao.save(posts.get(0));

		searchFilters.setPrice(Map.of("gte", new BigDecimal("50"), "lte", new BigDecimal("200")));

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(posts.get(0)), false);
	}

	/**
	 * Test find all posts with a specific date.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPostsWithDateHourFilter()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		posts.get(0).setCreationDate(LocalDateTime.now().minusHours(2));
		postDao.save(posts.get(0));
		searchFilters.setDate("hour");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(posts.get(2), posts.get(1)),
				false);
	}

	/**
	 * Test find all posts with a specific date.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPostsWithDateDayFilter()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		posts.get(0).setCreationDate(LocalDateTime.now().minusDays(2));
		postDao.save(posts.get(0));
		searchFilters.setDate("day");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(posts.get(2), posts.get(1)),
				false);
	}

	/**
	 * Test find all posts with a specific date.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPostsWithDateWeekFilter()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		posts.get(0).setCreationDate(LocalDateTime.now().minusWeeks(2));
		postDao.save(posts.get(0));
		searchFilters.setDate("week");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(posts.get(2), posts.get(1)),
				false);
	}

	/**
	 * Test find all posts with a specific date.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPostsWithDateMonthFilter()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		posts.get(0).setCreationDate(LocalDateTime.now().minusMonths(2));
		postDao.save(posts.get(0));
		searchFilters.setDate("month");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(posts.get(2), posts.get(1)),
				false);
	}

	/**
	 * Test find all posts with a specific date.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPostsWithDateYearFilter()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		posts.get(0).setCreationDate(LocalDateTime.now().minusYears(2));
		postDao.save(posts.get(0));
		searchFilters.setDate("year");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(posts.get(2), posts.get(1)),
				false);
	}

	/**
	 * Test find all posts expired.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws PermissionException
	 * @throws InstanceNotFoundException
	 *
	 */
	@Test
	public void testFindAllPostsExpired() throws MaximumImageSizeExceededException, DuplicateInstanceException,
			InstanceNotFoundException, PermissionException {
		postService.markAsExpired(users.get(0).getId(), posts.get(0).getId(), true);

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(posts.get(2), posts.get(1)),
				false);
	}

	/**
	 * Test find all posts with some filters.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPostsWithSomeFilters() throws MaximumImageSizeExceededException, DuplicateInstanceException {
		posts.get(0).setPrice(new BigDecimal(100));
		posts.get(0).setTitle("pepe");
		postDao.save(posts.get(0));
		searchFilters.setCategoryId(categories.get(0).getId());
		searchFilters.setPrice(Map.of("gte", new BigDecimal("50"), "lte", new BigDecimal("10000")));

		assertPostBlockEquals(catalogService.findPosts(searchFilters, "pepe", 0, 2), List.of(posts.get(0)), false);
	}

	/**
	 * Test find no posts.
	 */
	@Test
	public void testFindNoPosts() {
		postDao.deleteAll();

		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "creationDate",
				"DESC");

		assertTrue(catalogService.findPosts(searchFilters, null, 0, 1).getItems().isEmpty());
		assertFalse(catalogService.findPosts(searchFilters, null, 0, 1).getExistMoreItems());
	}

	/**
	 * Test find posts by id.
	 */
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
	 * Test find no post by id.
	 *
	 */
	@Test
	public void testFindNoPostById() {
		assertThrows(InstanceNotFoundException.class, () -> catalogService.findPostById(NON_EXISTENT_ID));
	}

	/**
	 * Test find all posts sorted by creationDate descendent.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 *
	 */
	@Test
	public void testFindAndSortByCreationDateDescAllPosts()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "creationDate",
				"DESC");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 1), List.of(posts.get(2)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 1), List.of(posts.get(1)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 2, 1), List.of(posts.get(0)), false);
	}

	/**
	 * Test find all posts sorted by creationDate ascendent.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 *
	 */
	@Test
	public void testFindAndSortByCreationDateAscAllPosts()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "creationDate", "ASC");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 1), List.of(posts.get(0)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 1), List.of(posts.get(1)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 2, 1), List.of(posts.get(2)), false);
	}

	/**
	 * Test find all posts sorted by title descendent.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 *
	 */
	@Test
	public void testFindAndSortByTitleDescAllPosts()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "title", "DESC");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 1), List.of(posts.get(0)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 1), List.of(posts.get(2)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 2, 1), List.of(posts.get(1)), false);
	}

	/**
	 * Test find all posts sorted by title ascendent.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 *
	 */
	@Test
	public void testFindAndSortByTitleAscAllPosts()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "title", "ASC");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 1), List.of(posts.get(1)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 1), List.of(posts.get(2)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 2, 1), List.of(posts.get(0)), false);
	}

	/**
	 * Test find all posts sorted by price descendent.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 *
	 */
	@Test
	public void testFindAndSortByPriceDescAllPosts()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "price", "DESC");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 1), List.of(posts.get(1)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 1), List.of(posts.get(2)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 2, 1), List.of(posts.get(0)), false);
	}

	/**
	 * Test find all posts sorted by price ascendent.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 *
	 */
	@Test
	public void testFindAndSortByPriceAscAllPosts()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "price", "ASC");

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 1), List.of(posts.get(0)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 1), List.of(posts.get(2)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 2, 1), List.of(posts.get(1)), false);
	}

	/**
	 * Test find all posts sorted by positiveRatings descendent.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException
	 *
	 */
	@Test
	public void testFindAndSortByPositiveRatingsDescAllPosts()
			throws MaximumImageSizeExceededException, DuplicateInstanceException, InstanceNotFoundException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "positiveRatings",
				"DESC");

		ratingService.ratePostPositive(users.get(0).getId(), posts.get(0).getId());
		ratingService.ratePostPositive(users.get(0).getId(), posts.get(1).getId());
		ratingService.ratePostPositive(users.get(1).getId(), posts.get(1).getId());

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 1), List.of(posts.get(1)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 1), List.of(posts.get(0)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 2, 1), List.of(posts.get(2)), false);
	}

	/**
	 * Test find all posts sorted by positiveRatings ascendent.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException
	 *
	 */
	@Test
	public void testFindAndSortByPositiveRatingsAscAllPosts()
			throws MaximumImageSizeExceededException, DuplicateInstanceException, InstanceNotFoundException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "positiveRatings",
				"ASC");

		ratingService.ratePostPositive(users.get(0).getId(), posts.get(0).getId());
		ratingService.ratePostPositive(users.get(0).getId(), posts.get(1).getId());
		ratingService.ratePostPositive(users.get(1).getId(), posts.get(1).getId());

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 1), List.of(posts.get(2)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 1), List.of(posts.get(0)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 2, 1), List.of(posts.get(1)), false);
	}

	/**
	 * Test find all posts sorted by negativeRatings descendent.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException
	 *
	 */
	@Test
	public void testFindAndSortByNegativeRatingsDescAllPosts()
			throws MaximumImageSizeExceededException, DuplicateInstanceException, InstanceNotFoundException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "negativeRatings",
				"DESC");

		ratingService.ratePostNegative(users.get(0).getId(), posts.get(1).getId());
		ratingService.ratePostNegative(users.get(0).getId(), posts.get(0).getId());
		ratingService.ratePostNegative(users.get(1).getId(), posts.get(0).getId());

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 1), List.of(posts.get(0)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 1), List.of(posts.get(1)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 2, 1), List.of(posts.get(2)), false);
	}

	/**
	 * Test find all posts sorted by negativeRatings ascendent.
	 * 
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws DuplicateInstanceException        the duplicate instance exception
	 * @throws InstanceNotFoundException
	 *
	 */
	@Test
	public void testFindAndSortByNegativeRatingsAscAllPosts()
			throws MaximumImageSizeExceededException, DuplicateInstanceException, InstanceNotFoundException {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false, "negativeRatings",
				"ASC");

		ratingService.ratePostNegative(users.get(0).getId(), posts.get(1).getId());
		ratingService.ratePostNegative(users.get(0).getId(), posts.get(0).getId());
		ratingService.ratePostNegative(users.get(1).getId(), posts.get(0).getId());

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 1), List.of(posts.get(2)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 1), List.of(posts.get(1)), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 2, 1), List.of(posts.get(0)), false);
	}
}
