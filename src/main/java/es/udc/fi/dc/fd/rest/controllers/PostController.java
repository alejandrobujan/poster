package es.udc.fi.dc.fd.rest.controllers;

import static es.udc.fi.dc.fd.rest.dtos.CategoryConversor.toCategoryDtos;
import static es.udc.fi.dc.fd.rest.dtos.PostConversor.toPostSummaryDtos;
import static java.util.Map.entry;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.services.Block;
import es.udc.fi.dc.fd.model.services.PostService;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;
import es.udc.fi.dc.fd.rest.dtos.BlockDto;
import es.udc.fi.dc.fd.rest.dtos.CategoryDto;
import es.udc.fi.dc.fd.rest.dtos.CouponConversor;
import es.udc.fi.dc.fd.rest.dtos.OfferConversor;
import es.udc.fi.dc.fd.rest.dtos.PostConversor;
import es.udc.fi.dc.fd.rest.dtos.PostDto;
import es.udc.fi.dc.fd.rest.dtos.PostParamsDto;
import es.udc.fi.dc.fd.rest.dtos.PostSummaryDto;

/**
 * The Class PostController.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

	private final Map<String, PostConversor> conversors;

	@Autowired
	private PostService postService;

	@Autowired
	public PostController(OfferConversor offerConversor, CouponConversor couponConversor) {
		this.conversors = Map.ofEntries(entry("Offer", offerConversor), entry("Coupon", couponConversor));
	}

	@PostMapping("/post")
	public PostDto createPost(@RequestAttribute Long userId, @Validated @RequestBody PostParamsDto params)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException {

		PostConversor postConversor = conversors.get(params.getType());

		Post post = postService.createPost(params.getTitle(), params.getDescription(), params.getUrl(),
				params.getPrice(), userId, params.getCategoryId(), params.getImages(), params.getType(),
				params.getProperties());

		return postConversor.toPostDto(post);

	}

	@GetMapping("/feed")
	public BlockDto<PostSummaryDto> findAllPosts(@RequestParam(defaultValue = "0") int page) {

		Block<Post> posts = postService.findAllPosts(page, 4);

		return new BlockDto<>(toPostSummaryDtos(posts.getItems()), posts.getExistMoreItems());

	}

	@GetMapping("/categories")
	public List<CategoryDto> findAllCategories() {

		return toCategoryDtos(postService.findAllCategories());

	}

}