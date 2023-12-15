package es.udc.fi.dc.fd.model.services;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.services.exceptions.InvalidCommentParameterException;

/**
 * The Interface CommentService.
 */
public interface CommentService {

	/**
	 * Create a comment.
	 * 
	 * @param description the description
	 * @param userId      the user id
	 * @param postId      the post id
	 * @return the comment
	 * @throws InstanceNotFoundException        the instance not found exception
	 * @throws InvalidCommentParameterException the invalid comment parameter
	 *                                          exception
	 */
	Comment createComment(String description, Long userId, Long postId)
			throws InstanceNotFoundException, InvalidCommentParameterException;

	/**
	 * Answer a comment
	 * 
	 * @param description the description
	 * @param userId      the user id
	 * @param parentId    the parent id
	 * @return the comment
	 * @throws InstanceNotFoundException        the instance not found exception
	 * @throws InvalidCommentParameterException the invalid comment parameter
	 *                                          exception
	 */
	Comment answerComment(String description, Long userId, Long parentId)
			throws InstanceNotFoundException, InvalidCommentParameterException;

	/**
	 * Find comments
	 * 
	 * @param postId the post id
	 * @param page   the page
	 * @param size   the size
	 * @return a block of comments
	 */
	Block<Comment> findComments(Long postId, int page, Long parentId, int size);

}
