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
import java.util.List;
import java.util.Map;

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
	private CatalogService catalogService;

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
		return postDao.save(
				new Offer("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user, category));
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
		Category category = categoryDao.save(new Category("Comida"));

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
		Category category = categoryDao.save(new Category("Comida"));

		byte[] maxSizeImageBytes = new byte[1024001];

		assertThrows(MissingRequiredParameterException.class,
				() -> postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(),
						category.getId(), List.of(maxSizeImageBytes), "Coupon", Map.ofEntries()));
	}

	@Test
	public void testDeletePost() throws DuplicateInstanceException, MaximumImageSizeExceededException,
			InstanceNotFoundException, PermissionException {
		User u = signUpUser("Pepe");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		catalogService.findPostById(post.getId());

		postService.deletePost(u.getId(), post.getId());

		assertThrows(InstanceNotFoundException.class, () -> {
			catalogService.findPostById(post.getId());
		});
	}

	@Test
	public void testDeletePostNotBelongingToUser()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		User u = signUpUser("Pepe");

		User u2 = signUpUser("Meme");

		Category c = createCategory("Car");

		Post post = createPost(u, c);

		catalogService.findPostById(post.getId());

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

		catalogService.findPostById(post.getId());

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

		Post foundPost = catalogService.findPostById(post.getId());

		assertFalse(foundPost.isExpired());

		postService.markAsExpired(u.getId(), foundPost.getId(), true);

		foundPost = catalogService.findPostById(post.getId());

		assertTrue(foundPost.isExpired());

		postService.markAsExpired(u.getId(), foundPost.getId(), false);

		foundPost = catalogService.findPostById(post.getId());

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

		catalogService.findPostById(post.getId());

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

	/**
	 * Test update offer.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 * @throws MissingRequiredParameterException
	 * @throws InstanceNotFoundException
	 * @throws PermissionException
	 *
	 */
	@Test
	public void testUpdatePostOffer() throws DuplicateInstanceException, MaximumImageSizeExceededException,
			InstanceNotFoundException, MissingRequiredParameterException, PermissionException {
		User user = signUpUser("userName1");
		Category category = createCategory("category1");
		String title = "title";
		String description = "description";
		String url = "http//www.google.es";
		BigDecimal price = new BigDecimal(10);

		Post post = postService.createPost(title, description, url, price, user.getId(), category.getId(),
				new ArrayList<byte[]>(), "Offer", Map.ofEntries());

		Category newCategory = createCategory("category2");
		String newTitle = "new title";
		String newDescription = "new description";
		String newUrl = "https://www.bing.com";
		BigDecimal newPrice = new BigDecimal(12);

		Post updatedPost = postService.updatePost(post.getId(), newTitle, newDescription, newUrl, newPrice,
				user.getId(), newCategory.getId(), new ArrayList<byte[]>(), "Offer", Map.ofEntries());

		Post actualPost = postDao.findById(post.getId()).get();

		assertNotNull(actualPost);
		assertEquals(updatedPost, actualPost);
		assertEquals(newCategory.getId(), actualPost.getCategory().getId());
		assertEquals(newDescription, actualPost.getDescription());
		assertEquals(newPrice, actualPost.getPrice());
		assertEquals(newTitle, actualPost.getTitle());
		assertEquals(newUrl, actualPost.getUrl());
		assertEquals(user.getId(), actualPost.getUser().getId());
		assertTrue(actualPost.getImages().isEmpty());

	}

	/**
	 * Test update coupon.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 * @throws MissingRequiredParameterException
	 * @throws InstanceNotFoundException
	 * @throws PermissionException
	 *
	 */
	@Test
	public void testUpdatePostCoupon() throws DuplicateInstanceException, MaximumImageSizeExceededException,
			InstanceNotFoundException, MissingRequiredParameterException, PermissionException {
		User user = signUpUser("userName1");
		Category category = createCategory("category1");
		String title = "title";
		String description = "description";
		String url = "http//www.google.es";
		BigDecimal price = new BigDecimal(10);

		String code = "EXTRA25";
		Post post = postService.createPost(title, description, url, price, user.getId(), category.getId(),
				new ArrayList<byte[]>(), "Coupon", Map.ofEntries(entry("code", code)));

		Category newCategory = createCategory("category2");
		String newTitle = "new title";
		String newDescription = "new description";
		String newUrl = "https://www.bing.com";
		BigDecimal newPrice = new BigDecimal(12);

		String newCode = "EXTRANEW25";

		Post updatedPost = postService.updatePost(post.getId(), newTitle, newDescription, newUrl, newPrice,
				user.getId(), newCategory.getId(), new ArrayList<byte[]>(), "Coupon",
				Map.ofEntries(entry("code", newCode)));

		Post actualPost = postDao.findById(post.getId()).get();

		assertNotNull(actualPost);
		assertEquals(updatedPost, actualPost);
		assertEquals(newCategory.getId(), actualPost.getCategory().getId());
		assertEquals(newDescription, actualPost.getDescription());
		assertEquals(newPrice, actualPost.getPrice());
		assertEquals(newTitle, actualPost.getTitle());
		assertEquals(newUrl, actualPost.getUrl());
		assertEquals(user.getId(), actualPost.getUser().getId());
		assertTrue(actualPost.getImages().isEmpty());
		assertEquals(newCode, ((Coupon) actualPost).getCode());

	}

	/**
	 * Test update inexistent post.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 *
	 */
	@Test
	public void testUpdateInexistentPost() throws DuplicateInstanceException, MaximumImageSizeExceededException {
		long inexistentPostId = -1L;
		User user = signUpUser("userName1");
		Category category = createCategory("category1");
		assertThrows(InstanceNotFoundException.class,
				() -> postService.updatePost(inexistentPostId, "title", "description", "url", new BigDecimal(10),
						user.getId(), category.getId(), new ArrayList<byte[]>(), "Coupon",
						Map.ofEntries(entry("code", "EXTRA25"))));
	}

	/**
	 * Test update with inexistent category.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 * @throws MissingRequiredParameterException
	 * @throws InstanceNotFoundException
	 *
	 */
	@Test
	public void testUpdateWithInexistentCategory() throws DuplicateInstanceException, MaximumImageSizeExceededException,
			InstanceNotFoundException, MissingRequiredParameterException {
		long inexistentCategoryId = -1L;
		User user = signUpUser("userName1");
		Category category = createCategory("category1");
		Post post = postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(),
				category.getId(), new ArrayList<byte[]>(), "Coupon", Map.ofEntries(entry("code", "EXTRA25")));
		assertThrows(InstanceNotFoundException.class,
				() -> postService.updatePost(post.getId(), "title", "description", "url", new BigDecimal(10),
						user.getId(), inexistentCategoryId, new ArrayList<byte[]>(), "Coupon",
						Map.ofEntries(entry("code", "EXTRA25"))));
	}

	/**
	 * Test update post with maximum file size exceeded.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 * @throws MissingRequiredParameterException
	 * @throws InstanceNotFoundException
	 */
	@Test
	public void testUpdatePostWithMaximumImageSize() throws MaximumImageSizeExceededException,
			DuplicateInstanceException, MissingRequiredParameterException, InstanceNotFoundException {
		User user = signUpUser("userName1");
		Category category = createCategory("category1");
		Post post = postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(),
				category.getId(), new ArrayList<byte[]>(), "Coupon", Map.ofEntries(entry("code", "EXTRA25")));

		byte[] maxSizeImageBytes = new byte[1024001];

		assertThrows(MaximumImageSizeExceededException.class,
				() -> postService.updatePost(post.getId(), "title", "description", "url", new BigDecimal(10),
						user.getId(), category.getId(), List.of(maxSizeImageBytes), "Coupon",
						Map.ofEntries(entry("code", "EXTRA25"))));
	}

	/**
	 * Test update post with missing required parameter.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 * @throws MissingRequiredParameterException
	 * @throws InstanceNotFoundException
	 */
	@Test
	public void testUpdatePostWithMissingRequiredParameter() throws MaximumImageSizeExceededException,
			DuplicateInstanceException, MissingRequiredParameterException, InstanceNotFoundException {
		User user = signUpUser("userName1");
		Category category = createCategory("category1");
		Post post = postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(),
				category.getId(), new ArrayList<byte[]>(), "Coupon", Map.ofEntries(entry("code", "EXTRA25")));

		byte[] maxSizeImageBytes = new byte[1024001];

		assertThrows(MissingRequiredParameterException.class,
				() -> postService.updatePost(post.getId(), "title", "description", "url", new BigDecimal(10),
						user.getId(), category.getId(), List.of(maxSizeImageBytes), "Coupon", Map.ofEntries()));
	}

}
