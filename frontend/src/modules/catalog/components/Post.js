import ImageGallery from "react-image-gallery";
// import stylesheet if you're not already using CSS @import
import "react-image-gallery/styles/css/image-gallery.css";
import {PostLink} from "../../common";
import { ExternalLinkButton } from "../../common";
import OfferIcon from "./OfferIcon";
import CouponIcon from "./CouponIcon";

const Post = ({ id, title, description, url, price, categoryDto, images, type }) => {
	const components = {
		'Offer': OfferIcon,
		'Coupon': CouponIcon
	};
	const Icon = components[type];

return (
		<div className="col mb-5">
			<div className="card h-100">
				<div className="card-body p-4">
					<div className="text-center">
						<div className="fw-bolder pb-2 row">
							<div className="border-right col"><Icon/></div> <div className="col">{categoryDto.name ? categoryDto.name : 'Uncategorized'}</div>
						</div>
						<div className="border-top pt-4">
							{images.length === 0 ?
								(<ImageGallery showFullscreenButton={false} showPlayButton={false} showNav={false}
									items={[{ original: '/poster/assets/noimage.png', originalHeight: "200px" }]} />) :
								(<ImageGallery showFullscreenButton={false} showPlayButton={false}
									items={images.map((image) => ({ original: `data:image/*;base64,${image}`, originalHeight: "200px" }))} />)}
						</div>
						<hr className="my-4" />
						<h5 className="fw-bolder">
							<PostLink id={id} title={title}/> &nbsp; {url &&
								<ExternalLinkButton url={`//${url}`} />
							}
						</h5>
						<p><i>{description}</i></p>

						<hr className="my-4" />
						<p className="post-price">{price.toFixed(2)}€</p>
					</div>
				</div>
			</div>
		</div>);
	
}

export default Post;