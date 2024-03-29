package es.udc.fi.dc.fd.model.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Comment;
import es.udc.fi.dc.fd.model.entities.CommentDao;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.InvalidCommentParameterException;

/**
 * The Class CommentServiceImpl.
 */

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

	/** The comment dao. */
	@Autowired
	private CommentDao commentDao;

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The permission checker. */
	@Autowired
	private PermissionChecker permissionChecker;

	/** The notification service. */
	@Autowired
	private NotificationService notificationService;

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
	@Override
	public Comment createComment(String description, Long userId, Long postId)
			throws InstanceNotFoundException, InvalidCommentParameterException {
		User user = permissionChecker.checkUser(userId);

		Post post = null;
		if (postId != null) {
			Optional<Post> postOptional = postDao.findById(postId);

			if (!postOptional.isPresent())
				throw new InstanceNotFoundException("project.entities.post", postId);

			post = postOptional.get();
		} else {
			throw new InvalidCommentParameterException("project.entities.post", postId);
		}

		Comment comment = new Comment(description, LocalDateTime.now(), user, post, null, 1, 0);

		commentDao.save(comment);

		String notificationText = user.getUserName() + " commented on your post: " + comment.getDescription();

		notificationService.notify(
				notificationText.length() < 128 ? notificationText : notificationText.substring(0, 124) + "...", user,
				post.getUser(), post, comment);

		return comment;
	}

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
	@Override
	public Comment answerComment(String description, Long userId, Long parentId)
			throws InstanceNotFoundException, InvalidCommentParameterException {
		User user = permissionChecker.checkUser(userId);

		Comment parent = null;
		if (parentId != null) {
			Optional<Comment> commentOptional = commentDao.findById(parentId);

			if (!commentOptional.isPresent())
				throw new InstanceNotFoundException("project.entities.comment", parentId);

			parent = commentOptional.get();
		} else {
			throw new InvalidCommentParameterException("project.entities.comment", parentId);
		}

		parent.setAnswers(parent.getAnswers() + 1);

		Comment comment = new Comment(description, LocalDateTime.now(), user, parent.getPost(), parent,
				parent.getLevel() + 1, 0);

		commentDao.save(comment);
		commentDao.save(parent);

		String parentNotificationText = user.getUserName() + " answered your comment: " + comment.getDescription();
		String postNotificationText = user.getUserName() + " commented on your post: " + comment.getDescription();

		if (!comment.getPost().getUser().equals(parent.getUser())) {
			notificationService.notify(
					postNotificationText.length() < 128 ? postNotificationText
							: postNotificationText.substring(0, 124) + "...",
					user, parent.getPost().getUser(), parent.getPost(), comment);
		}

		notificationService.notify(
				parentNotificationText.length() < 128 ? parentNotificationText
						: parentNotificationText.substring(0, 124) + "...",
				user, parent.getUser(), parent.getPost(), comment);

		return comment;
	}

	/**
	 * Find comments
	 * 
	 * @param postId the post id
	 * @param page   the page
	 * @param size   the size
	 * @return a block of comments
	 */
	@Override
	public Block<Comment> findComments(Long postId, int page, Long parentId, int size) {
		Slice<Comment> slice = commentDao.findByPostIdAndCommentIdOrderByDateDesc(postId, parentId,
				PageRequest.of(page, size));

		return new Block<>(slice.getContent(), slice.hasNext());
	}

}
