package es.udc.fi.dc.fd.rest.dtos;

import static java.util.Map.entry;

import java.util.Map;

import org.springframework.stereotype.Component;

import es.udc.fi.dc.fd.model.entities.Coupon;
import es.udc.fi.dc.fd.model.entities.Post;

/**
 * The Class CouponConversor.
 */
@Component
public class CouponConversor extends PostConversor {

	/**
	 * To post dto.
	 * 
	 * @param post the post
	 * @return the post dto
	 */
	@Override
	public PostDto toPostDto(Post post) {
		return new PostDto(post.getId(), post.getTitle(), post.getDescription(), post.getUrl(), post.getPrice(),
				(post.getCategory() != null ? new CategoryDto(post.getCategory().getId(), post.getCategory().getName())
						: null),
				new UserSummaryDto(post.getUser().getId(), post.getUser().getUserName(), post.getUser().getFirstName(),
						post.getUser().getLastName(), post.getUser().getAvatar()),
				toImageDtos(post.getImages()), toMillis(post.getCreationDate()), post.getPositiveRatings(),
				post.getNegativeRatings(), post.isExpired(),
				post.getValidationDate() != null ? toMillis(post.getValidationDate()) : null,
				post.getClass().getSimpleName(), Map.ofEntries(entry("code", ((Coupon) post).getCode())));

	}

}
