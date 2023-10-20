package es.udc.fi.dc.fd.model.entities;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface RatingDao.
 */
public interface RatingDao extends JpaRepository<Rating, Long> {

	/**
	 * Find rating by user id and post id.
	 * 
	 * @param userId the user id
	 * @param postId the post id
	 * @return the rating
	 */
	Optional<Rating> findRatingByUserIdAndPostId(Long userId, Long postId);
}
