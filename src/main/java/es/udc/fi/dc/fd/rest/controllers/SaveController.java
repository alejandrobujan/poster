package es.udc.fi.dc.fd.rest.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.services.SaveService;
import es.udc.fi.dc.fd.model.services.exceptions.AlreadySavedException;
import es.udc.fi.dc.fd.model.services.exceptions.SavePostUserCreatorException;
import es.udc.fi.dc.fd.rest.common.ErrorsDto;

@RestController
@RequestMapping("/api/saves")
public class SaveController {

	/** The Constant ALREADY_SAVED_EXCEPTION_CODE. */
	private static final String ALREADY_SAVED_EXCEPTION_CODE = "project.exceptions.AlreadySavedException";

	/** The Constant SAVE_POST_USER_CREATOR_EXCEPTION_CODE. */
	private static final String SAVE_POST_USER_CREATOR_EXCEPTION_CODE = "project.exceptions.SavePostUserCreatorException";

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/** The post service */
	@Autowired
	private SaveService saveService;

	@ExceptionHandler(AlreadySavedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorsDto handleAlreadySavedException(AlreadySavedException exception, Locale locale) {

		String errorMessage = messageSource.getMessage(ALREADY_SAVED_EXCEPTION_CODE, null, ALREADY_SAVED_EXCEPTION_CODE,
				locale);

		return new ErrorsDto(errorMessage);

	}

	@ExceptionHandler(SavePostUserCreatorException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ResponseBody
	public ErrorsDto handleSavePostUserCreatorException(SavePostUserCreatorException exception, Locale locale) {

		String errorMessage = messageSource.getMessage(SAVE_POST_USER_CREATOR_EXCEPTION_CODE, null,
				SAVE_POST_USER_CREATOR_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);

	}

	@PostMapping("/post/{postId}")
	public void savePost(@RequestAttribute Long userId, @PathVariable Long postId)
			throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException {
		saveService.savePost(postId, userId);
	}

	@GetMapping("/post/{postId}/save")
	public boolean isPostSavedByUser(@RequestAttribute Long userId, @PathVariable Long postId) {
		return saveService.isPostSavedByUser(postId, userId);
	}

}
