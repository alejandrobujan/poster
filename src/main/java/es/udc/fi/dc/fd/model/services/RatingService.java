package es.udc.fi.dc.fd.model.services;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;

/**
 * The Interface RatingService.
 */
public interface RatingService {
	/**
	 * Rate post positive
	 * 
	 * @param userId the user id associated to the rating
	 * @param postId the post id associated to the rating
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	Post ratePostPositive(Long userId, Long postId) throws InstanceNotFoundException;

	/**
	 * Rate post negative
	 * 
	 * @param userId the user id associated to the rating
	 * @param postId the post id associated to the rating
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	Post ratePostNegative(Long userId, Long postId) throws InstanceNotFoundException;
}
