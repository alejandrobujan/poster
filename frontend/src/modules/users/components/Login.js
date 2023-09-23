import {useState} from 'react';
import {useDispatch} from 'react-redux';
import {Link} from 'react-router-dom';
import {useNavigate} from 'react-router-dom';


import * as actions from '../actions';

import {Errors} from '../../common';

const Login = () => {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [login, setLogin] = useState('');
    const [password, setPassword] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);
    let form;

    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity()) {

            dispatch(actions.login(
                login.trim(),
                password,
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

    return (
        <div>
            <Errors id="signUpErrors" errors={backendErrors} onClose={() => setBackendErrors(null)}/>
            <p className="text-center">
                <Link to="/users/signup">
                    Sign up
                </Link>
            </p>
            <div className="card bg-light border-dark">
                <h5 className="card-header">
                    Login
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
                            <div className="offset-md-3 col-md-1">
                                <button id="loginButton" type="submit" className="btn btn-primary">
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

export default Login;