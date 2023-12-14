package es.udc.fi.dc.fd.model.entities;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface SaveDao.
 */
public interface SaveDao extends JpaRepository<Save, Long> {

	/**
	 * Find save by post id.
	 * 
	 * @param postId the post id
	 * @return a list of save
	 */
	List<Save> findSaveByPostId(Long postId);

	/**
	 * Return if the save exists by post id and user id.
	 * 
	 * @param postId
	 * @param userId
	 * @return if the save exists or not
	 */
	boolean existsSaveByPostIdAndUserId(Long postId, Long userId);

	/**
	 * Find save by post id and user id.
	 * 
	 * @param postId
	 * @param userId
	 * @return optional save
	 */
	Optional<Save> findSaveByPostIdAndUserId(Long postId, Long userId);
}
