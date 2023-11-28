package es.udc.fi.dc.fd.rest.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
import es.udc.fi.dc.fd.rest.common.ErrorsDto;

@RestController
@RequestMapping("/api/saves")
public class SaveController {

	/** The Constant ALREADY_SAVED_EXCEPTION_CODE. */
	private static final String ALREADY_SAVED_EXCEPTION_CODE = "project.exceptions.AlreadySavedException";

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

	@PostMapping("/post/{postId}")
	public void savePost(@RequestAttribute Long userId, @PathVariable Long postId)
			throws InstanceNotFoundException, AlreadySavedException {
		saveService.savePost(postId, userId);
	}

	@PostMapping("/post/{postId}/save")
	public boolean isPostSavedByUser(@RequestAttribute Long userId, @PathVariable Long postId) {
		return saveService.isPostSavedByUser(postId, userId);
	}

}
