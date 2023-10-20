package es.udc.fi.dc.fd.rest.controllers;

import static java.util.Map.entry;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
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

	/** The Constant INCORRECT_FORM_VALUES_EXCEPTION_CODE. */
	private static final String INCORRECT_FORM_VALUES_EXCEPTION_CODE = "project.exceptions.IncorrectFormValuesException";

	private final Map<String, PostConversor> conversors;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private PostService postService;

	@Autowired
	public PostController(OfferConversor offerConversor, CouponConversor couponConversor) {
		this.conversors = Map.ofEntries(entry("Offer", offerConversor), entry("Coupon", couponConversor));
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

	@PostMapping("/post")
	public PostDto createPost(@RequestAttribute Long userId, @Validated @RequestBody PostParamsDto params)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException,
			IncorrectFormValuesException {

		PostConversor postConversor = conversors.get(params.getType());

		Post post = postService.createPost(params.getTitle(), params.getDescription(), params.getUrl(),
				params.getPrice(), userId, params.getCategoryId(), params.getImages(), params.getType(),
				params.getProperties());

		return postConversor.toPostDto(post);

	}

	@DeleteMapping("/post/{id}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletePost(@RequestAttribute Long userId, @PathVariable Long id)
			throws InstanceNotFoundException, PermissionException {

		postService.deletePost(userId, id);

	}

	@PostMapping("/post/{id}/markAsExpired")
	public PostExpiredDto markPostAsExpired(@RequestAttribute Long userId, @PathVariable Long id,
			@RequestBody PostExpiredDto postExpiredDto) throws InstanceNotFoundException, PermissionException {

		return new PostExpiredDto(postService.markAsExpired(userId, id, postExpiredDto.isExpired()));

	}

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
				params.getProperties());

		return postConversor.toPostDto(post);

	}

}