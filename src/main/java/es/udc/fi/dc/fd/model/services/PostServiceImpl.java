/**
 * 
 */
package es.udc.fi.dc.fd.model.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.entities.PostDao;

/**
 * The Class PostServiceImpl.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostDao postDao;

	/**
	 * Create post.
	 * 
	 * @param post
	 */
	@Override
	public void createPost(Post post) {
		postDao.save(post);

	}

	/**
	 * Find all posts.
	 * 
	 * @param page
	 * @param size
	 * @return the posts.
	 */
	@Override
	public Block<Post> findAllPosts(int page, int size) {
		Pageable request = PageRequest.of(page, size);

        Slice<Post> postSlice = postDao.findAllByOrderByCreationDateDesc(request);

        return new Block<>(postSlice.getContent(), postSlice.hasNext());
	}

}
