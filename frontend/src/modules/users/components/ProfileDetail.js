import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

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
						<img data-testid="Avatar" src={user.avatar ? `data:image/*;base64,${user.avatar}` : "/poster/assets/profile.png"} alt="Avatar" height="200px" width="200px" />&nbsp;
						<h5 className="card-title" data-testid="UserName">{user.userName}</h5></div>
					<div className='col text-left'>
						<ul className="list-group list-group-flush">
							<li className="list-group-item" data-testid="FirstName">First Name: <b>{user.firstName}</b></li>
							<li className="list-group-item" data-testid="LastName">Last Name: <b>{user.lastName}</b></li>
							<li className="list-group-item" data-testid="Email">Email: <b>{user.email}</b></li>
						</ul>
					</div>
				</div>
				<div className="text-center mb-4">
					<button type="button" className="btn btn-primary" data-testid="EditButton" onClick={() => navigate("/users/update-profile")}>
						Edit Profile
					</button>
				</div>
			</div>
		</div>
	);

}

export default ProfileDetail;