import {useSelector} from 'react-redux';

import * as selectors from '../../post/selectors';

import ImageGallery from "react-image-gallery";
// import stylesheet if you're not already using CSS @import
import "react-image-gallery/styles/css/image-gallery.css";

const ImagesCard = ( {images} ) => {
	//const postImages = useSelector(selectors.getImages);
	return (
		<div className="images-card">
			<p>Uploaded images: </p>
			
			{images.length === 0 ?
							(<ImageGallery showFullscreenButton={false} showPlayButton={false} showNav={false}
								items={[{ original: '/poster/assets/noimage.png', originalHeight: "30px" }]} />) :
							(<ImageGallery showPlayButton={false} showFullscreenButton={false}
								items={images.map((image) => ({ original: `data:image/*;base64,${image}`, thumbnailHeight: "30px" }))} />)}
		</div>
	);
};

export default ImagesCard;