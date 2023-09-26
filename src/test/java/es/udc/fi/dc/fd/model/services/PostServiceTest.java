package es.udc.fi.dc.fd.model.services;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.assertj.core.util.*;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import es.udc.fi.dc.fd.model.common.exceptions.*;

import es.udc.fi.dc.fd.model.entities.*;
import es.udc.fi.dc.fd.model.services.*;
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
    
    
    /** Maximum size for images */
	private final int MAX_SIZE = 1024001; 
    

	/**
	 * Creates the user.
	 *
	 * @param userName the user name
	 * @return the user
	 */
	private User createUser(String userName) {
		
		byte avatar[] = new byte[] {50};
		return new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com", avatar);
	}
	
	/**
	 * Creates the category.
	 *
	 * @return the category
	 */
	private Category createCategory(long id, String categoryName) {
		Category category = new Category();
		category.setId(id);
		category.setName(categoryName);;
		return category;
	}
	
	/**
	 * Creates the post.
	 *
	 * @param title the title of the post
	 * @return the post
	 */
	private Post createPost(User user, Category category) {
		return new Post("title", "description", "url", new BigDecimal(10), LocalDateTime.now(), user, category);
	}
	
    private User signUpUser(String userName) throws MaximumImageSizeExceededException {

		byte avatar[] = new byte[] {50};
        User user = new User(userName, "password", "firstName", "lastName", userName + "@" + userName + ".com", avatar);

        try {
            userService.signUp(user);
        } catch (DuplicateInstanceException e) {
            throw new RuntimeException(e);
        }

        return user;

    }

	/**
	 * Test find all categories.
	 *
	 */
	@Test
	public void testfindAllCategories() {
		Category c1 = createCategory(1,"Comida");
		Category c2 = createCategory(2,"Motor");
		Category c3 = createCategory(3,"Hogar");
		Category c4 = createCategory(4,"Juguetes");
		Category c5 = createCategory(5,"Tecnología");
		Category c6 = createCategory(6,"Entretenimiento");
		List<Category> expectedListCategory = new ArrayList<>();
		Collections.addAll(expectedListCategory, c1, c2, c3, c4, c5, c6);

		
		List<Category> listCategory = postService.findAllCategories();
		
		assertEquals(expectedListCategory.size(), listCategory.size());
		
		for (int i = 0; i < listCategory.size(); i++) {
			assertEquals(listCategory.get(i).getId(), expectedListCategory.get(i).getId());
			assertEquals(listCategory.get(i).getName(), expectedListCategory.get(i).getName());
		}
		
	}

	/**
	 * Test find no categories.
	 *
	 */
    @Test
    public void testFindNoCategories() {
    	
    	categoryDao.deleteAll();
        List<Category> emptyList = new ArrayList<>();
        assertEquals(emptyList, postService.findAllCategories());
    	
    }

	/**
	 * Test create post.
	 * @throws MaximumImageSizeExceededException 
	 *
	 */
    @Test
    public void testCreatePost() throws MaximumImageSizeExceededException {
    	
    	User user1 = signUpUser("userName1");
    	User user2 = signUpUser("userName2");

    	
		List<Category> listCategory = postService.findAllCategories();
    	
    	postDao.deleteAll();
    	
    	Post post1 = createPost(user1,listCategory.get(1));
    	Post post2 = createPost(user2,listCategory.get(2));
    	
    	postDao.save(post1);
    	postDao.save(post2);
    	@SuppressWarnings("deprecation")
		Post post3 = postDao.getById(post1.getId());
    	
        assertEquals(post1.getImages(), post3.getImages());
        assertEquals(post1.getCategory(), post3.getCategory());
        assertEquals(post1.getCreationDate(), post3.getCreationDate());
        assertEquals(post1.getDescription(), post3.getDescription());
        assertEquals(post1.getId(), post3.getId());
        assertEquals(post1.getPrice(), post3.getPrice());
        assertEquals(post1.getTitle(), post3.getTitle());
        assertEquals(post1.getUrl(), post3.getUrl());
        assertEquals(post1.getUser(), post3.getUser());
        
        assertNotEquals(post1.getUser(), post2.getUser());
        assertNotEquals(post1.getCategory(), post2.getCategory());
        
        assertEquals(post1, post3);
        assertNotEquals(post1, post2);
    	
    }
    
    /**
	 * Test create post without a category.
     * @throws MaximumImageSizeExceededException 
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@Test
	public void testCreatePostWithNoCategory() throws MaximumImageSizeExceededException {
		User user = signUpUser("userName");
		List<byte[]> list = new ArrayList<byte[]>();
		assertThrows(InstanceNotFoundException.class, () -> postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(), (long) 10, list));
	}
	
	/**
	 * Test create post without a category.
     * @throws MaximumImageSizeExceededException 
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@Test
	public void testCreatePostMaximumImageSize() throws MaximumImageSizeExceededException {
		User user = signUpUser("userName");
		Category category = createCategory(1,"Comida");

		byte[] maxSizeImageBytes = new byte[MAX_SIZE];
		
		assertThrows(MaximumImageSizeExceededException.class, () -> postService.createPost("title", "description", "url", new BigDecimal(10), user.getId(), category.getId(), List.of(maxSizeImageBytes)));
	}
    
	/**
	 * Test find all posts.
	 * @throws MaximumImageSizeExceededException 
	 *
	 */
    @Test
    public void testFindAllPosts() throws MaximumImageSizeExceededException {
    	
    	User user1 = signUpUser("userName1");
    	User user2 = signUpUser("userName2");

    	
		List<Category> listCategory = postService.findAllCategories();
    	
    	postDao.deleteAll();
    	
    	Post post1 = createPost(user1,listCategory.get(1));
    	Post post2 = createPost(user2,listCategory.get(2));
    	Post post3 = createPost(user1,listCategory.get(3));
    	
    	postDao.save(post1);
    	postDao.save(post2);
    	postDao.save(post3);
    	
        Block <Post> expectedBlock = new Block<>(List.of(post1, post2), true);
        assertEquals(expectedBlock, postService.findAllPosts(0, 2));
        
    	expectedBlock = new Block<>(List.of(post1, post2, post3), false);
        assertEquals(expectedBlock, postService.findAllPosts(0, 3));
        
        expectedBlock = new Block<>(List.of(post1), false);
        assertEquals(expectedBlock, postService.findAllPosts(1, 2));
        
        expectedBlock = new Block<>(List.of(), false);
        assertEquals(expectedBlock, postService.findAllPosts(3, 1));
        
    }
    
    /**
	 * Test find no posts.
	 *
	 */
    @Test
    public void testFindNoPosts() {
    	
    	postDao.deleteAll();
        Block<Post> emptyList = new Block<>(List.of(), false);;
        assertEquals(emptyList, postService.findAllPosts(0, 1));
    	
    }
    
}
