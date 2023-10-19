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

	private final static long NON_EXISTENT_ID = -1L;
	private final static int EXCEEDED_BYTE_SIZE = 1024001;

	private User user;

	private Category category;

	private Post offer;

	private Post coupon;

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
	 * Creates the offer.
	 *
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the offer
	 */
	private Post createOffer(User user, Category category) {
		return postDao.save(
				new Offer("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user, category));
	}

	/**
	 * Creates the coupon.
	 *
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
	 */
	private Post createCoupon(User user, Category category) {
		return postDao.save(new Coupon("title", "description", "url", new BigDecimal(10), LocalDateTime.now(),
				"EXTRA25", user, category));
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

	@Before
	public void setUp() throws DuplicateInstanceException, MaximumImageSizeExceededException {
		user = signUpUser("user");
		category = createCategory("category");
		offer = createOffer(user, category);
		coupon = createCoupon(user, category);
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
		String url = "http://www.google.es";
		BigDecimal price = new BigDecimal(10);
		String code = "EXTRA25";

		Post post1 = postService.createPost(title, description, url, price, user.getId(), category.getId(),
				new ArrayList<byte[]>(), "Coupon", Map.ofEntries(entry("code", code)));
		Post post2 = postService.createPost(title, description, url, price, user.getId(), category.getId(),
				new ArrayList<byte[]>(), "Offer", Map.ofEntries());

		Post actualPost1 = postDao.findById(post1.getId()).get();
		Post actualPost2 = postDao.findById(post2.getId()).get();

		assertNotNull(actualPost1);
		assertEquals(post1, actualPost1);
		assertEquals(category.getId(), actualPost1.getCategory().getId());
		assertEquals(description, actualPost1.getDescription());
		assertEquals(price, actualPost1.getPrice());
		assertEquals(title, actualPost1.getTitle());
		assertEquals(url, actualPost1.getUrl());
		assertEquals(user.getId(), actualPost1.getUser().getId());
		assertTrue(actualPost1.getImages().isEmpty());
		assertEquals(code, ((Coupon) actualPost1).getCode());

		assertNotNull(actualPost2);
		assertEquals(post2, actualPost2);
		assertEquals(category.getId(), actualPost2.getCategory().getId());
		assertEquals(description, actualPost2.getDescription());
		assertEquals(price, actualPost2.getPrice());
		assertEquals(title, actualPost2.getTitle());
		assertEquals(url, actualPost2.getUrl());
		assertEquals(user.getId(), actualPost2.getUser().getId());
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
		assertThrows(InstanceNotFoundException.class,
				() -> postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(),
						NON_EXISTENT_ID, new ArrayList<byte[]>(), "Coupon", Map.ofEntries(entry("code", "EXTRA25"))));
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
		assertThrows(MaximumImageSizeExceededException.class,
				() -> postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(),
						category.getId(), List.of(new byte[EXCEEDED_BYTE_SIZE]), "Coupon",
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
		assertThrows(MissingRequiredParameterException.class,
				() -> postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(),
						category.getId(), List.of(new byte[EXCEEDED_BYTE_SIZE]), "Coupon", Map.ofEntries()));
	}

	@Test
	public void testDeletePost() throws DuplicateInstanceException, MaximumImageSizeExceededException,
			InstanceNotFoundException, PermissionException {
		postService.deletePost(user.getId(), offer.getId());

		assertThrows(InstanceNotFoundException.class, () -> {
			catalogService.findPostById(offer.getId());
		});
	}

	@Test
	public void testDeletePostNotBelongingToUser()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		assertThrows(PermissionException.class, () -> {
			postService.deletePost(signUpUser("user2").getId(), offer.getId());
		});
	}

	@Test
	public void testDeletePostNonExistentPost()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		assertThrows(InstanceNotFoundException.class, () -> {
			postService.deletePost(user.getId(), NON_EXISTENT_ID);
		});
	}

	@Test
	public void testDeletePostNonExistentUser()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		assertThrows(PermissionException.class, () -> {
			postService.deletePost(NON_EXISTENT_ID, offer.getId());
		});
	}

	@Test
	public void testMarkAsExpired() throws DuplicateInstanceException, MaximumImageSizeExceededException,
			InstanceNotFoundException, PermissionException {

		assertFalse(offer.isExpired());

		postService.markAsExpired(user.getId(), offer.getId(), true);
		offer = catalogService.findPostById(offer.getId());

		assertTrue(offer.isExpired());

		postService.markAsExpired(user.getId(), offer.getId(), false);
		offer = catalogService.findPostById(offer.getId());

		assertFalse(offer.isExpired());
	}

	@Test
	public void testMarkAsExpiredNoPost()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		assertThrows(InstanceNotFoundException.class,
				() -> postService.markAsExpired(user.getId(), NON_EXISTENT_ID, true));
	}

	@Test
	public void testMarkAsExpiredNoUser()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		assertThrows(PermissionException.class, () -> {
			postService.markAsExpired(signUpUser("user2").getId(), offer.getId(), true);
		});
	}

	@Test
	public void testMarkAsExpiredNonExistentUser()
			throws DuplicateInstanceException, MaximumImageSizeExceededException, InstanceNotFoundException {
		assertThrows(PermissionException.class, () -> postService.markAsExpired(NON_EXISTENT_ID, offer.getId(), true));
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
		Category newCategory = createCategory("category2");
		String newTitle = "new title";
		String newDescription = "new description";
		String newUrl = "https://www.bing.com";
		BigDecimal newPrice = new BigDecimal(12);

		Post updatedPost = postService.updatePost(offer.getId(), newTitle, newDescription, newUrl, newPrice,
				user.getId(), newCategory.getId(), new ArrayList<byte[]>(), "Offer", Map.ofEntries());

		Post actualPost = postDao.findById(offer.getId()).get();

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
		Category newCategory = createCategory("category2");
		String newTitle = "new title";
		String newDescription = "new description";
		String newUrl = "https://www.bing.com";
		BigDecimal newPrice = new BigDecimal(12);

		String newCode = "EXTRANEW25";

		Post updatedPost = postService.updatePost(coupon.getId(), newTitle, newDescription, newUrl, newPrice,
				user.getId(), newCategory.getId(), new ArrayList<byte[]>(), "Coupon",
				Map.ofEntries(entry("code", newCode)));

		Post actualPost = postDao.findById(coupon.getId()).get();

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
		assertThrows(InstanceNotFoundException.class,
				() -> postService.updatePost(NON_EXISTENT_ID, "title", "description", "url", new BigDecimal(10),
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
		assertThrows(InstanceNotFoundException.class,
				() -> postService.updatePost(coupon.getId(), "title", "description", "url", new BigDecimal(10),
						user.getId(), NON_EXISTENT_ID, new ArrayList<byte[]>(), "Coupon",
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
		assertThrows(MaximumImageSizeExceededException.class,
				() -> postService.updatePost(coupon.getId(), "title", "description", "url", new BigDecimal(10),
						user.getId(), category.getId(), List.of(new byte[EXCEEDED_BYTE_SIZE]), "Coupon",
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
		assertThrows(MissingRequiredParameterException.class,
				() -> postService.updatePost(coupon.getId(), "title", "description", "url", new BigDecimal(10),
						user.getId(), category.getId(), List.of(new byte[EXCEEDED_BYTE_SIZE]), "Coupon",
						Map.ofEntries()));
	}

}
