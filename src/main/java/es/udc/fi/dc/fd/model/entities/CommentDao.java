package es.udc.fi.dc.fd.model.entities;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface CommentDao.
 */
public interface CommentDao extends JpaRepository<Comment, Long> {
	/**
	 * @param postId
	 * @param pageable
	 * @return
	 */
	Slice<Comment> findByPostIdAndCommentIdOrderByDateDesc(Long postId, Long commentId, Pageable pageable);

	/**
	 * @param commentId
	 * @param pageable
	 * @return
	 */
	Slice<Comment> findByCommentIdOrderByDateDesc(Long commentId, Pageable pageable);
}
