import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import CategorySelector from './CategorySelector';

import { Errors } from '../../common';

import * as actions from '../actions';

import { fileToBase64 } from '../../../backend/fileToBase64';

const CreatePost = () => {
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const [title, setTitle] = useState('');
	const [description, setDescription] = useState('');
	const [url, setUrl] = useState('');
	const [price, setPrice] = useState(0);
	const [categoryId, setCategoryId] = useState('');
	const [images, setImages] = useState([]);
	const [backendErrors, setBackendErrors] = useState(null);

	let form;

	const handleSubmit = event => {

		event.preventDefault();

		if (form.checkValidity()) {
			dispatch(actions.createPost(
				title, description, url,
				price, categoryId !== '' ? categoryId : null, images, () => navigate('/poster')
			));
		} else {
			setBackendErrors(null);
			form.classList.add('was-validated');
		}
	}

	return (
		<div className='container'>
			<Errors id="createPostErrors" errors={backendErrors} onClose={() => setBackendErrors(null)} />
			<div className="card bg-light border-dark m-4">
				<h5 className="card-header text-center">
					Create post
				</h5>
				<div className="card-body">
					<form ref={node => form = node}
						className="needs-validation" noValidate
						onSubmit={e => handleSubmit(e)}>
						<div className="form-group row">
							<label htmlFor="title" className="col-md-3 col-form-label">
								Title
							</label>
							<div className="col-md-9">
								<input type="text"
									className="form-control"
									value={title}
									onChange={e => setTitle(e.target.value)}
									autoFocus
									minLength={1}
									maxLength={60}
									required />
								<div className="invalid-feedback">
									The title size must be between 1 and 60
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="description" className="col-md-3 col-form-label">
								Description
							</label>
							<div className="col-md-9">
								<textarea
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
								<input type="text"
									className="form-control"
									value={url}
									onChange={e => setUrl(e.target.value)}
									minLength={0}
									maxLength={2048}
								/>
								<div className="invalid-feedback">
									The url size must be between 0 and 2048
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="price" className="col-md-3 col-form-label">
								Price
							</label>
							<div className="col-md-3">
								<input type="number"
									className="form-control"
									value={price}
									onChange={e => setPrice(Number(e.target.value))}
									step="0.01" min="0.00" max="9999999.99"
									required
								/>
								<div className="invalid-feedback">
									Price must be between 0 and 9999999.99
								</div>
							</div>
							<label htmlFor="category" className="col-md-3 col-form-label">
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
								<input type="file"
									accept="image/*"
									onChange={async e => setImages(await Promise.all(Array.from(e.target.files).map(async file => await fileToBase64(file))))}
									multiple />
							</div>
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