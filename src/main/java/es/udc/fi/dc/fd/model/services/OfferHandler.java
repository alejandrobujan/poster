package es.udc.fi.dc.fd.model.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Image;
import es.udc.fi.dc.fd.model.entities.ImageDao;
import es.udc.fi.dc.fd.model.entities.Offer;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;

/**
 * 
 */
@Component
@Transactional
public class OfferHandler implements PostHandler {

	@Autowired
	private PermissionChecker permissionChecker;

	/** The category dao. */
	@Autowired
	private CategoryDao categoryDao;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The image dao. */
	@Autowired
	private ImageDao imageDao;

	@Override
	public Post handleCreate(String title, String description, String url, BigDecimal price, Long userId,
			Long categoryId, List<byte[]> imageList, Map<String, String> properties)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException {
		User user = permissionChecker.checkUser(userId);

		Category category = null;

		if (categoryId != null) {
			Optional<Category> categoryOptional = categoryDao.findById(categoryId);

			if (!categoryOptional.isPresent())
				throw new InstanceNotFoundException("project.entities.category", categoryId);

			category = categoryOptional.get();
		}

		LocalDateTime creationDate = LocalDateTime.now();

		Post post = postDao.save(new Offer(title, description, url, price, creationDate, user, category));

		int maxSize = 1024000;
		Image image;

		for (byte[] imageBytes : imageList) {

			image = new Image(imageBytes, post);

			if (image.getData().length > maxSize) {
				throw new MaximumImageSizeExceededException(maxSize);
			}

			post.addImage(image);
			imageDao.save(image);

		}

		return post;
	}

}
