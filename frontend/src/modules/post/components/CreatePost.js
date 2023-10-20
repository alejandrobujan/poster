import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import { CategorySelector } from '../../catalog';

import { Errors } from '../../common';

import * as actions from '../actions';

import { fileToBase64, isImage } from '../../../backend/utils';

const CreatePost = () => {
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const properties = {};
	const [title, setTitle] = useState('');
	const [description, setDescription] = useState('');
	const [url, setUrl] = useState('');
	const [price, setPrice] = useState(0);
	const [categoryId, setCategoryId] = useState('');
	const [images, setImages] = useState([]);
	const [backendErrors, setBackendErrors] = useState(null);
	const [wrongFileType, setWrongFileType] = useState(false);
	const [type, setType] = useState('Offer');
	const [code, setCode] = useState('');
	let form;
	let imagesInput;
	let clearImages;

	const handleSubmit = event => {

		event.preventDefault();

		if (type === 'Coupon') {
			properties.code = code;
		}

		if (form.checkValidity()) {
			dispatch(actions.createPost(
				title, description, url,
				price, categoryId !== '' ? categoryId : null, images, type, properties, () => navigate('/'), errors => setBackendErrors(errors)
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

	const handleOfferTypeChange = () => {
		setType('Offer');
		setCode('');
	}

	const handleCouponTypeChange = () => {
		setType('Coupon');
		setCode('');
	}

	return (
		<div className='container'>
			<Errors id="createPostErrors" errors={backendErrors} onClose={() => setBackendErrors(null)} />
			<div className="card bg-light border-dark m-4">
				<h5 className="card-header text-center">
					Create post
				</h5>
				<div className="card-body">
					<nav aria-label="page navigation">
						<ul className="pagination justify-content-center">
							<li className={`page-item ${type === 'Offer' ? "disabled" : ""}`}>
								<button className="page-link"
									onClick={handleOfferTypeChange}>
									Offer
								</button>
							</li>
							<li className={`page-item ${type === 'Coupon' ? "disabled" : ""}`}>
								<button className="page-link"
									onClick={handleCouponTypeChange}>
									Coupon
								</button>
							</li>
						</ul>
					</nav>
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
										step="0.01" min="0.00" max="999999.99"
										required
									/>
									<span className="input-group-text">€</span>
								</div>

								<div className="invalid-feedback">
									Price must be between 0 and 999999.99
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
						{type === 'Coupon' &&
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
								Submit
							</button>
						</div>
					</form>
				</div>
			</div >
		</div >
	);
};

export default CreatePost;