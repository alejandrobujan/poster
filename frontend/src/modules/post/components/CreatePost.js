import {useState} from 'react';
import {useDispatch} from 'react-redux';

import {useNavigate} from 'react-router-dom';

import * as actions from '../actions';

const CreatePost = () => {
	const dispatch = useDispatch();
	const navigate = useNavigate();
	
	const [title, setTitle] = useState('');
	const [description, setDescription] = useState('');
	const [url, setUrl] = useState('');
	const [price, setPrice] = useState(0);
	const [categoryId, setCategoryId] = useState(0);
	const [images, setImages] = useState([]);
	const [backendErrors, setBackendErrors] = useState(null);
	
	const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity()) {
            dispatch(actions.createPost(
				title, description, url,
				price, categoryId, images 
			));
        } else {
            setBackendErrors(null);
            form.classList.add('was-validated');
        }
     }
     
     return(
		 <div>
			 <form ref={node => form = node}
	                  className="needs-validation" noValidate
	                  onSubmit={e => handleSubmit(e)}>
	         	<div>
                    <label htmlFor="title">
                        Title
                    </label>
                    <div>
                        <input type="text"
                               value={title}
                               onChange={e => setTitle(e.target.value)}
                               autoFocus
                               minLength={1}
                               maxLength={60}
                               required/>
                         <div className="invalid-feedback">
                            The title size must be between 1 and 60
                        </div>       
                    </div>
                </div>
                <div>
                    <label htmlFor="description">
                        Description
                    </label>
                    <div>
                        <input type="text"
                               value={description}
                               onChange={e => setDescription(e.target.value)}
                               autoFocus
                               minLength={1}
                               maxLength={256}
                               required/>
                        <div className="invalid-feedback">
                            The description size must be between 1 and 256
                        </div>        
                    </div>
                </div>
                <div>
                    <label htmlFor="url">
                        Url
                    </label>
                    <div>
                        <input type="text"
                               value={url}
                               onChange={e => setUrl(e.target.value)}
                               autoFocus
                               minLength={0}
                               maxLength={2048}
                               />
                        <div className="invalid-feedback">
                            The url size must be between 0 and 2048
                        </div>       
                    </div>
                </div>
                <div>
                    <label htmlFor="price">
                        Price
                    </label>
                    <div>
                        <input type="number"
                               value={price}
                               onChange={e => setPrice(Number(e.target.value))}
                               autoFocus
                               min="0"
                               required
                        />
                        <div className="invalid-feedback">
                            Price must be greater than 0
                        </div>
                    </div>
                </div>
                <div>
                    <label htmlFor="categoryId">
                        Category id
                    </label>
                    <div>
                        <input type="number"
                               value={categoryId}
                               onChange={e => setCategoryId(Number(e.target.value))}
                               autoFocus/>
                    </div>
                </div>
                <div>
                    <label htmlFor="images">
                        Images
                    </label>
                    <div>
                        <input type="image"
                               value={images}
                               onChange={e => setImages(Image(e.target.value))}
                               autoFocus
                               required/>
                    </div>
                </div>
	         </form>
	     </div>             
	 );	
};

export default CreatePost;