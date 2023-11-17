package es.udc.fi.dc.fd.model.entities;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationDao extends JpaRepository<Notification, Long> {

	/**
	 * Find unviewed notifications by user id.
	 * 
	 * @param userId the user id
	 * @return the rating
	 */
	List<Notification> findByNotifiedUserIdAndViewedFalseOrderByCreationDateDesc(Long userId);

}
