const UserCard = ({ user }) => {
	return (
		<div class="user-card">
			<img
				src={user.avatar ? `data:image/*;base64,${user.avatar}` : "/poster/assets/profile.png"}
				alt="Avatar"
				width="30px"
				height="30px"
			/>
			<div>
				<div><b>{`${user.userName}`}</b></div>
				<div><i>{`${user.firstName + " " + user.lastName}`}</i></div>
			</div>
		</div>
	);
};

export default UserCard;