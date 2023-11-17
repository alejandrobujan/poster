package es.udc.fi.dc.fd.model.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.Offer;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

/**
 * The Class OfferHandler.
 */
@Component
@Transactional
public class OfferHandler extends PostHandler {

	/** The permission checker. */
	@Autowired
	private PermissionChecker permissionChecker;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	public Post createPost(String title, String description, String url, BigDecimal price, User user, Category category,
			LocalDateTime creationDate, Map<String, String> properties, LocalDateTime expirationDate) throws MissingRequiredParameterException {
		return postDao
				.save(new Offer(title.trim(), description.trim(), url.trim(), price, creationDate, user, category, expirationDate));

	}

	public Post updatePost(Long id, String title, String description, String url, BigDecimal price, Long userId,
			Category category, Map<String, String> properties, LocalDateTime expirationDate)
			throws MissingRequiredParameterException, InstanceNotFoundException, PermissionException {
		Post post = permissionChecker.checkPostExistsAndBelongsTo(id, userId);

		post.setTitle(title.trim());
		post.setDescription(description.trim());
		post.setUrl(url.trim());
		post.setPrice(price);
		post.setCategory(category);
		post.setExpirationDate(expirationDate);

		return postDao.save(post);

	}

}
