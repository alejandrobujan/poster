package es.udc.fi.dc.fd.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.services.RatingService;
import es.udc.fi.dc.fd.rest.dtos.RatePostDto;

/**
 * The Class RatingController.
 */
@RestController
@RequestMapping("/api/rating")
public class RatingController {

	/** The rating service. */
	@Autowired
	private RatingService ratingService;

	/**
	 * @param userId the user id
	 * @param id     the id
	 * @return the rate post dto
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@PostMapping("/post/{id}/ratePositive")
	public RatePostDto ratePostPositive(@RequestAttribute Long userId, @PathVariable Long id)
			throws InstanceNotFoundException {

		Post post = ratingService.ratePostPositive(userId, id);

		return new RatePostDto(post.getPositiveRatings(), post.getNegativeRatings());

	}

	/**
	 * @param userId the user id
	 * @param id     the id
	 * @return the rate post dto
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@PostMapping("/post/{id}/rateNegative")
	public RatePostDto ratePostNegative(@RequestAttribute Long userId, @PathVariable Long id)
			throws InstanceNotFoundException {

		Post post = ratingService.ratePostNegative(userId, id);

		return new RatePostDto(post.getPositiveRatings(), post.getNegativeRatings());

	}

}