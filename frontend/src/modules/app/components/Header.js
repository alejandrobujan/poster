import { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Link } from 'react-router-dom';

import users from '../../users';
import notification from '../../notification';
import { Notifications } from '../../notification';

const Header = () => {
	
	const dispatch = useDispatch();	
	const userNotifications = useSelector(notification.selectors.getNotifications);
	const userId = useSelector(users.selectors.getUserId);
	const userName = useSelector(users.selectors.getUserName);
	const avatar = useSelector(users.selectors.getAvatar);
	const isLoggedIn = useSelector(users.selectors.isLoggedIn);

	
	useEffect(() => {
		if(isLoggedIn){
			dispatch(notification.actions.findNotifications());
		}
	}, [dispatch, userId, isLoggedIn]);

	
	return (

		<nav className="navbar navbar-expand-lg navbar-light bg-light border">
			<Link className="navbar-brand" to="/"><img src="/poster/assets/logo.png" alt="Poster" height="50px" /></Link>
			<button className="navbar-toggler" type="button"
				data-toggle="collapse" data-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span className="navbar-toggler-icon"></span>
			</button>
			<div className="collapse navbar-collapse" id="navbarSupportedContent">

				<ul className="navbar-nav mr-auto">
				</ul>

				{userName ?

					<ul className="navbar-nav">

						<Notifications notifications={userNotifications}/>

						<li className="nav-item">
							<Link className="nav-link" to="/post/create-post">
								Create new post
							</Link>
						</li>

						<li className="nav-item dropdown">

							<a id="userNameLink" className="dropdown-toggle nav-link"
								data-toggle="dropdown" href='#!'>
								<img src={avatar ? `data:image/*;base64,${avatar}` : "/poster/assets/profile.png"} alt="Avatar" height="30px" width="30px" />&nbsp;
								{userName}
							</a>
							<div className="dropdown-menu dropdown-menu-right">
								<Link className="dropdown-item" to="/users/profile-detail">
									View profile
								</Link>
								<div className="dropdown-divider"></div>
								<Link className="dropdown-item" to="/users/logout">
									Logout
								</Link>
							</div>

						</li>

					</ul>

					:

					<ul className="navbar-nav">
						<li className="nav-item">
							<Link id="loginLink" className="nav-link" to="/users/login">
								Login
							</Link>
						</li>
					</ul>

				}

			</div>
		</nav>

	);
};

export default Header;