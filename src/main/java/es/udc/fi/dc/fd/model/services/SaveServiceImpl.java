package es.udc.fi.dc.fd.model.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.Save;
import es.udc.fi.dc.fd.model.entities.SaveDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.AlreadySavedException;
import es.udc.fi.dc.fd.model.services.exceptions.AlreadyUnsavedException;
import es.udc.fi.dc.fd.model.services.exceptions.SavePostUserCreatorException;

@Service
@Transactional
public class SaveServiceImpl implements SaveService {

	/** The save dao. */
	@Autowired
	private SaveDao saveDao;

	/** The permission checker. */
	@Autowired
	private PermissionChecker permissionChecker;

	/**
	 * Returns if the post saved by user
	 * 
	 * @param postId the post id
	 * @param userId the user id
	 * @return if the post is saved by the user
	 */
	@Override
	public boolean isPostSavedByUser(Long postId, Long userId) {
		return saveDao.existsSaveByPostIdAndUserId(postId, userId);
	}

	/**
	 * Save a post
	 * 
	 * @param postId the post id
	 * @param userId the user id
	 * @throws InstanceNotFoundException    the instance not found exception
	 * @throws AlreadySavedException        the already saved exception
	 * @throws SavePostUserCreatorException the save post user creator exception
	 */
	@Override
	public void savePost(Long postId, Long userId)
			throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException {
		Post post = permissionChecker.checkPost(postId);
		User user = permissionChecker.checkUser(userId);
		if (isPostSavedByUser(postId, userId))
			throw new AlreadySavedException();
		if (post.getUser().getId().equals(user.getId()))
			throw new SavePostUserCreatorException();
		saveDao.save(new Save(post, user));
	}

	/**
	 * Unsave a post
	 * 
	 * @param postId the post id
	 * @param userId the user id
	 * @throws InstanceNotFoundException the instance not found exception
	 * @throws AlreadyUnsavedException   the already unsaved exception
	 */
	@Override
	public void unSavePost(Long postId, Long userId) throws InstanceNotFoundException, AlreadyUnsavedException {
		permissionChecker.checkPost(postId);
		permissionChecker.checkUser(userId);
		Optional<Save> save = saveDao.findSaveByPostIdAndUserId(postId, userId);
		if (save.isPresent()) {
			saveDao.delete(save.get());
		} else {
			throw new AlreadyUnsavedException();
		}
	}

}
