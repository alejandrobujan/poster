package es.udc.fi.dc.fd.model.services;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Image;
import es.udc.fi.dc.fd.model.entities.ImageDao;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;

/**
 * The Class PostServiceImpl.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {
	
	/** The permission checker. */
	@Autowired
    private PermissionChecker permissionChecker;
	
	/** The post dao. */
	@Autowired
	private PostDao postDao;
	
	/** The category dao. */
	@Autowired
	private CategoryDao categoryDao;
	
	/** The image dao. */
	@Autowired
	private ImageDao imageDao;

	/**
	 * Create post.
	 * 
	 * @param post
	 */
	@Override
	public void createPost(String title, String description, String url, BigDecimal price, Long userId, 
			Long categoryId, List<byte[]> imageList) throws InstanceNotFoundException{
		
		User user = permissionChecker.checkUser(userId);
		
		Category category = null;
		
		if(categoryId != null) {
			Optional<Category> categoryOptional = categoryDao.findById(categoryId);
			
	        if (!categoryOptional.isPresent())
	            throw new InstanceNotFoundException("project.entities.category", categoryId);
	        
	        category = categoryOptional.get();
		}
        
        LocalDateTime creationDate = LocalDateTime.now();
        
		Post post = postDao.save(new Post(title, description, url, price, creationDate, user, category));
		
		for (byte[] imageBytes : imageList) {
			
			Image image = new Image(imageBytes, post);
			
			post.addImage(image);
			imageDao.save(image);
			
		}
		
	}

	/**
	 * Find all posts.
	 * 
	 * @param page
	 * @param size
	 * @return the posts.
	 */
	@Override
	public Block<Post> findAllPosts(int page, int size) {
		
		Pageable request = PageRequest.of(page, size);

        Slice<Post> postSlice = postDao.findAllByOrderByCreationDateDesc(request);

        return new Block<>(postSlice.getContent(), postSlice.hasNext());
	}
	
	
	/**
	 * Find all categories.
	 * 
	 * @return the categories.
	 */
	@Override
	public List<Category> findAllCategories() {
		
		Iterable<Category> categories = categoryDao.findAll(Sort.by(Sort.Direction.ASC, "id"));
		List<Category> categoriesAsList = new ArrayList<>();
		
		categories.forEach(c -> categoriesAsList.add(c));
		
		return categoriesAsList;
	}

}
