package es.udc.fi.dc.fd.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface CommentDao.
 */
public interface CommentDao extends JpaRepository<Comment, Long> {
	/**
	 * Find comment by post id and comment id order by date descending.
	 * 
	 * @param postId    the post id
	 * @param commentId the comment id
	 * @param pageable  the page
	 * @return a slice of comment
	 */
	Slice<Comment> findByPostIdAndCommentIdOrderByDateDesc(Long postId, Long commentId, Pageable pageable);
}
