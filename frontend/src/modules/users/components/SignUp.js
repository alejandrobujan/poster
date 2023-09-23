import {useState} from 'react';
import {useDispatch} from 'react-redux';
import {useNavigate} from 'react-router-dom';

import * as actions from '../actions';

import {Errors} from '../../common';


const SignUp = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [firstName, setFirstName] = useState('');
    const [lastname, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [role, setRole] = useState('');
    const [avatar, setAvatar] = useState('');
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
                firstName: firstName.trim(),
                lastname: lastname.trim(),
                email: email.trim(),
                role: role.trim(),
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
                            <label htmlFor="firstName" className="col-md-3 col-form-label">
                                First name
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="firstName" className="form-control"
                                    value={firstName}
                                    onChange={e => setFirstName(e.target.value)}
                                    required/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="lastName" className="col-md-3 col-form-label">
                                Last name
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="LastName" className="form-control"
                                    value={lastname}
                                    onChange={e => setLastName(e.target.value)}
                                    required/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="role" className="col-md-3 col-form-label">
                                Role
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="role" className="form-control"
                                    value={role}
                                    onChange={e => setRole(e.target.value)}
                                    required/>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="avatar" className="col-md-3 col-form-label">
                                Avatar
                            </label>
                            <div className="col-md-4">
                                <input type="file" id="avatar" className="form-control" accept="image/*"
                                    value={avatar}
                                    onChange={e => setAvatar(e.target.files[0])}
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