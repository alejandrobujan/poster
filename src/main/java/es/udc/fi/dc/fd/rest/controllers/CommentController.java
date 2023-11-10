package es.udc.fi.dc.fd.rest.controllers;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.services.CommentService;
import es.udc.fi.dc.fd.model.services.exceptions.InvalidCommentParameterException;
import es.udc.fi.dc.fd.rest.common.ErrorsDto;
import es.udc.fi.dc.fd.rest.dtos.CommentConversor;
import es.udc.fi.dc.fd.rest.dtos.CommentDto;
import es.udc.fi.dc.fd.rest.dtos.CommentParamsDto;

/**
 * The Class CommentController.
 */

@RestController
@RequestMapping("/api/comments")
public class CommentController {

	/** The Constant INCORRECT_FORM_VALUES_EXCEPTION_CODE. */
	private static final String INVALID_COMMENT_PARAMETER_EXCEPTION_CODE = "project.exceptions.InvalidCommentParameterException";

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/** The comment service */
	@Autowired
	private CommentService commentService;

	/**
	 * @param exception
	 * @param locale
	 * @return
	 */
	@ExceptionHandler(InvalidCommentParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorsDto handleInvalidCommentParameterException(InvalidCommentParameterException exception, Locale locale) {

		String nameMessage = messageSource.getMessage(exception.getName(), null, exception.getName(), locale);
		String errorMessage = messageSource.getMessage(INVALID_COMMENT_PARAMETER_EXCEPTION_CODE,
				new Object[] { nameMessage, exception.getKey().toString() }, INVALID_COMMENT_PARAMETER_EXCEPTION_CODE,
				locale);

		return new ErrorsDto(errorMessage);

	}

	@PostMapping("/post/{id}/comment")
	public CommentDto createComment(@RequestAttribute Long userId, @PathVariable Long id,
			@Validated @RequestBody CommentParamsDto params)
			throws InstanceNotFoundException, InvalidCommentParameterException {

		Comment comment = null;

		if (params.getCommentParentId() == null) {

			comment = commentService.createComment(params.getDescription(), userId, id);
		} else
			comment = commentService.answerComment(params.getDescription(), userId, params.getCommentParentId());

		return CommentConversor.toCommentDto(comment);

	}

}