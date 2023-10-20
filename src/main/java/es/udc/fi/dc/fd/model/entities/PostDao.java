package es.udc.fi.dc.fd.model.entities;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The Interface PostDao.
 */
public interface PostDao extends JpaRepository<Post, Long>, CustomizedPostDao {
}