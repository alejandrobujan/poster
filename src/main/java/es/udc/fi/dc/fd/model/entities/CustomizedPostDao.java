package es.udc.fi.dc.fd.model.entities;

import org.springframework.data.domain.Slice;

/**
 * The Interface CustomizedPostDao.
 */
public interface CustomizedPostDao {

	/**
	 * Find customized post
	 * 
	 * @param filters  the filters for the post
	 * @param keywords the keywords for search the post
	 * @param page     the page
	 * @param size     the size
	 * @return slice of posts
	 */
	Slice<Post> find(SearchFilters filters, String keywords, int page, int size);

}