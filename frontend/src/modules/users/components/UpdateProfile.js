import {useState} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {useNavigate} from 'react-router-dom';

import {Errors} from '../../common';
import * as actions from '../actions';
import * as selectors from '../selectors';

const UpdateProfile = () => {

    const user = useSelector(selectors.getUser);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [firstName, setFirstName] = useState(user.firstName);
    const [lastName, setLastName] = useState(user.lastName);
    const [email, setEmail]  = useState(user.email);
    const [login, setLogin] = useState(user.userName);
    const [backendErrors, setBackendErrors] = useState(null);
    let form;

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity()) {
            
            dispatch(actions.updateProfile(
                {id: user.id,
                firstName: firstName.trim(),
                lastName: lastName.trim(),
                email: email.trim(),
                userName: login.trim()},
                () => navigate('/users/update-profile'),
                errors => setBackendErrors(errors)));

        } else {

            setBackendErrors(null);
            form.classList.add('was-validated');

        }

    }

    return (
        <div>
            <Errors id="updateProfileErrors" errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="card bg-light border-dark">
                <h5 className="card-header">
                    Edit your profile
                </h5>
                <div className="card-body">
                    <form ref={node => form = node} 
                        className="needs-validation" noValidate onSubmit={e => handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="login" className="col-md-3 col-form-label">
                                Login
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="login" className="form-control"
                                    value={login}
                                    onChange={e => setLogin(e.target.value)}
                                    autoFocus
                                    required/>
                                <div className="invalid-feedback">
                                    The login is required
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="firstName" className="col-md-3 col-form-label">
                                First Name
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="firstName" className="form-control"
                                    value={firstName}
                                    onChange={e => setFirstName(e.target.value)}
                                    autoFocus
                                    required/>
                                <div className="invalid-feedback">
                                    The first name is required
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="lastName" className="col-md-3 col-form-label">
                                Last Name
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="lastName" className="form-control"
                                    value={lastName}
                                    onChange={e => setLastName(e.target.value)}
                                    required/>
                                <div className="invalid-feedback">
                                    The last name is required
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="email" className="col-md-3 col-form-label">
                                Email
                            </label>
                            <div className="col-md-4">
                                <input type="email" id="email" className="form-control"
                                    value={email}
                                    onChange={e => setEmail(e.target.value)}
                                    required/>
                                <div className="invalid-feedback">
                                    The email is required
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <div className="offset-md-3 col-md-1">
                                <button type="submit" className="btn btn-primary">
                                    Save
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );

}

export default UpdateProfile;