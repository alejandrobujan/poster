package es.udc.fi.dc.fd.model.entities;

import org.springframework.data.domain.Slice;

public interface CustomizedPostDao {

	Slice<Post> find(SearchFilters filters, String keywords, int page, int size);

}