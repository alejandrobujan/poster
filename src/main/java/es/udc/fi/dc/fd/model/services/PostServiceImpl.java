package es.udc.fi.dc.fd.model.services;

import static java.util.Map.entry;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
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

	/** The post handlers. */
	private final Map<String, PostHandler> handlers;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The permission checker. */
	@Autowired
	private PermissionChecker permissionChecker;

	@Autowired
	private NotificationService notificationService;

	/**
	 * Instantiates a new post service impl.
	 * 
	 * @param offerHandler  the offer handler
	 * @param couponHandler the coupon handler
	 */
	@Autowired
	public PostServiceImpl(OfferHandler offerHandler, CouponHandler couponHandler) {
		this.handlers = Map.ofEntries(entry("Offer", offerHandler), entry("Coupon", couponHandler));
	}

	/**
	 * Create post.
	 * 
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url associated to the post
	 * @param price       the price of the post
	 * @param userId      the user id who create the post
	 * @param categoryId  the category id associated to the post
	 * @param imageList   the images associated to the post
	 * @param type        the post type
	 * @param properties  the properties of the post
	 * @return the new post
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 * @throws IncorrectFormValuesException      the incorrect form values exception
	 */
	@Override
	public Post createPost(String title, String description, String url, BigDecimal price, Long userId, Long categoryId,
			List<byte[]> imageList, String type, Map<String, String> properties, LocalDateTime expirationDate)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException,
			IncorrectFormValuesException {

		PostHandler postHandler = handlers.get(type);

		return postHandler.handleCreate(title, description, url, price, userId, categoryId, imageList, properties,
				expirationDate);

	}

	/**
	 * Delete post.
	 * 
	 * @param userId the user id who delete the post
	 * @param postId the post id associated to the post
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws PermissionException       the permission exception
	 */
	@Override
	public void deletePost(Long userId, Long postId) throws InstanceNotFoundException, PermissionException {
		Post post = permissionChecker.checkPostExistsAndBelongsTo(postId, userId);

		notificationService.sendNotification(post, "deleted");

		postDao.delete(post);

	}

	/**
	 * Update post.
	 * 
	 * @param postId      the post id
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url associated to the post
	 * @param price       the price of the post
	 * @param userId      the user id who create the post
	 * @param categoryId  the category id associated to the post
	 * @param imageList   the images associated to the post
	 * @param type        the post type
	 * @param properties  the properties of the post
	 * @return the updated post
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum image size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 * @throws PermissionException               the permission exception
	 */
	@Override
	public Post updatePost(Long postId, String title, String description, String url, BigDecimal price, Long userId,
			Long categoryId, List<byte[]> imageList, String type, Map<String, String> properties,
			LocalDateTime expirationDate) throws InstanceNotFoundException, MaximumImageSizeExceededException,
			MissingRequiredParameterException, PermissionException, IncorrectFormValuesException {

		permissionChecker.checkUserExists(userId);
		permissionChecker.checkPostExistsAndBelongsTo(postId, userId);
		PostHandler postHandler = handlers.get(type);

		return postHandler.handleUpdate(postId, title, description, url, price, userId, categoryId, imageList,
				properties, expirationDate);
	}

	/**
	 * Mark a post as expired.
	 * 
	 * @param userId the user id associated to the post
	 * @param postId the post id
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws PermissionException       the permission exception
	 */
	@Override
	public LocalDateTime markAsExpired(Long userId, Long postId) throws InstanceNotFoundException, PermissionException {
		Post post = permissionChecker.checkPostExistsAndBelongsTo(postId, userId);

		post.setExpirationDate(LocalDateTime.now());

		notificationService.sendNotification(post);

		return postDao.save(post).getExpirationDate();
	}

	/**
	 * Mark a post as valid.
	 * 
	 * @param postId the post id
	 * @return the validation date updated
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@Override
	public LocalDateTime markAsValid(Long postId) throws InstanceNotFoundException {
		Post post = permissionChecker.checkPost(postId);

		post.setValidationDate(LocalDateTime.now());

		notificationService.sendNotification(post);

		return postDao.save(post).getValidationDate();
	}

}
