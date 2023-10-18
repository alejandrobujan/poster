package es.udc.fi.dc.fd.rest.controllers;

import static es.udc.fi.dc.fd.rest.dtos.CategoryConversor.toCategoryDtos;
import static es.udc.fi.dc.fd.rest.dtos.PostConversor.toPostSummaryDtos;
import static es.udc.fi.dc.fd.rest.dtos.SearchConversor.toSearchFilters;
import static java.util.Map.entry;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.udc.fi.dc.fd.model.common.exceptions.InstanceNotFoundException;
import es.udc.fi.dc.fd.model.entities.Post;
import es.udc.fi.dc.fd.model.services.Block;
import es.udc.fi.dc.fd.model.services.CatalogService;
import es.udc.fi.dc.fd.rest.dtos.BlockDto;
import es.udc.fi.dc.fd.rest.dtos.CategoryDto;
import es.udc.fi.dc.fd.rest.dtos.CouponConversor;
import es.udc.fi.dc.fd.rest.dtos.OfferConversor;
import es.udc.fi.dc.fd.rest.dtos.PostConversor;
import es.udc.fi.dc.fd.rest.dtos.PostDto;
import es.udc.fi.dc.fd.rest.dtos.PostSummaryDto;
import es.udc.fi.dc.fd.rest.dtos.SearchParamsDto;

@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

	private final Map<String, PostConversor> conversors;

	@Autowired
	private CatalogService catalogService;

	@Autowired
	public CatalogController(OfferConversor offerConversor, CouponConversor couponConversor) {
		this.conversors = Map.ofEntries(entry("Offer", offerConversor), entry("Coupon", couponConversor));
	}

	@PostMapping("/feed")
	public BlockDto<PostSummaryDto> findPosts(@RequestBody SearchParamsDto params) {

		Block<Post> postBlock = catalogService.findPosts(toSearchFilters(params.getFilters()),
				params.getKeywords() != null ? params.getKeywords().trim() : null, params.getPage(), 6);

		return new BlockDto<>(toPostSummaryDtos(postBlock.getItems()), postBlock.getExistMoreItems());

	}

	@GetMapping("/categories")
	public List<CategoryDto> findAllCategories() {

		return toCategoryDtos(catalogService.findAllCategories());

	}

	@GetMapping("/postDetail/{id}")
	public PostDto findPostById(@PathVariable Long id) throws InstanceNotFoundException {

		Post post = catalogService.findPostById(id);

		PostConversor postConversor = conversors.get(post.getClass().getSimpleName());

		return postConversor.toPostDto(post);
	}

}