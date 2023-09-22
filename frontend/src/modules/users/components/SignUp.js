import {useState} from 'react';
import {useDispatch} from 'react-redux';
import {useNavigate} from 'react-router-dom';

import {Errors} from '../../common';
import * as actions from '../actions';

const SignUp = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [name, setName] = useState('');
    const [avatar, setAvatar] = useState(null);
    const [backendErrors, setBackendErrors] = useState(null);
    const [passwordsDoNotMatch, setPasswordsDoNotMatch] = useState(false);
    let form;
    let confirmPasswordInput;

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity() && checkConfirmPassword()) {
            
            dispatch(actions.signUp(
                {login: login.trim(),
                password: password,
                name: firstName.trim(),
                avatar: avatar},
                () => navigate('/'),
                errors => setBackendErrors(errors),
                () => {
                    navigate('/users/login');
                    dispatch(actions.logout());
                }
            ));
            

        } else {

            setBackendErrors(null);
            form.classList.add('was-validated');

        }

    }

    const checkConfirmPassword = () => {

        if (password !== confirmPassword) {

            confirmPasswordInput.setCustomValidity('error');
            setPasswordsDoNotMatch(true);

            return false;

        } else {
            return true;
        }

    }

    const handleConfirmPasswordChange = value => {

        confirmPasswordInput.setCustomValidity('');
        setConfirmPassword(value);
        setPasswordsDoNotMatch(false);
    
    }

    return (
        <div>
            <Errors id="signUpErrors" errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <div className="card bg-light border-dark">
                <h5 className="card-header">
                    Sign up
                </h5>
                <div className="card-body">
                    <form ref={node => form = node}
                        className="needs-validation" noValidate 
                        onSubmit={e => handleSubmit(e)}>
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
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="password" className="col-md-3 col-form-label">
                                Password
                            </label>
                            <div className="col-md-4">
                                <input type="password" id="password" className="form-control"
                                    value={password}
                                    onChange={e => setPassword(e.target.value)}
                                    required/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="confirmPassword" className="col-md-3 col-form-label">
                                Confirm password
                            </label>
                            <div className="col-md-4">
                                <input ref={node => confirmPasswordInput = node}
                                    type="password" id="confirmPassword" className="form-control"
                                    value={confirmPassword}
                                    onChange={e => handleConfirmPasswordChange(e.target.value)}
                                    required/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="name" className="col-md-3 col-form-label">
                                Name
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="name" className="form-control"
                                    value={name}
                                    onChange={e => setName(e.target.value)}
                                    required/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="avatar" className="col-md-3 col-form-label">
                                Avatar
                            </label>
                            <div className="col-md-4">
                                <input type="image" id="avatar" className="form-control"
                                    value={avatar}
                                    onChange={e => setAvatar(Image(e.target.value))}
                                    required/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <div className="offset-md-3 col-md-2">
                                <button type="submit" className="btn btn-primary">
                                    Submit
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );

}

export default SignUp;