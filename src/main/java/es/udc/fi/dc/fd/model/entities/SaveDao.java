package es.udc.fi.dc.fd.model.entities;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface SaveDao.
 */
public interface SaveDao extends JpaRepository<Save, Long> {
	List<Save> findSaveByPostId(Long postId);

	boolean existsSaveByPostIdAndUserId(Long postId, Long userId);

	Optional<Save> findSaveByPostIdAndUserId(Long postId, Long userId);
}
