package es.udc.fi.dc.fd.model.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Image;
import es.udc.fi.dc.fd.model.entities.ImageDao;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectFormValuesException;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

/**
 * The Class PostHandler.
 */
public abstract class PostHandler {

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
	 * The handle create post
	 * 
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url associated to the post
	 * @param price       the price of the post
	 * @param userId      the user id who create the post
	 * @param categoryId  the category id associated to the post
	 * @param imageList   the images associated to the post
	 * @param properties  the properties of the post
	 * @return the new post
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum images size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 * 
	 */
	public Post handleCreate(String title, String description, String url, BigDecimal price, Long userId,
			Long categoryId, List<byte[]> imageList, Map<String, String> properties, LocalDateTime expirationDate) throws InstanceNotFoundException,
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

		Post post = createPost(title.trim(), description.trim(), url.trim(), price, user, category, creationDate,
				properties, expirationDate);
		addImages(post, imageList);

		return postDao.save(post);

	}

	/**
	 * The handle update post
	 * 
	 * @param postId      the post id
	 * @param title       the title of the post
	 * @param description the description of the post
	 * @param url         the url associated to the post
	 * @param price       the price of the post
	 * @param userId      the user id who create the post
	 * @param categoryId  the category id associated to the post
	 * @param imageList   the images associated to the post
	 * @param properties  the properties of the post
	 * @return the updated post
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum image size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 * @throws PermissionException               the permission exception
	 * 
	 */
	public Post handleUpdate(Long postId, String title, String description, String url, BigDecimal price, Long userId,
			Long categoryId, List<byte[]> imageList, Map<String, String> properties, LocalDateTime expirationDate)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException,
			PermissionException, IncorrectFormValuesException {

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

		Post post = updatePost(postId, title, description, url, price, userId, category, properties, expirationDate);
		addImages(post, imageList);

		return postDao.save(post);

	}

	public void addImages(Post post, List<byte[]> imageList) throws MaximumImageSizeExceededException {
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
	}

	abstract Post createPost(String title, String description, String url, BigDecimal price, User user,
			Category category, LocalDateTime creationDate, Map<String, String> properties, LocalDateTime expirationDate)
			throws MissingRequiredParameterException;

	abstract Post updatePost(Long id, String title, String description, String url, BigDecimal price, Long userId,
			Category category, Map<String, String> properties, LocalDateTime expirationDate)
			throws MissingRequiredParameterException, InstanceNotFoundException, PermissionException;

}