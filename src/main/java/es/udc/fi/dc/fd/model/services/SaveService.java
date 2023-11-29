package es.udc.fi.dc.fd.model.services;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.services.exceptions.AlreadySavedException;
import es.udc.fi.dc.fd.model.services.exceptions.SavePostUserCreatorException;

public interface SaveService {

	void savePost(Long postId, Long userId)
			throws InstanceNotFoundException, AlreadySavedException, SavePostUserCreatorException;

	boolean isPostSavedByUser(Long postId, Long userId);

}
