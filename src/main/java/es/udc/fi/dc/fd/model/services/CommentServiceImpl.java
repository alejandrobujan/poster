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

	/**
	 * @param description
	 * @param userId
	 * @param postId
	 * @return
	 * @throws InstanceNotFoundException
	 * @throws InvalidCommentParameterException
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

		return comment;
	}

	/**
	 * @param description
	 * @param userId
	 * @param parentId
	 * @return
	 * @throws InstanceNotFoundException
	 * @throws InvalidCommentParameterException
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

		parent.setAnswers(parent.getAnswers()+1);

		Comment comment = new Comment(description, LocalDateTime.now(), user, parent.getPost(), parent, parent.getLevel()+1, 0);

		commentDao.save(comment);
		commentDao.save(parent);

		return comment;
	}

	@Override
	public Block<Comment> findComments(Long postId, int page, Long parentId, int size) {
		Slice<Comment> slice = commentDao.findByPostIdAndCommentIdOrderByDateDesc(postId, parentId, PageRequest.of(page, size));

		return new Block<>(slice.getContent(), slice.hasNext());
	}

	@Override
	public Block<Comment> findCommentResponses(Long commentId, int page, int size) {
		Slice<Comment> slice = commentDao.findByCommentIdOrderByDateDesc(commentId, PageRequest.of(page, size));

		return new Block<>(slice.getContent(), slice.hasNext());
	}

}
