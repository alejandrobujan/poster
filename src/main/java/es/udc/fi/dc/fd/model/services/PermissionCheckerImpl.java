package es.udc.fi.dc.fd.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.entities.UserDao;
import es.udc.fi.dc.fd.model.services.exceptions.PermissionException;

/**
 * The Class PermissionCheckerImpl.
 */
@Service
@Transactional(readOnly = true)
public class PermissionCheckerImpl implements PermissionChecker {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PostDao postDao;

	@Override
	public void checkUserExists(Long userId) throws InstanceNotFoundException {

		if (!userDao.existsById(userId)) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}

	}

	@Override
	public User checkUser(Long userId) throws InstanceNotFoundException {

		Optional<User> user = userDao.findById(userId);

		if (!user.isPresent()) {
			throw new InstanceNotFoundException("project.entities.user", userId);
		}

		return user.get();

	}

	@Override
	public Post checkPostExistsAndBelongsTo(Long postId, Long userId)
			throws PermissionException, InstanceNotFoundException {
		Optional<Post> post = postDao.findById(postId);

		if (!post.isPresent()) {
			throw new InstanceNotFoundException("project.entities.post", postId);
		}

		if (!post.get().getUser().getId().equals(userId)) {
			throw new PermissionException();
		}

		return post.get();
	}

}