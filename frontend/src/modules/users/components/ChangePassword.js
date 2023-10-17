import { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useNavigate } from 'react-router-dom';

import { Errors } from '../../common';
import * as actions from '../actions';
import * as selectors from '../selectors';

const ChangePassword = () => {

	const user = useSelector(selectors.getUser);
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const [oldPassword, setOldPassword] = useState('');
	const [newPassword, setNewPassword] = useState('');
	const [confirmNewPassword, setConfirmNewPassword] = useState('');
	const [backendErrors, setBackendErrors] = useState(null);
	const [passwordsDoNotMatch, setPasswordsDoNotMatch] = useState(false);
	let form;
	let confirmNewPasswordInput;

	const handleSubmit = event => {

		event.preventDefault();

		if (form.checkValidity() && checkConfirmNewPassword()) {

			dispatch(actions.changePassword(user.id, oldPassword, newPassword,
				() => navigate('/users/update-profile'),
				errors => setBackendErrors(errors)));

		} else {

			setBackendErrors(null);
			form.classList.add('was-validated');

		}

	}

	const checkConfirmNewPassword = () => {

		if (newPassword !== confirmNewPassword) {

			confirmNewPasswordInput.setCustomValidity('error');
			setPasswordsDoNotMatch(true);

			return false;

		} else {
			return true;
		}

	}

	const handleConfirmNewPasswordChange = event => {

		confirmNewPasswordInput.setCustomValidity('');
		setConfirmNewPassword(event.target.value);
		setPasswordsDoNotMatch(false);

	}

	return (
		<div className="container">
			<Errors errors={backendErrors} onClose={() => setBackendErrors(null)} />
			<div className="card bg-light border-dark m-5">
				<h5 className="card-header text-center">
					Change password
				</h5>
				<div className="card-body">
					<form ref={node => form = node}
						className="needs-validation" noValidate onSubmit={e => handleSubmit(e)}>
						<div className="form-group row">
							<label htmlFor="oldPassword" className="col-md-6 col-form-label">
								Old password
							</label>
							<div className="col-md-6">
								<input type="password" id="oldPassword" className="form-control"
									value={oldPassword}
									onChange={e => setOldPassword(e.target.value)}
									autoFocus
									required />
								<div className="invalid-feedback">
									The old password is required
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="newPassword" className="col-md-6 col-form-label">
								New password
							</label>
							<div className="col-md-6">
								<input type="password" id="newPassword" className="form-control"
									value={newPassword}
									onChange={e => setNewPassword(e.target.value)}
									required />
								<div className="invalid-feedback">
									The new password is required
								</div>
							</div>
						</div>
						<div className="form-group row">
							<label htmlFor="confirmNewPassword" className="col-md-6 col-form-label">
								Confirm new password
							</label>
							<div className="col-md-6">
								<input ref={node => confirmNewPasswordInput = node}
									type="password" id="confirmNewPassword" className="form-control"
									value={confirmNewPassword}
									onChange={e => handleConfirmNewPasswordChange(e)}
									required />
								<div className="invalid-feedback">
									{passwordsDoNotMatch ?
										"The passwords do not match" :
										"The password confirmation is required"}

								</div>
							</div>
						</div>
						<div className="form-group">
							<div className="text-center">
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

export default ChangePassword;