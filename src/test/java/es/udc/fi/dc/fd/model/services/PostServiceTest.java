package es.udc.fi.dc.fd.model.services;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.junit.Ignore;
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
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;
import jakarta.transaction.Transactional;

/**
 * The Class PostServiceTest.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PostServiceTest {

	/** The user service. */
	@Autowired
	private PostService postService;

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

	/**
	 * Test find all categories.
	 *
	 */
	@Test
	@Ignore
	public void testFindAllCategories() {
		Category c1 = new Category(1L, "Comida");
		Category c2 = new Category(2L, "Motor");
		Category c3 = new Category(3L, "Hogar");
		Category c4 = new Category(4L, "Juguetes");
		Category c5 = new Category(5L, "Tecnologia");
		Category c6 = new Category(6L, "Entretenimiento");

		List<Category> expectedListCategory = Arrays.asList(c1, c2, c3, c4, c5, c6);
		List<Category> listCategory = postService.findAllCategories();

		IntStream.range(0, expectedListCategory.size()).forEach(i -> {
			assertEquals(expectedListCategory.get(i).getId(), listCategory.get(i).getId());
			assertEquals(expectedListCategory.get(i).getName(), listCategory.get(i).getName());
		});

	}

	/**
	 * Test find no categories.
	 *
	 */
	@Test
	public void testFindNoCategories() {
		categoryDao.deleteAll();
		assertTrue(postService.findAllCategories().isEmpty());
	}

	/**
	 * Test find all posts.
	 * 
	 * @throws MaximumImageSizeExceededException
	 *
	 */
	@Test
	public void testFindAllPosts() throws MaximumImageSizeExceededException, DuplicateInstanceException {
		User user1 = signUpUser("userName1");
		User user2 = signUpUser("userName2");
		List<Category> listCategory = postService.findAllCategories();

		Post post1 = createPost(user1, listCategory.get(1));
		Post post2 = createPost(user2, listCategory.get(2));
		Post post3 = createPost(user1, listCategory.get(3));

		postDao.save(post1);
		postDao.save(post2);
		postDao.save(post3);

		assertPostBlockEquals(postService.findAllPosts(0, 2), List.of(post3, post2), true);
		assertPostBlockEquals(postService.findAllPosts(0, 3), List.of(post3, post2, post1), false);
		assertPostBlockEquals(postService.findAllPosts(1, 2), List.of(post1), false);
	}

	/**
	 * Test find no posts.
	 *
	 */
	@Test
	public void testFindNoPosts() {
		assertTrue(postService.findAllPosts(0, 1).getItems().isEmpty());
		assertFalse(postService.findAllPosts(0, 1).getExistMoreItems());
	}

	/**
	 * Test create post.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 * @throws MissingRequiredParameterException
	 *
	 */
	@Test
	public void testCreatePost() throws MaximumImageSizeExceededException, DuplicateInstanceException,
			InstanceNotFoundException, MissingRequiredParameterException {

		String title = "title";
		String description = "description";
		String url = "http//www.google.es";
		BigDecimal price = new BigDecimal(10);

		String code = "EXTRA25";

		User user1 = signUpUser("userName1");
		User user2 = signUpUser("userName2");

		Category category1 = createCategory("category1");
		Category category2 = createCategory("category2");

		Post post1 = postService.createPost(title, description, url, price, user1.getId(), category1.getId(),
				new ArrayList<byte[]>(), "Coupon", Map.ofEntries(entry("code", code)));
		Post post2 = postService.createPost(title, description, url, price, user2.getId(), category2.getId(),
				new ArrayList<byte[]>(), "Offer", Map.ofEntries());

		Post actualPost1 = postDao.findById(post1.getId()).get();
		Post actualPost2 = postDao.findById(post2.getId()).get();

		assertNotNull(actualPost1);
		assertEquals(post1, actualPost1);
		assertEquals(category1.getId(), actualPost1.getCategory().getId());
		assertEquals(description, actualPost1.getDescription());
		assertEquals(price, actualPost1.getPrice());
		assertEquals(title, actualPost1.getTitle());
		assertEquals(url, actualPost1.getUrl());
		assertEquals(user1.getId(), actualPost1.getUser().getId());
		assertTrue(actualPost1.getImages().isEmpty());
		assertEquals(code, ((Coupon) actualPost1).getCode());

		assertNotNull(actualPost2);
		assertEquals(post2, actualPost2);
		assertEquals(category2.getId(), actualPost2.getCategory().getId());
		assertEquals(description, actualPost2.getDescription());
		assertEquals(price, actualPost2.getPrice());
		assertEquals(title, actualPost2.getTitle());
		assertEquals(url, actualPost2.getUrl());
		assertEquals(user2.getId(), actualPost2.getUser().getId());
		assertTrue(actualPost2.getImages().isEmpty());

	}

	/**
	 * Test create post with maximum file size exceeded.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 * @throws MissingRequiredParameterException
	 */
	@Test
	public void testCreatePostWithInstanceNotFound()
			throws MaximumImageSizeExceededException, DuplicateInstanceException, MissingRequiredParameterException {
		User user = signUpUser("userName");
		long nonExistentId = -1L;

		assertThrows(InstanceNotFoundException.class,
				() -> postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(),
						nonExistentId, new ArrayList<byte[]>(), "Coupon", Map.ofEntries(entry("code", "EXTRA25"))));
	}

	/**
	 * Test create post with maximum file size exceeded.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 * @throws MissingRequiredParameterException
	 */
	@Test
	public void testCreatePostWithMaximumImageSize()
			throws MaximumImageSizeExceededException, DuplicateInstanceException, MissingRequiredParameterException {
		User user = signUpUser("userName");
		Category category = new Category(1L, "Comida");

		byte[] maxSizeImageBytes = new byte[1024001];

		assertThrows(MaximumImageSizeExceededException.class,
				() -> postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(),
						category.getId(), List.of(maxSizeImageBytes), "Coupon",
						Map.ofEntries(entry("code", "EXTRA25"))));
	}

	/**
	 * Test create post with missing required parameter.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 * @throws MissingRequiredParameterException
	 */
	@Test
	public void testCreatePostWithMissingRequiredParameter()
			throws MaximumImageSizeExceededException, DuplicateInstanceException, MissingRequiredParameterException {
		User user = signUpUser("userName");
		Category category = new Category(1L, "Comida");

		byte[] maxSizeImageBytes = new byte[1024001];

		assertThrows(MissingRequiredParameterException.class,
				() -> postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(),
						category.getId(), List.of(maxSizeImageBytes), "Coupon", Map.ofEntries()));
	}

	@Test
	@Ignore
	public void testFindPostById()
			throws InstanceNotFoundException, DuplicateInstanceException, MaximumImageSizeExceededException {

		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		Post foundPost = postService.findPostById(post.getId());

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
		long nonExistentId = -1L;
		assertThrows(InstanceNotFoundException.class, () -> postService.findPostById(nonExistentId));
	}

	@Test
	public void testDeletePost() throws DuplicateInstanceException, MaximumImageSizeExceededException,
			InstanceNotFoundException, PermissionException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		Post foundPost = postService.findPostById(post.getId());

		postService.deletePost(u.getId(), post.getId());

		assertThrows(InstanceNotFoundException.class, () -> {
			postService.findPostById(post.getId());
		});
	}

	@Test
	public void testDeletePostNotBelongingToUser()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		User u2 = signUpUser("Meme");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		Post foundPost = postService.findPostById(post.getId());

		assertThrows(PermissionException.class, () -> {
			postService.deletePost(u2.getId(), post.getId());
		});
	}

	@Test
	public void testDeletePostNonExistentPost()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		long nonExistentId = -1L;

		assertThrows(InstanceNotFoundException.class, () -> {
			postService.deletePost(u.getId(), nonExistentId);
		});
	}

	@Test
	public void testDeletePostNonExistentUser()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		Post foundPost = postService.findPostById(post.getId());

		long nonExistentId = -1L;

		assertThrows(PermissionException.class, () -> {
			postService.deletePost(nonExistentId, post.getId());
		});
	}
	
	@Test
	public void testMarkAsExpired() throws DuplicateInstanceException, MaximumImageSizeExceededException,
			InstanceNotFoundException, PermissionException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		Post foundPost = postService.findPostById(post.getId());

		assertFalse(foundPost.isExpired());

		postService.markAsExpired(u.getId(), foundPost.getId(), true);

		foundPost = postService.findPostById(post.getId());

		assertTrue(foundPost.isExpired());
		
		postService.markAsExpired(u.getId(), foundPost.getId(), false);

		foundPost = postService.findPostById(post.getId());

		assertFalse(foundPost.isExpired());

	}

	@Test
	public void testMarkAsExpiredNoPost()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");
		long nonExistentId = -1L;
		assertThrows(InstanceNotFoundException.class, () -> postService.markAsExpired(u.getId(), nonExistentId, true));
	}

	@Test
	public void testMarkAsExpiredNoUser()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		User u2 = signUpUser("Meme");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		postService.findPostById(post.getId());

		assertThrows(PermissionException.class, () -> {
			postService.markAsExpired(u2.getId(), post.getId(), true);
		});
	}

	@Test
	public void testMarkAsExpiredNonExistentUser()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		long nonExistentId = -1L;

		assertThrows(PermissionException.class, () -> postService.markAsExpired(nonExistentId, post.getId(), true));
	}
	

}
