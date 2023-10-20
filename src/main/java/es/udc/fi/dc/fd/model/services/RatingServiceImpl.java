package es.udc.fi.dc.fd.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.Rating;
import es.udc.fi.dc.fd.model.entities.RatingDao;
import es.udc.fi.dc.fd.model.entities.User;

/**
 * The Class RatingServiceImpl.
 */
@Service
@Transactional
public class RatingServiceImpl implements RatingService {

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The permission checker. */
	@Autowired
	private PermissionChecker permissionChecker;

	/** The rating dao */
	@Autowired
	private RatingDao ratingDao;

	/**
	 * Rate post positive
	 * 
	 * @param userId the user id associated to the rating
	 * @param postId the post id associated to the rating
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@Override
	public Post ratePostPositive(Long userId, Long postId) throws InstanceNotFoundException {

		User user = permissionChecker.checkUser(userId);

		Optional<Post> optionalPost = postDao.findById(postId);

		if (!optionalPost.isPresent()) {
			throw new InstanceNotFoundException("project.entities.post", postId);
		}

		Post post = optionalPost.get();

		Optional<Rating> optionalRating = ratingDao.findRatingByUserIdAndPostId(userId, postId);

		if (optionalRating.isPresent()) {

			Rating rating = optionalRating.get();

			if (rating.isPositive()) {

				ratingDao.delete(rating);

				post.setPositiveRatings(post.getPositiveRatings() - 1);

			} else {

				rating.setPositive(true);

				post.setPositiveRatings(post.getPositiveRatings() + 1);

				post.setNegativeRatings(post.getNegativeRatings() - 1);

				ratingDao.save(rating);

			}

		} else {

			Rating rating = new Rating(true, user, post);

			ratingDao.save(rating);

			post.setPositiveRatings(post.getPositiveRatings() + 1);
		}

		return postDao.save(post);

	}

	/**
	 * Rate post negative
	 * 
	 * @param userId the user id associated to the rating
	 * @param postId the post id associated to the rating
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@Override
	public Post ratePostNegative(Long userId, Long postId) throws InstanceNotFoundException {

		User user = permissionChecker.checkUser(userId);

		Optional<Post> optionalPost = postDao.findById(postId);

		if (!optionalPost.isPresent()) {
			throw new InstanceNotFoundException("project.entities.post", postId);
		}

		Post post = optionalPost.get();

		Optional<Rating> optionalRating = ratingDao.findRatingByUserIdAndPostId(userId, postId);

		if (optionalRating.isPresent()) {

			Rating rating = optionalRating.get();

			if (rating.isPositive()) {

				rating.setPositive(false);

				post.setPositiveRatings(post.getPositiveRatings() - 1);

				post.setNegativeRatings(post.getNegativeRatings() + 1);

				ratingDao.save(rating);

			} else {

				ratingDao.delete(rating);

				post.setNegativeRatings(post.getNegativeRatings() - 1);

			}

		} else {

			Rating rating = new Rating(false, user, post);

			ratingDao.save(rating);

			post.setNegativeRatings(post.getNegativeRatings() + 1);

		}

		return postDao.save(post);

	}
}
