import { getDate } from '../../../backend/utils';
import { useDispatch } from 'react-redux';
import * as actions from '../actions';
import { useNavigate } from 'react-router-dom';

const Notification = ({ id, text, viewed, creationDate, postId, notifierUserDto }) => {
	
	const dispatch = useDispatch();
	const navigate = useNavigate();
	
	const handleNotificationClick = () => {
		dispatch(actions.markAsViewed(id));
		navigate(`/post/post-details/${postId}`);
	}

	return (
		<button className="dropdown-item border-bottom" style={{ overflowX: 'hidden' }} onClick={handleNotificationClick}>
			<div className='' style={{minWidth: '300px'}}>
				<div className="row text-center">
					<div className="col-2">
						<img src={notifierUserDto.avatar ? `data:image/*;base64,${notifierUserDto.avatar}` : "/poster/assets/profile.png"} alt="Avatar" height="30px" width="30px" />
					</div>
					<div className="col-8 text-center">
						<small style={{ maxWidth: '100%', display: 'block', whiteSpace: 'normal', fontSize: '12px' }}>
							<span style={{ color: 'grey', display: 'block', whiteSpace: 'nowrap' }}>{getDate(creationDate)}</span><br />
						</small>
					</div>
					<div className="col-2">
						{!viewed && <span className="badge bg-danger text-white rounded-pill"><small style={{color:'#dc3545'}}>&nbsp;•</small></span>}
					</div>
				</div>
				<span style={{ maxWidth: '100%', display: 'block', whiteSpace: 'normal', fontSize: '12px', textAlign: 'justify' }}>{text}</span>
			</div>
		</button>
	)
};

export default Notification;