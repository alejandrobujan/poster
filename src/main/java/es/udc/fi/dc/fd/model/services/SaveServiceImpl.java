package es.udc.fi.dc.fd.model.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.Save;
import es.udc.fi.dc.fd.model.entities.SaveDao;
import es.udc.fi.dc.fd.model.entities.User;
import es.udc.fi.dc.fd.model.services.exceptions.AlreadySavedException;

@Service
@Transactional
public class SaveServiceImpl implements SaveService {

	/** The save dao. */
	@Autowired
	private SaveDao saveDao;

	/** The permission checker. */
	@Autowired
	private PermissionChecker permissionChecker;

	@Override
	public boolean isPostSavedByUser(Long postId, Long userId) {
		return saveDao.existsSaveByPostIdAndUserId(postId, userId);
	}

	@Override
	public void savePost(Long postId, Long userId) throws InstanceNotFoundException, AlreadySavedException {
		Post post = permissionChecker.checkPost(postId);
		User user = permissionChecker.checkUser(userId);
		if (isPostSavedByUser(postId, userId))
			throw new AlreadySavedException();
		saveDao.save(new Save(post, user));
	}
}
