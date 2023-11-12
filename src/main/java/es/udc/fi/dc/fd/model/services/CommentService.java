package es.udc.fi.dc.fd.model.services;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.services.exceptions.InvalidCommentParameterException;

/**
 * The Interface CommentService.
 */
public interface CommentService {

	/**
	 * @param description
	 * @param userId
	 * @param postId
	 * @return
	 * @throws InstanceNotFoundException
	 * @throws InvalidCommentParameterException
	 */
	Comment createComment(String description, Long userId, Long postId)
			throws InstanceNotFoundException, InvalidCommentParameterException;

	/**
	 * @param description
	 * @param userId
	 * @param parentId
	 * @return
	 * @throws InstanceNotFoundException
	 * @throws InvalidCommentParameterException
	 */
	Comment answerComment(String description, Long userId, Long parentId)
			throws InstanceNotFoundException, InvalidCommentParameterException;

	/**
	 * @param postId
	 * @param page
	 * @param size
	 * @return
	 */
	Block<Comment> findComments(Long postId, int page, Long parentId, int size);

	/**
	 * @param postId
	 * @param commentId
	 * @param page
	 * @param size
	 * @return
	 */
	Block<Comment> findCommentResponses(Long commentId, int page, int size);

}
