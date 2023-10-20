package es.udc.fi.dc.fd.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

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

	/** The catalog service. */
	@Autowired
	private CatalogService catalogService;

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
	 * Creates the post.
	 *
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
	 */
	private Post createPost(User user, Category category) {
		return postDao.save(
				new Offer("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user, category));
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
	 * Test find all categories.
	 */
	@Test
	public void testFindAllCategories() {
		Category c1 = new Category("Comida");
		Category c2 = new Category("Motor");
		Category c3 = new Category("Hogar");
		Category c4 = new Category("Juguetes");
		Category c5 = new Category("Tecnologia");
		Category c6 = new Category("Entretenimiento");

		List<Category> expectedListCategory = Arrays.asList(c1, c2, c3, c4, c5, c6);
		expectedListCategory.forEach(c -> categoryDao.save(c));

		List<Category> listCategory = catalogService.findAllCategories();

		IntStream.range(0, expectedListCategory.size()).forEach(i -> {
			assertEquals(expectedListCategory.get(i).getId(), listCategory.get(i).getId());
			assertEquals(expectedListCategory.get(i).getName(), listCategory.get(i).getName());
		});

	}

	/**
	 * Test find no categories.
	 */
	@Test
	public void testFindNoCategories() {
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
		User user1 = signUpUser("userName1");
		User user2 = signUpUser("userName2");
		Category c1 = categoryDao.save(new Category("Comida"));
		Category c2 = categoryDao.save(new Category("Motor"));
		Category c3 = categoryDao.save(new Category("Hogar"));

		Post post1 = createPost(user1, c1);
		Post post2 = createPost(user2, c2);
		Post post3 = createPost(user1, c3);

		postDao.save(post1);
		postDao.save(post2);
		postDao.save(post3);

		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false);

		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 2), List.of(post3, post2), true);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 0, 3), List.of(post3, post2, post1), false);
		assertPostBlockEquals(catalogService.findPosts(searchFilters, null, 1, 2), List.of(post1), false);
	}

	/**
	 * Test find no posts.
	 */
	@Test
	public void testFindNoPosts() {
		SearchFilters searchFilters = new SearchFilters(null, null,
				Map.of("gte", new BigDecimal("0"), "lte", new BigDecimal("10000")), null, false);

		assertTrue(catalogService.findPosts(searchFilters, null, 0, 1).getItems().isEmpty());
		assertFalse(catalogService.findPosts(searchFilters, null, 0, 1).getExistMoreItems());
	}

	/**
	 * Test find posts by id.
	 */
	@Test
	public void testFindPostById()
			throws InstanceNotFoundException, DuplicateInstanceException, MaximumImageSizeExceededException {

		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

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
		long nonExistentId = -1L;
		assertThrows(InstanceNotFoundException.class, () -> catalogService.findPostById(nonExistentId));
	}

}
