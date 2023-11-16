import { Link } from 'react-router-dom';
import { getDate } from '../../../backend/utils';

const Notification = ({ text, viewed, creationDate, postId, notifierUserDto }) => {

	return (
		<Link className="dropdown-item border-bottom" style={{ overflowX: 'hidden' }} to={`/post/post-details/${postId}`}>
			<div className='' style={{width: '300px'}}>
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
		</Link>
	)
};

export default Notification;