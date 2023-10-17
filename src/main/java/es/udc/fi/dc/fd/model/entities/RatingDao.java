package es.udc.fi.dc.fd.model.entities;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface RatingDao.
 */
public interface RatingDao extends JpaRepository<Rating, Long> {
	Optional<Rating> findRatingByUserIdAndPostId(Long userId, Long postId);
}
