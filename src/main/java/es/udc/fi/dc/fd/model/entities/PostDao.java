package es.udc.fi.dc.fd.model.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

/**
 * The Interface PostDao.
 */
public interface PostDao extends JpaRepository<Post, Long> {
	Slice<Post> findAllByOrderByCreationDateDesc(Pageable pageable);
}
