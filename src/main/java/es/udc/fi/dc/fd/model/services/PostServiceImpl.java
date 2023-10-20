package es.udc.fi.dc.fd.model.services;

import static java.util.Map.entry;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectFormValuesException;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

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

	/** The permission checker. */
	@Autowired
	private PermissionChecker permissionChecker;

	@Autowired
	public PostServiceImpl(OfferHandler offerHandler, CouponHandler couponHandler) {
		this.handlers = Map.ofEntries(entry("Offer", offerHandler), entry("Coupon", couponHandler));
	}

	/**
	 * Create post.
	 * 
	 * @param post
	 * @throws IncorrectFormValuesException
	 */
	@Override
	public Post createPost(String title, String description, String url, BigDecimal price, Long userId, Long categoryId,
			List<byte[]> imageList, String type, Map<String, String> properties) throws InstanceNotFoundException,
			MaximumImageSizeExceededException, MissingRequiredParameterException, IncorrectFormValuesException {

		PostHandler postHandler = handlers.get(type);

		return postHandler.handleCreate(title, description, url, price, userId, categoryId, imageList, properties);

	}

	@Override
	public void deletePost(Long userId, Long postId) throws InstanceNotFoundException, PermissionException {
		Post post = permissionChecker.checkPostExistsAndBelongsTo(postId, userId);

		postDao.delete(post);
	}

	@Override
	public Post updatePost(Long postId, String title, String description, String url, BigDecimal price, Long userId,
			Long categoryId, List<byte[]> imageList, String type, Map<String, String> properties)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException,
			PermissionException, IncorrectFormValuesException {

		permissionChecker.checkUserExists(userId);
		permissionChecker.checkPostExistsAndBelongsTo(postId, userId);
		PostHandler postHandler = handlers.get(type);

		return postHandler.handleUpdate(postId, title, description, url, price, userId, categoryId, imageList,
				properties);
	}

	@Override
	public boolean markAsExpired(Long userId, Long postId, boolean expired)
			throws InstanceNotFoundException, PermissionException {
		Post post = permissionChecker.checkPostExistsAndBelongsTo(postId, userId);

		post.setExpired(expired);

		return postDao.save(post).isExpired();
	}

}
