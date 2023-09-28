import { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import * as actions from '../actions';

import { Errors } from '../../common';
import { fileToBase64 } from '../../../backend/fileToBase64';


const SignUp = () => {

	const dispatch = useDispatch();
	const navigate = useNavigate();
	const [login, setLogin] = useState('');
	const [password, setPassword] = useState('');
	const [confirmPassword, setConfirmPassword] = useState('');
	const [firstName, setFirstName] = useState('');
	const [lastName, setLastName] = useState('');
	const [email, setEmail] = useState('');
	const [avatar, setAvatar] = useState('');
	const [backendErrors, setBackendErrors] = useState(null);
	const [passwordsDoNotMatch, setPasswordsDoNotMatch] = useState(false);
	let form;
	let confirmPasswordInput;
	let avatarInput;
	let clearAvatar;

	const handleSubmit = event => {

		event.preventDefault();

		if (form.checkValidity() && checkConfirmPassword()) {

			dispatch(actions.signUp(
				{
					userName: login.trim(),
					password: password,
					firstName: firstName.trim(),
					lastName: lastName.trim(),
					email: email.trim(),
					avatar: avatar
				},
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

	const handleAvatarChange = async e => {

		const maxSize = 1024000;
		const file = e.target.files[0];
		
		clearAvatar.style.display = file ? 'inline' : 'none';

		if (file && file.size <= maxSize) {
			setAvatar(await fileToBase64(file));
			avatarInput.setCustomValidity('');
		} else {
			setAvatar('');
			avatarInput.setCustomValidity(file ? 'error' : '');

		}

	}

	const handleClearAvatar = () => {
		
		avatarInput.value = '';
		avatarInput.setCustomValidity('');
		clearAvatar.style.display = 'none';

	}



	return (
		<div className="container">
			<Errors id="signUpErrors" errors={backendErrors} onClose={() => setBackendErrors(null)} />
			<div className="card bg-light border-dark m-4">
				<h5 className="card-header text-center">
					Sign up
				</h5>
				<div className="card-body">
					<form ref={node => form = node}
						className="needs-validation" noValidate
						onSubmit={e => handleSubmit(e)}>
						<div className="form-group row">
							<label htmlFor="login" className="col-md-6 col-form-label">
								Login
							</label>
							<div className="col-md-6">
								<input type="text" id="login" className="form-control"
									value={login}
									onChange={e => setLogin(e.target.value)}
									autoFocus
									required />
								<div className="invalid-feedback">
									The login is required
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="email" className="col-md-6 col-form-label">
								Email
							</label>
							<div className="col-md-6">
								<input type="text" id="email" className="form-control"
									value={email}
									onChange={e => setEmail(e.target.value)}
									required />
								<div className="invalid-feedback">
									The email is required
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="password" className="col-md-6 col-form-label">
								Password
							</label>
							<div className="col-md-6">
								<input type="password" id="password" className="form-control"
									value={password}
									onChange={e => setPassword(e.target.value)}
									required />
								<div className="invalid-feedback">
									The password is required
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="confirmPassword" className="col-md-6 col-form-label">
								Confirm password
							</label>
							<div className="col-md-6">
								<input ref={node => confirmPasswordInput = node}
									type="password" id="confirmPassword" className="form-control"
									value={confirmPassword}
									onChange={e => handleConfirmPasswordChange(e.target.value)}
									required />
								<div className="invalid-feedback">
									{passwordsDoNotMatch ?
										"The passwords don't match" :
										"The password confirmation is required"}
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="firstName" className="col-md-6 col-form-label">
								First name
							</label>
							<div className="col-md-6">
								<input type="text" id="firstName" className="form-control"
									value={firstName}
									onChange={e => setFirstName(e.target.value)}
									required />
								<div className="invalid-feedback">
									The first name is required
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="lastName" className="col-md-6 col-form-label">
								Last name
							</label>
							<div className="col-md-6">
								<input type="text" id="lastName" className="form-control"
									value={lastName}
									onChange={e => setLastName(e.target.value)}
									required />
								<div className="invalid-feedback">
									The last name is required
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="avatar" className="col-md-6 col-form-label">
								Avatar
							</label>
							<div className="col-md-6">
								<input ref={node => avatarInput = node} type="file" id="avatar" accept="image/*"
									onChange={handleAvatarChange}
								/>
								<button ref={node => clearAvatar = node} type="button" className="btn btn-outline-danger" onClick={handleClearAvatar} style={{'display':'none'}}>
									<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-trash" viewBox="0 0 16 16">
										<path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"></path>
										<path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"></path>
									</svg>
								</button>
								<div className="invalid-feedback">
									The maximum size allowed is 1MB.
								</div>
							</div>
						</div>
						<div className='text-center'>
							<button type="submit" className="btn btn-primary">
								Submit
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	);

}

export default SignUp;