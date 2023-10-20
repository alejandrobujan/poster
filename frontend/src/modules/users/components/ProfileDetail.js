import { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { useParams, useNavigate } from 'react-router-dom';

import * as selectors from '../selectors';

const ProfileDetail = () => {

	const navigate = useNavigate();
	const user = useSelector(selectors.getUser);

	if (!user) {
		return null;
	}


	return (

		<div>
			<div className="card text-center">
				<div className="card-body row">
					<div className='col'>
						<img src={user.avatar ? `data:image/*;base64,${user.avatar}` : "/poster/assets/profile.png"} alt="Avatar" height="200px" width="200px" />&nbsp;
						<h5 className="card-title">{user.userName}</h5></div>
					<div className='col text-left'>
						<ul className="list-group list-group-flush">
							<li className="list-group-item">First Name: <b>{user.firstName}</b></li>
							<li className="list-group-item">Last Name: <b>{user.lastName}</b></li>
							<li className="list-group-item">Email: <b>{user.email}</b></li>
						</ul>
					</div>
				</div>
				<div className="text-center mb-4">
					<button type="button" className="btn btn-primary" onClick={() => navigate("/users/update-profile")}>
						Edit Profile
					</button>
				</div>
			</div>
		</div>
	);

}

export default ProfileDetail;