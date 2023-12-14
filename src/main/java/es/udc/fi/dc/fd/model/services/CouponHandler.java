package es.udc.fi.dc.fd.model.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.Coupon;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

/**
 * The Class CouponHandler.
 */
@Component
@Transactional
public class CouponHandler extends PostHandler {

	/** The permission checker. */
	@Autowired
	private PermissionChecker permissionChecker;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/**
	 * Create coupon
	 * 
	 * @param title          the title of the post
	 * @param description    the description of the post
	 * @param url            the url associated to the post
	 * @param price          the price of the post
	 * @param user           the user who create the post
	 * @param category       the category associated to the post
	 * @param creationDate   the creation date
	 * @param properties     the properties of the post
	 * @param expirationDate the expiration date
	 * @return the coupon
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 */
	public Post createPost(String title, String description, String url, BigDecimal price, User user, Category category,
			LocalDateTime creationDate, Map<String, String> properties, LocalDateTime expirationDate)
			throws MissingRequiredParameterException {
		String code = properties.get("code");

		if (code == null || code.trim().equals("")) {
			throw new MissingRequiredParameterException("code", getClass().getSimpleName());
		}

		return postDao.save(new Coupon(title.trim(), description.trim(), url.trim(), price, creationDate, code, user,
				category, expirationDate));

	}

	/**
	 * Update a coupon
	 * 
	 * @param id             the post id
	 * @param title          the title
	 * @param description    the description
	 * @param url            the url
	 * @param price          the price
	 * @param userId         the user id associated to the post
	 * @param category       the category associated to the post
	 * @param properties     the properties of the post
	 * @param expirationDate the expiration date
	 * @return the post
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws PermissionException               the permission exception
	 */
	public Post updatePost(Long id, String title, String description, String url, BigDecimal price, Long userId,
			Category category, Map<String, String> properties, LocalDateTime expirationDate)
			throws MissingRequiredParameterException, InstanceNotFoundException, PermissionException {
		Post post = permissionChecker.checkPostExistsAndBelongsTo(id, userId);

		String code = properties.get("code");

		if (code == null || code.trim().equals("")) {
			throw new MissingRequiredParameterException("code", getClass().getSimpleName());
		}

		post.setTitle(title.trim());
		post.setDescription(description.trim());
		post.setUrl(url.trim());
		post.setPrice(price);
		post.setCategory(category);
		post.setExpirationDate(expirationDate);
		((Coupon) post).setCode(code);

		return postDao.save(post);

	}

}
