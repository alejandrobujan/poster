package es.udc.fi.dc.fd.model.services;

import static java.util.Map.entry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;

/**
 * The Class PostServiceImpl.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {

	private final Map<String, PostHandler> handlers;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The category dao. */
	@Autowired
	private CategoryDao categoryDao;

	@Autowired
	public PostServiceImpl(OfferHandler offerHandler, CouponHandler couponHandler) {
		this.handlers = Map.ofEntries(entry("Offer", offerHandler), entry("Coupon", couponHandler));
	}

	/**
	 * Create post.
	 * 
	 * @param post
	 */
	@Override
	public Post createPost(String title, String description, String url, BigDecimal price, Long userId, Long categoryId,
			List<byte[]> imageList, String type, Map<String, String> properties)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException {

		PostHandler postCreator = handlers.get(type);

		return postCreator.handleCreate(title, description, url, price, userId, categoryId, imageList, properties);

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

	@Override
	public Post findPostById(Long postId) throws InstanceNotFoundException {
		Optional<Post> post = postDao.findById(postId);

		if (!post.isPresent()) {
			throw new InstanceNotFoundException("project.entities.post", postId);
		}

		return post.get();
	}

}
