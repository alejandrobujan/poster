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

/**
 * The Class CatalogController.
 */
@RestController
@RequestMapping("/api/catalog")
public class CatalogController {

	/** The post conversor. */
	private final Map<String, PostConversor> conversors;

	/** The catalog service. */
	@Autowired
	private CatalogService catalogService;

	/**
	 * The catalog controller.
	 * 
	 * @param offerConversor  the offer conversor
	 * @param couponConversor the coupon conversor
	 */
	@Autowired
	public CatalogController(OfferConversor offerConversor, CouponConversor couponConversor) {
		this.conversors = Map.ofEntries(entry("Offer", offerConversor), entry("Coupon", couponConversor));
	}

	/**
	 * Find Posts.
	 *
	 * @param params the params
	 * @return the post summary dto
	 */
	@PostMapping("/feed")
	public BlockDto<PostSummaryDto> findPosts(@RequestBody SearchParamsDto params) {

		Block<Post> postBlock = catalogService.findPosts(toSearchFilters(params.getFilters()),
				params.getKeywords() != null ? params.getKeywords().trim() : null, params.getPage(), 6);

		return new BlockDto<>(toPostSummaryDtos(postBlock.getItems()), postBlock.getExistMoreItems());

	}

	/**
	 * Find All Categories.
	 *
	 * @return list of category dto
	 */
	@GetMapping("/categories")
	public List<CategoryDto> findAllCategories() {

		return toCategoryDtos(catalogService.findAllCategories());

	}

	/**
	 * Find Posts By Id.
	 * 
	 * @param id the post id
	 * @return the post dto
	 * @throws InstanceNotFoundException the instance not found exception
	 */
	@GetMapping("/postDetail/{id}")
	public PostDto findPostById(@PathVariable Long id) throws InstanceNotFoundException {

		Post post = catalogService.findPostById(id);

		PostConversor postConversor = conversors.get(post.getClass().getSimpleName());

		return postConversor.toPostDto(post);
	}

}