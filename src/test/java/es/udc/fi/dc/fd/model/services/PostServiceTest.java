package es.udc.fi.dc.fd.model.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
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
	private UserDao userDao;

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
	 * Creates the post.
	 *
	 * @param user     the user of the post
	 * @param category the category of the post
	 * @return the post
	 */
	private Post createPost(User user, Category category) {
		return new Post("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user, category);
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
	private void assertPostBlockEquals(Block<Post> actualBlock, List<Post> expectedPosts, boolean expectedExistMoreItems) {
		assertTrue(expectedPosts.containsAll(actualBlock.getItems()));
		assertTrue(actualBlock.getItems().containsAll(expectedPosts));
		assertEquals(expectedExistMoreItems, actualBlock.getExistMoreItems());
	}

	/**
	 * Test find all categories.
	 *
	 */
	@Test
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
	 * @throws InstanceNotFoundException 
	 *
	 */
	@Test
	public void testCreatePost() throws MaximumImageSizeExceededException, DuplicateInstanceException, InstanceNotFoundException {

		
		String title = "title";
		String description = "description";
		String url = "http//www.google.es";
		BigDecimal price = new BigDecimal(10);
		
		User user1 = signUpUser("userName1");
		User user2 = signUpUser("userName2");
		
		List<User> users = Arrays.asList(user2, user1);

		List<Category> listCategory = postService.findAllCategories();
		
		postService.createPost(title, description, url, price, 
				user1.getId(), listCategory.get(1).getId(), new ArrayList<byte[]>());
		postService.createPost(title, description, url, price, 
				user2.getId(), listCategory.get(2).getId(), new ArrayList<byte[]>());
		
		List<Post> posts = postService.findAllPosts(0, 2).getItems();
		
		for (int i = 0 ; i < posts.size() ; i++) {
			
			assertEquals(listCategory.get(posts.size() - i).getId(), posts.get(i).getCategory().getId());
			assertEquals(description, posts.get(i).getDescription());
			assertEquals(price, posts.get(i).getPrice());
			assertEquals(title, posts.get(i).getTitle());
			assertEquals(url, posts.get(i).getUrl());
			assertEquals(users.get(i).getId(), posts.get(i).getUser().getId());
			assertTrue(posts.get(i).getImages().isEmpty());
			
		}
		
		assertFalse(postService.findAllPosts(0, 2).getExistMoreItems());

	}

	/**
	 * Test create post with maximum file size exceeded.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 */
	@Test
	public void testCreatePostWithInstanceNotFound()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		User user = signUpUser("userName");
		long nonExistentId = -1L;

		assertThrows(InstanceNotFoundException.class, () -> postService.createPost("title", "description", "url",
				new BigDecimal(10), user.getId(), nonExistentId, new ArrayList<byte[]>()));
	}

	/**
	 * Test create post with maximum file size exceeded.
	 * 
	 * @throws MaximumImageSizeExceededException
	 * @throws DuplicateInstanceException
	 */
	@Test
	public void testCreatePostWithMaximumImageSize()
			throws MaximumImageSizeExceededException, DuplicateInstanceException {
		User user = signUpUser("userName");
		Category category = new Category(1L, "Comida");

		byte[] maxSizeImageBytes = new byte[1024001];

		assertThrows(MaximumImageSizeExceededException.class, () -> postService.createPost("title", "description",
				"url", new BigDecimal(10), user.getId(), category.getId(), List.of(maxSizeImageBytes)));
	}

}
