import { useSelector } from 'react-redux';

import * as selectors from '../../post/selectors';

import ImageGallery from "react-image-gallery";
// import stylesheet if you're not already using CSS @import
import "react-image-gallery/styles/css/image-gallery.css";

const ImagesCard = ({ images }) => {
	return (
		<div>
			<p>Uploaded images:</p>
			{images.length === 0 ?
				(<p className='noImages'>
					No uploaded images.
				</p>) : (
					<div className='images-card'>
						<ImageGallery showPlayButton={false} showFullscreenButton={false}
							items={images.map((image) => ({ original: `data:image/*;base64,${image}`, thumbnailHeight: "30px" }))} />
					</div>)
			}

		</div>
	);
};

export default ImagesCard;