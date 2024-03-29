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
import es.udc.fi.dc.fd.model.services.Block;
import es.udc.fi.dc.fd.model.services.CommentService;
import es.udc.fi.dc.fd.model.services.exceptions.InvalidCommentParameterException;
import es.udc.fi.dc.fd.rest.common.ErrorsDto;
import es.udc.fi.dc.fd.rest.dtos.BlockDto;
import es.udc.fi.dc.fd.rest.dtos.CommentConversor;
import es.udc.fi.dc.fd.rest.dtos.CommentDto;
import es.udc.fi.dc.fd.rest.dtos.CommentParamsDto;
import es.udc.fi.dc.fd.rest.dtos.FindCommentsParamsDto;

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
	 * Handle invalid comment parameter exception
	 * 
	 * @param exception the exception
	 * @param locale    the locale
	 * @return an errors dto
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

	/**
	 * Create a comment
	 * 
	 * @param userId the user id
	 * @param id     the comment id
	 * @param params the params
	 * @throws InstanceNotFoundException        the instance not found exception
	 * @throws InvalidCommentParameterException the invalid comment parameter
	 *                                          exception
	 */
	@PostMapping("/post/{id}/comment")
	public void createComment(@RequestAttribute Long userId, @PathVariable Long id,
			@Validated @RequestBody CommentParamsDto params)
			throws InstanceNotFoundException, InvalidCommentParameterException {

		if (params.getCommentParentId() == null) {

			commentService.createComment(params.getDescription(), userId, id);
		} else
			commentService.answerComment(params.getDescription(), userId, params.getCommentParentId());

	}

	/**
	 * Find the comments
	 * 
	 * @param id     the comment id
	 * @param params the params
	 * @return a block dto of comment dto
	 */
	@PostMapping("/post/{id}")
	public BlockDto<CommentDto> findComments(@PathVariable Long id, @RequestBody FindCommentsParamsDto params) {

		Block<Comment> postComments = commentService.findComments(id, params.getPage(), params.getParentId(), 6);

		return new BlockDto<>(CommentConversor.toCommentDtos(postComments.getItems()),
				postComments.getExistMoreItems());

	}

}
