package es.udc.fi.dc.fd.model.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Category;
import es.udc.fi.dc.fd.model.entities.CategoryDao;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;
import es.udc.fi.dc.fd.model.entities.SearchFilters;

@Service
@Transactional(readOnly = true)
public class CatalogServiceImpl implements CatalogService {

	/** The post dao. */
	@Autowired
	private PostDao postDao;

	/** The category dao. */
	@Autowired
	private CategoryDao categoryDao;

	@Override
	public Block<Post> findPosts(SearchFilters filters, String keywords, int page, int size) {
		Slice<Post> slice = postDao.find(filters, keywords, page, size);

		return new Block<>(slice.getContent(), slice.hasNext());
	}

	/**
	 * Find all categories.
	 * 
	 * @return the categories.
	 */
	@Override
	public List<Category> findAllCategories() {

		Iterable<Category> categories = categoryDao.findAll(Sort.by(Sort.Direction.ASC, "id"));
		List<Category> categoriesAsList = new ArrayList<>();

		categories.forEach(c -> categoriesAsList.add(c));

		return categoriesAsList;
	}

	@Override
	public Post findPostById(Long postId) throws InstanceNotFoundException {
		Optional<Post> post = postDao.findById(postId);

		if (!post.isPresent()) {
			throw new InstanceNotFoundException("project.entities.post", postId);
		}

		return post.get();
	}

}
