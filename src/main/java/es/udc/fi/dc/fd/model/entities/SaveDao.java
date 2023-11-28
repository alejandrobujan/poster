package es.udc.fi.dc.fd.model.entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface SaveDao.
 */
public interface SaveDao extends JpaRepository<Save, Long> {
	List<Save> findSaveByPostId(Long postId);
}
