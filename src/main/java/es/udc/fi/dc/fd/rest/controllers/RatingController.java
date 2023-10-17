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

@RestController
@RequestMapping("/api/rating")
public class RatingController {

	@Autowired
	private RatingService ratingService;

	@PostMapping("/post/{id}/ratePositive")
	public RatePostDto ratePostPositive(@RequestAttribute Long userId, @PathVariable Long id)
			throws InstanceNotFoundException {

		Post post = ratingService.ratePostPositive(userId, id);

		return new RatePostDto(post.getPositiveRatings(), post.getNegativeRatings());

	}

	@PostMapping("/post/{id}/rateNegative")
	public RatePostDto ratePostNegative(@RequestAttribute Long userId, @PathVariable Long id)
			throws InstanceNotFoundException {

		Post post = ratingService.ratePostNegative(userId, id);

		return new RatePostDto(post.getPositiveRatings(), post.getNegativeRatings());

	}

}