package es.udc.fi.dc.fd.model.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

public class CustomizedPostDaoImpl implements CustomizedPostDao {

	@PersistenceContext
	private EntityManager entityManager;

	private String[] getTokens(String keywords) {

		if (keywords == null || keywords.length() == 0) {
			return new String[0];
		} else {
			return keywords.split("\\s");
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public Slice<Post> find(SearchFilters filters, String keywords, int page, int size) {

		LocalDateTime ago = null;

		StringBuilder queryBuilder = new StringBuilder();

		String[] tokens = getTokens(keywords);
		queryBuilder.append("SELECT p FROM Post p");

		Map<String, String> typedQueries = Map.ofEntries(Map.entry("Offer", " JOIN Offer o ON p.id = o.id"),
				Map.entry("Coupon", " JOIN Coupon c ON p.id = c.id"));

		if (filters.getType() != null) {
			queryBuilder.append(typedQueries.get(filters.getType()));
		}

		if (tokens.length != 0 || filters.getCategoryId() != null || filters.getType() != null
				|| filters.getPrice() != null || filters.getDate() != null || !filters.isExpired())
			queryBuilder.append(" WHERE ");

		List<String> parameters = new ArrayList<>();

		if (filters.getCategoryId() != null)
			parameters.add("p.category.id = :categoryId");

		if (!filters.isExpired())
			parameters.add("p.expired = :expired");

		if (filters.getPrice() != null && filters.getPrice().get("gte") != null)
			parameters.add("p.price >= :gte");

		if (filters.getPrice() != null && filters.getPrice().get("lte") != null)
			parameters.add("p.price <= :lte");

		if (filters.getDate() != null) {
			parameters.add("p.creationDate >= :ago");
			switch (filters.getDate()) {
			case "hour":
				ago = LocalDateTime.now().minusHours(1);
				break;
			case "day":
				ago = LocalDateTime.now().minusDays(1);
				break;
			case "week":
				ago = LocalDateTime.now().minusWeeks(1);
				break;
			case "month":
				ago = LocalDateTime.now().minusMonths(1);
				break;
			case "year":
				ago = LocalDateTime.now().minusYears(1);
				break;
			}
		}

		for (String param : parameters) {
			queryBuilder.append(param + " AND ");
		}

		String queryString = queryBuilder.toString();

		if (tokens.length != 0) {

			for (int i = 0; i < tokens.length - 1; i++) {
				queryString += "LOWER(p.title) LIKE LOWER(:token" + i + ") AND ";
			}

			queryString += "LOWER(p.title) LIKE LOWER(:token" + (tokens.length - 1) + ")";
		} else {
			queryString = queryString.replaceAll(" AND $", "");
		}

		queryString += " ORDER BY p.creationDate DESC";

		Query query = entityManager.createQuery(queryString).setFirstResult(page * size).setMaxResults(size + 1);

		if (filters.getCategoryId() != null)
			query.setParameter("categoryId", filters.getCategoryId());

		if (!filters.isExpired())
			query.setParameter("expired", filters.isExpired());

		if (filters.getPrice() != null && filters.getPrice().get("gte") != null)
			query.setParameter("gte", filters.getPrice().get("gte"));

		if (filters.getPrice() != null && filters.getPrice().get("lte") != null)
			query.setParameter("lte", filters.getPrice().get("lte"));

		if (filters.getDate() != null) {
			query.setParameter("ago", ago);
		}

		if (tokens.length != 0) {
			for (int i = 0; i < tokens.length; i++)
				query.setParameter("token" + i, '%' + tokens[i] + '%');

		}

		List<Post> posts = query.getResultList();
		boolean hasNext = posts.size() == (size + 1);

		if (hasNext) {
			posts.remove(posts.size() - 1);
		}

		return new SliceImpl<>(posts, PageRequest.of(page, size), hasNext);

	}

}