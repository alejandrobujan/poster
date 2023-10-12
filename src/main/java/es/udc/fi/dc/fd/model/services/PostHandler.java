package es.udc.fi.dc.fd.model.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.services.exceptions.MaximumImageSizeExceededException;
import es.udc.fi.dc.fd.model.services.exceptions.MissingRequiredParameterException;

/**
 * 
 */
public interface PostHandler {
	Post handleCreate(String title, String description, String url, BigDecimal price, Long userId, Long categoryId,
			List<byte[]> imageList, Map<String, String> properties)
			throws InstanceNotFoundException, MaximumImageSizeExceededException, MissingRequiredParameterException;

}