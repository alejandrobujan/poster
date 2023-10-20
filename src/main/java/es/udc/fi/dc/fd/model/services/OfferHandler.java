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
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectFormValuesException;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

/**
 * The Class OfferHandler.
 */
@Component
@Transactional
public class OfferHandler implements PostHandler {

	/** The permission checker. */
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

	/**
	 * The handle create offer
	 * 
	 * @param title       the title of the offer
	 * @param description the description of the offer
	 * @param url         the url associated to the offer
	 * @param price       the price of the offer
	 * @param userId      the user id who create the offer
	 * @param categoryId  the category id associated to the offer
	 * @param imageList   the images associated to the offer
	 * @param properties  the properties of the offer
	 * @return the new offer
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 * 
	 */
	@Override
	public Post handleCreate(String title, String description, String url, BigDecimal price, Long userId,
			Long categoryId, List<byte[]> imageList, Map<String, String> properties) throws InstanceNotFoundException,
			MaximumImageSizeExceededException, MissingRequiredParameterException, IncorrectFormValuesException {
		User user = permissionChecker.checkUser(userId);

		if (title == null || description == null || title.trim().equals("") || description.trim().equals("")) {
			throw new IncorrectFormValuesException();
		}

		Category category = null;

		if (categoryId != null) {
			Optional<Category> categoryOptional = categoryDao.findById(categoryId);

			if (!categoryOptional.isPresent())
				throw new InstanceNotFoundException("project.entities.category", categoryId);

			category = categoryOptional.get();
		}

		LocalDateTime creationDate = LocalDateTime.now();

		Post post = postDao
				.save(new Offer(title.trim(), description.trim(), url.trim(), price, creationDate, user, category));

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

	/**
	 * The handle update offer
	 * 
	 * @param postId      the offer id
	 * @param title       the title of the offer
	 * @param description the description of the offer
	 * @param url         the url associated to the offer
	 * @param price       the price of the offer
	 * @param userId      the user id who create the offer
	 * @param categoryId  the category id associated to the offer
	 * @param imageList   the images associated to the offer
	 * @param properties  the properties of the offer
	 * @return the updated offer
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum image size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 * @throws PermissionException               the permission exception
	 * 
	 */
	@Override
	public Post handleUpdate(Long postId, String title, String description, String url, BigDecimal price, Long userId,
			Long categoryId, List<byte[]> imageList, Map<String, String> properties)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException,
			PermissionException, IncorrectFormValuesException {

		Post post = permissionChecker.checkPostExistsAndBelongsTo(postId, userId);

		if (title == null || description == null || title.trim().equals("") || description.trim().equals("")) {
			throw new IncorrectFormValuesException();
		}

		Category category = null;

		if (categoryId != null) {
			Optional<Category> categoryOptional = categoryDao.findById(categoryId);

			if (!categoryOptional.isPresent())
				throw new InstanceNotFoundException("project.entities.category", categoryId);

			category = categoryOptional.get();
		}

		post.setTitle(title.trim());
		post.setDescription(description.trim());
		post.setUrl(url.trim());
		post.setPrice(price);
		post.setCategory(category);
		post.getImages().forEach(i -> imageDao.delete(i));
		post.getImages().clear();

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

		return postDao.save(post);

	}

}
