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
	const [price, setPrice] = useState(1);
	const [categoryId, setCategoryId] = useState(1);
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
	         	<div className="form-group row">
                    <label htmlFor="title" className="offset-md-4 col-md-2 col-form-label">
                        Quantity
                    </label>
                    <div className="col-md-4">
                        <input type="text" className="form-control"
                               value={quantity}
                               onChange={e => setQuantity(Number(e.target.value))}
                               autoFocus
                               min="1"
                               max="10"
                               required
                        />
                        <div className="invalid-feedback">
                            <FormattedMessage id='project.global.validator.incorrectQuantity'/>
                        </div>
                    </div>
                </div>
	         </form>
	     </div>             
	 );
    
    
		
};