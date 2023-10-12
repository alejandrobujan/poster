import ImageGallery from "react-image-gallery";
// import stylesheet if you're not already using CSS @import
import "react-image-gallery/styles/css/image-gallery.css";
import {PostLink} from "../../common";

const Post = ({ id, title, description, url, price, categoryDto, images }) => {
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
							<PostLink id={id} title={title}/> &nbsp; {url &&
							<a className="btn btn-primary" href={url} target="_blank">
								<div className="d-flex align-items-center">
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-link" viewBox="0 0 16 16">
										<path d="M6.354 5.5H4a3 3 0 0 0 0 6h3a3 3 0 0 0 2.83-4H9c-.086 0-.17.01-.25.031A2 2 0 0 1 7 10.5H4a2 2 0 1 1 0-4h1.535c.218-.376.495-.714.82-1z"></path>
										<path d="M9 5.5a3 3 0 0 0-2.83 4h1.098A2 2 0 0 1 9 6.5h3a2 2 0 1 1 0 4h-1.535a4.02 4.02 0 0 1-.82 1H12a3 3 0 1 0 0-6H9z"></path>
									</svg>
								</div>
							</a>
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