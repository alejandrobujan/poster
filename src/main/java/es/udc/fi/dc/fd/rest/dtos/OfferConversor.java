package es.udc.fi.dc.fd.rest.dtos;

import java.util.Map;

import org.springframework.stereotype.Component;

import es.udc.fi.dc.fd.model.entities.Post;

@Component
public class OfferConversor extends PostConversor {

	@Override
	public PostDto toPostDto(Post post) {
		return new PostDto(post.getId(), post.getTitle(), post.getDescription(), post.getUrl(), post.getPrice(),
				new CategoryDto(post.getCategory().getId(), post.getCategory().getName()),
				new UserSummaryDto(post.getUser().getId(), post.getUser().getUserName(), post.getUser().getFirstName(),
						post.getUser().getLastName(), post.getUser().getAvatar()),
				toImageDtos(post.getImages()), toMillis(post.getCreationDate()), post.getPositiveRatings(),
				post.getNegativeRatings(), post.isExpired(), post.getClass().getSimpleName(),
				Map.ofEntries());

	}

}
