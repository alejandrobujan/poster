import ImageGallery from "react-image-gallery";
// import stylesheet if you're not already using CSS @import
import "react-image-gallery/styles/css/image-gallery.css";

const Post = ({ title, description, url, price, categoryDto, images }) => {
	return (
		<div className="col mb-5">
			<div className="card h-100">
				<div className="card-body p-4">
					<div className="text-center">
						<div className="fw-bolder pb-2">
							{categoryDto.name ? categoryDto.name : 'Uncategorized'}
						</div>
						<div className="border-top pt-4">
							{images.length === 0 ?
								(<ImageGallery showFullscreenButton={false} showPlayButton={false} showNav={false}
									items={[{ original: '/poster/assets/noimage.png', originalHeight: "300px" }]} />) :
								(<ImageGallery showFullscreenButton={false} showPlayButton={false}
									items={images.map((image) => ({ original: `data:image/*;base64,${image}`, originalHeight: "300px" }))} />)}
						</div>
						<hr className="my-4" />
						<h5 className="fw-bolder">
							{title}
						</h5>
						<p><i>{description}</i></p>
						<hr className="my-4" />
						<ul className="list-unstyled">

							<li>{url}</li>
							<li>{price}</li>
						</ul>
					</div>
				</div>
			</div>
		</div>);
}

export default Post;