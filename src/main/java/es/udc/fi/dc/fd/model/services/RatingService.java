package es.udc.fi.dc.fd.model.services;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;

/**
 * The Interface RatingService.
 */
public interface RatingService {
	/**
	 * @param userId
	 * @param postId
	 * @throws InstanceNotFoundException
	 */
	Post ratePostPositive(Long userId, Long postId) throws InstanceNotFoundException;

	/**
	 * @param userId
	 * @param postId
	 * @throws InstanceNotFoundException
	 */
	Post ratePostNegative(Long userId, Long postId) throws InstanceNotFoundException;
}
