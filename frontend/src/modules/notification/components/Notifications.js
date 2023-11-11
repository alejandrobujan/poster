import Notification from './Notification';

const Notifications = ({ notifications }) => {
	if (!notifications || notifications.length === 0) {
		return (
			<li className="nav-item dropdown">
				<a className="dropdown-toggle nav-link"
					data-toggle="dropdown" href='#!'>
					<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-bell" viewBox="0 0 16 16">
						<path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z" />
					</svg>
				</a>
				<div className="dropdown-menu dropdown-menu-right p-4 text-center">
						<small>You have not unread notifications</small>
				</div>
			</li>
		);
	}

	return (
		<li className="nav-item dropdown">
			<a className="dropdown-toggle nav-link"
				data-toggle="dropdown" href='#!'>
				<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-bell" viewBox="0 0 16 16">
					<path d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z" />
				</svg>
				<span className="badge bg-danger text-white ms-1 rounded-pill">{notifications.length}</span>
			</a>
			<div className="dropdown-menu dropdown-menu-right" style={{maxHeight: '500px', overflowY: 'auto'}}>
				{notifications.map(({ id, text, viewed, creationDate, postId, notifierUserDto }) =>
					<Notification key={id} id={id} text={text} viewed={viewed} creationDate={creationDate}
						postId={postId} notifierUserDto={notifierUserDto} />
				)}
			</div>
		</li>

	)
};

export default Notifications;