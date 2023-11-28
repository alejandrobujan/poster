package es.udc.fi.dc.fd.model.services;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.services.exceptions.AlreadySavedException;

public interface SaveService {

	void savePost(Long postId, Long userId) throws InstanceNotFoundException, AlreadySavedException;

	boolean isPostSavedByUser(Long postId, Long userId);

}
