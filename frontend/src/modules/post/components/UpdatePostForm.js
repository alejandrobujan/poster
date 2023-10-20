import { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate, useParams } from 'react-router-dom';

import { CategorySelector } from '../../catalog';
import * as selectors from '../selectors';
import * as userSelectors from '../../users/selectors';

import { BackLink, Errors } from '../../common';

import * as actions from '../actions';

import { fileToBase64, isImage } from '../../../backend/utils';

import ImageGallery from "react-image-gallery";
// import stylesheet if you're not already using CSS @import
import "react-image-gallery/styles/css/image-gallery.css";

const UpdatePostForm = () => {
	const user = useSelector(userSelectors.getUser);
	const post = useSelector(selectors.getPost);
	const { id } = useParams();
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const properties = {};
	const [title, setTitle] = useState(post.title);
	const [description, setDescription] = useState(post.description);
	const [url, setUrl] = useState(post.url);
	const [price, setPrice] = useState(post.price);
	const [categoryId, setCategoryId] = useState(post.categoryDto != null ? post.categoryDto.id : '');
	const [images, setImages] = useState([]);
	const [backendErrors, setBackendErrors] = useState(null);
	const [wrongFileType, setWrongFileType] = useState(false);
	const [code, setCode] = useState(post.properties.code);
	const [imageGalleryImages, setImageGalleryImages] = useState(post.images);
	const [currentIndex, setCurrentIndex] = useState(0);
	let form;
	let imagesInput;
	let clearImages;

	const handleImagesGalleryChange = () => {
		setImageGalleryImages([...imageGalleryImages.slice(0, currentIndex), ...imageGalleryImages.slice(currentIndex + 1)]);
		setCurrentIndex(0);
	}

	const handleSlide = (currentIndex) => {
		setCurrentIndex(currentIndex);
	};

	const handleSubmit = event => {

		event.preventDefault();

		if (form.checkValidity()) {
			properties.code = code;
			dispatch(actions.updatePost(
				{
					authorId: user.id, id: id, title: title, description: description, url: url,
					price: price, categoryId: (categoryId !== '' ? categoryId : null), images: imageGalleryImages.concat(images), type: post.type, properties: properties
				}, () => navigate('/post/post-details/' + id), errors => setBackendErrors(errors)
			));
		} else {
			setBackendErrors(null);
			form.classList.add('was-validated');
		}
	}

	const handleImagesChange = async e => {

		const maxSize = 1024000;
		const files = Array.from(e.target.files);

		clearImages.style.display = files.length !== 0 ? 'inline' : 'none';

		if (files.length !== 0 && files.every(file => file.size <= maxSize)) {
			if (files.every(file => isImage(file))) {
				setImages(await Promise.all(files.map(async file => await fileToBase64(file))));
				imagesInput.setCustomValidity('');
				setWrongFileType(false);
			} else {
				setImages([]);
				imagesInput.setCustomValidity('error');
				setWrongFileType(true);
			}
		} else {
			setImages([]);
			imagesInput.setCustomValidity(files.length !== 0 ? 'error' : '');
			setWrongFileType(false);
		}



	}

	const handleClearImages = () => {

		imagesInput.value = '';
		imagesInput.setCustomValidity('');
		clearImages.style.display = 'none';
		setImages([]);

	}

	return (
		<div className='container'>
			<Errors id="updatePostErrors" errors={backendErrors} onClose={() => setBackendErrors(null)} />
			<div className="card bg-light border-dark m-4">
				<h5 className="card-header text-center">
					Update post
				</h5>
				<div className="card-body">
					<form ref={node => form = node}
						className="needs-validation" noValidate
						onSubmit={e => handleSubmit(e)}>
						<div className="form-group row">
							<label htmlFor="title" className="col-md-3 col-form-label">
								Title (*)
							</label>
							<div className="col-md-9">
								<input type="text"
									id="title"
									className="form-control"
									value={title}
									onChange={e => setTitle(e.target.value)}
									autoFocus
									minLength={1}
									maxLength={16}
									required />
								<div className="invalid-feedback">
									The title size must be between 1 and 60
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="description" className="col-md-3 col-form-label">
								Description (*)
							</label>
							<div className="col-md-9">
								<textarea
									id="description"
									className="form-control"
									value={description}
									onChange={e => setDescription(e.target.value)}
									minLength={1}
									maxLength={256}
									required />
								<div className="invalid-feedback">
									The description size must be between 1 and 256
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="url" className="col-md-3 col-form-label">
								Url
							</label>
							<div className="col-md-9">
								<div className="input-group">
									<span className="input-group-text">http(s)://</span>
									<input type="text"
										id="url"
										className="form-control"
										value={url}
										onChange={e => setUrl(e.target.value)}
										minLength={0}
										maxLength={2048}
									/>
								</div>
								<div className="invalid-feedback">
									The url size must be between 0 and 2048
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="price" className="col-md-3 col-form-label">
								Price (*)
							</label>
							<div className="col-md-3">
								<div className="input-group">
									<input type="number"
										id="price"
										className="form-control"
										value={price}
										onChange={e => setPrice(Number(e.target.value))}
										step="0.01" min="0.00" max="9999999.99"
										required
									/>
									<span className="input-group-text">€</span>
								</div>

								<div className="invalid-feedback">
									Price must be between 0 and 9999999.99
								</div>
							</div>
							<label htmlFor="categoryId" className="col-md-3 col-form-label">
								Category
							</label>
							<div className="col-md-3">
								<CategorySelector id="categoryId" className="form-control"
									value={categoryId} onChange={e => setCategoryId(e.target.value)} />
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="images" className="col-md-3 col-form-label">
								Images
							</label>
							<div className="col-md-9">
								<input ref={node => imagesInput = node} type="file"
									id="images"
									accept="image/*"
									onChange={handleImagesChange}
									multiple />
								<button ref={node => clearImages = node} type="button" className="btn btn-outline-danger" onClick={handleClearImages} style={{ 'display': 'none' }}>
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash" viewBox="0 0 16 16">
										<path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"></path>
										<path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"></path>
									</svg>
								</button>
								<div className="invalid-feedback">
									{wrongFileType ?
										"Only images are allowed" :
										"The maximum size allowed is 1MB."}
								</div>
							</div>
						</div>

						<div>
							<p>New uploaded images:</p>
							{images.length === 0 ?
								(<p className='noImages'>
									No new uploaded images.
								</p>) : (
									<div className='images-card'>
										<ImageGallery showPlayButton={false} showFullscreenButton={false} infinite={false}
											items={images.map((image) => ({ original: `data:image/*;base64,${image}`, thumbnailHeight: "30px" }))} />
									</div>)
							}

						</div>

						<div>
							<p>Previously uploaded images:</p>
							{imageGalleryImages.length === 0 ?
								(<p className='noImages'>
									No previously uploaded images.
								</p>) : (
									<div className='images-card' id='images-card' value='${imageGalleryImages}'>
										<ImageGallery showPlayButton={false} showFullscreenButton={false} infinite={false} onSlide={handleSlide}
											items={imageGalleryImages.map((image) => ({ original: `data:image/*;base64,${image}`, thumbnailHeight: "30px" }))} />
										<button type="button" className="btn btn-outline-danger" onClick={handleImagesGalleryChange}>
											<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash" viewBox="0 0 16 16">
												<path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"></path>
												<path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"></path>
											</svg>
										</button>
									</div>)
							}

						</div>
						{post.type === 'Coupon' &&
							<div className="form-group row">
								<label htmlFor="code" className="col-md-3 col-form-label">
									Code (*)
								</label>
								<div className="col-md-9">
									<input type="text"
										id="code"
										className="form-control"
										value={code}
										onChange={e => setCode(e.target.value)}
										required
									/>
									<div className="invalid-feedback">
										The code is mandatory
									</div>
								</div>
							</div>
						}
						<div>
							<p>
								(*) means mandatory field
							</p>
						</div>
						<div className="text-center">
							<button type="submit" className="btn btn-primary">
								Save changes
							</button>
						</div>
					</form>
					<BackLink />
				</div>
			</div >
		</div >
	);
};

export default UpdatePostForm;