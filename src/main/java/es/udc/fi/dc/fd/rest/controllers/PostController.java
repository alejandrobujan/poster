package es.udc.fi.dc.fd.rest.controllers;

import static java.util.Map.entry;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.services.PostService;
import es.udc.fi.dc.fd.model.services.exceptions.IncorrectFormValuesException;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;
import es.udc.fi.dc.fd.rest.common.ErrorsDto;
import es.udc.fi.dc.fd.rest.dtos.CouponConversor;
import es.udc.fi.dc.fd.rest.dtos.OfferConversor;
import es.udc.fi.dc.fd.rest.dtos.PostConversor;
import es.udc.fi.dc.fd.rest.dtos.PostDto;
import es.udc.fi.dc.fd.rest.dtos.PostParamsDto;
import es.udc.fi.dc.fd.rest.dtos.PostStreamDto;
import es.udc.fi.dc.fd.rest.dtos.PostUpdateDto;
import es.udc.fi.dc.fd.rest.dtos.PostValidDto;
import es.udc.fi.dc.fd.rest.dtos.UserDto;

/**
 * The Class PostController.
 */
@RestController
@RequestMapping("/api/posts")
public class PostController {

	/** The Constant INCORRECT_FORM_VALUES_EXCEPTION_CODE. */
	private static final String INCORRECT_FORM_VALUES_EXCEPTION_CODE = "project.exceptions.IncorrectFormValuesException";

	/** The post conversor. */
	private final Map<String, PostConversor> conversors;

	private final SimpMessagingTemplate messagingTemplate;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/** The post service */
	@Autowired
	private PostService postService;

	/**
	 * The post controller.
	 * 
	 * @param offerConversor  the offer conversor
	 * @param couponConversor the coupon conversor
	 */
	@Autowired
	public PostController(OfferConversor offerConversor, CouponConversor couponConversor, SimpMessagingTemplate messagingTemplate) {
		this.conversors = Map.ofEntries(entry("Offer", offerConversor), entry("Coupon", couponConversor));
		this.messagingTemplate = messagingTemplate;
	}

	/**
	 * Handle incorrect form values exception.
	 * 
	 * @param exception the exception
	 * @param locale    the locale
	 * @return the errors dto
	 */
	@ExceptionHandler(IncorrectFormValuesException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorsDto handleIncorrectFormValuesException(IncorrectFormValuesException exception, Locale locale) {

		String errorMessage = messageSource.getMessage(INCORRECT_FORM_VALUES_EXCEPTION_CODE, null,
				INCORRECT_FORM_VALUES_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);

	}

	/**
	 * The create post
	 * 
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
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException,
			IncorrectFormValuesException {

		PostConversor postConversor = conversors.get(params.getType());

		Post post = postService.createPost(params.getTitle(), params.getDescription(), params.getUrl(),
				params.getPrice(), userId, params.getCategoryId(), params.getImages(), params.getType(),
				params.getProperties(), PostConversor.fromMillis(params.getExpirationDate()));

		messagingTemplate.convertAndSend("/topic/posts", new PostStreamDto("posts.newPost"));

		return postConversor.toPostDto(post);

	}

	/**
	 * The delete post
	 * 
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
	 * Mark post as expired
	 * 
	 * @param userId         the user id
	 * @param id             the id
	 * @param postExpiredDto the post expired dto
	 * @return the post expired dto
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws PermissionException       the permission exception
	 */
	@PostMapping("/post/{id}/markAsExpired")
	public Long markPostAsExpired(@RequestAttribute Long userId, @PathVariable Long id)
			throws InstanceNotFoundException, PermissionException {

		return PostConversor.toMillis(postService.markAsExpired(userId, id));

	}

	/**
	 * The update post
	 * 
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
			MissingRequiredParameterException, IncorrectFormValuesException {

		if (!(params.getAuthorId()).equals(userId)) {
			throw new PermissionException();
		}

		PostConversor postConversor = conversors.get(params.getType());

		Post post = postService.updatePost(postId, params.getTitle(), params.getDescription(), params.getUrl(),
				params.getPrice(), userId, params.getCategoryId(), params.getImages(), params.getType(),
				params.getProperties(), PostConversor.fromMillis(params.getExpirationDate()));

		return postConversor.toPostDto(post);

	}

	/**
	 * Mark post as valid
	 * 
	 * @param id the id
	 * @return the post valid dto
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@PostMapping("/post/{id}/markAsValid")
	public PostValidDto markPostAsValid(@RequestAttribute Long userId, @PathVariable Long id)
			throws InstanceNotFoundException {
		return new PostValidDto(postService.markAsValid(userId, id));

	}

}