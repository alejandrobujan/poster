import { useSelector } from 'react-redux';

import * as selectors from '../selectors';

const ProfileDetail = () => {
	const user = useSelector(selectors.getUser);

	if (!user) {
		return null;
	}


	return (

		<div>
			<div className="card text-center">
				<div className="card-body">
					<img src={user.avatar ? `data:image/*;base64,${user.avatar}` : "/poster/assets/profile.png"} alt="Avatar" height="200px" width="200px" />&nbsp;
					<h5 className="card-title">{user.userName}</h5>
					<ul className="list-group list-group-flush">
						<li className="list-group-item">First Name: {user.firstName}</li>
						<li className="list-group-item">Last Name: {user.lastName}</li>
						<li className="list-group-item">Email: {user.email}</li>
					</ul>
				</div>
			</div>
		</div>

	);

}

export default ProfileDetail;