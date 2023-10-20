package es.udc.fi.dc.fd.rest.controllers;

import static java.util.Map.entry;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.services.PostService;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;
import es.udc.fi.dc.fd.rest.dtos.CouponConversor;
import es.udc.fi.dc.fd.rest.dtos.OfferConversor;
import es.udc.fi.dc.fd.rest.dtos.PostConversor;
import es.udc.fi.dc.fd.rest.dtos.PostDto;
import es.udc.fi.dc.fd.rest.dtos.PostExpiredDto;
import es.udc.fi.dc.fd.rest.dtos.PostParamsDto;
import es.udc.fi.dc.fd.rest.dtos.PostUpdateDto;
import es.udc.fi.dc.fd.rest.dtos.UserDto;

/**
 * The Class PostController.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

	/** The post conversor. */
	private final Map<String, PostConversor> conversors;

	/** The post service. */
	@Autowired
	private PostService postService;

	/**
	 * The post controller.
	 * 
	 * @param offerConversor  the offer conversor
	 * @param couponConversor the coupon conversor
	 */
	@Autowired
	public PostController(OfferConversor offerConversor, CouponConversor couponConversor) {
		this.conversors = Map.ofEntries(entry("Offer", offerConversor), entry("Coupon", couponConversor));
	}

	/**
	 * @param userId the user id
	 * @param params the params
	 * @return the post dto
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws MaximumImageSizeExceededException the maximum image size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 */
	@PostMapping("/post")
	public PostDto createPost(@RequestAttribute Long userId, @Validated @RequestBody PostParamsDto params)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException {

		PostConversor postConversor = conversors.get(params.getType());

		Post post = postService.createPost(params.getTitle(), params.getDescription(), params.getUrl(),
				params.getPrice(), userId, params.getCategoryId(), params.getImages(), params.getType(),
				params.getProperties());

		return postConversor.toPostDto(post);

	}

	/**
	 * @param userId the user id
	 * @param id     the post id
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws PermissionException       the permission exception
	 */
	@DeleteMapping("/post/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletePost(@RequestAttribute Long userId, @PathVariable Long id)
			throws InstanceNotFoundException, PermissionException {

		postService.deletePost(userId, id);

	}

	/**
	 * @param userId         the user id
	 * @param id             the id
	 * @param postExpiredDto the post expired dto
	 * @return the post expired dto
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws PermissionException       the permission exception
	 */
	@PostMapping("/post/{id}/markAsExpired")
	public PostExpiredDto markPostAsExpired(@RequestAttribute Long userId, @PathVariable Long id,
			@RequestBody PostExpiredDto postExpiredDto) throws InstanceNotFoundException, PermissionException {

		return new PostExpiredDto(postService.markAsExpired(userId, id, postExpiredDto.isExpired()));

	}

	/**
	 * @param userId the user id
	 * @param postId the post id
	 * @param params the params
	 * @return the post dto
	 * @throws InstanceNotFoundException         the instance not found exception
	 * @throws PermissionException               the permission exception
	 * @throws MaximumImageSizeExceededException the maximum image size exceeded
	 *                                           exception
	 * @throws MissingRequiredParameterException the missing required parameter
	 *                                           exception
	 */
	@PutMapping("/post/{postId}")
	public PostDto updatePost(@RequestAttribute Long userId, @PathVariable Long postId,
			@Validated({ UserDto.UpdateValidations.class }) @RequestBody PostUpdateDto params)
			throws InstanceNotFoundException, PermissionException, MaximumImageSizeExceededException,
			MissingRequiredParameterException {

		if (!(params.getAuthorId()).equals(userId)) {
			throw new PermissionException();
		}

		PostConversor postConversor = conversors.get(params.getType());

		Post post = postService.updatePost(postId, params.getTitle(), params.getDescription(), params.getUrl(),
				params.getPrice(), userId, params.getCategoryId(), params.getImages(), params.getType(),
				params.getProperties());

		return postConversor.toPostDto(post);

	}

}