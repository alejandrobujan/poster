const UserCard = (user) => {

    return(
		
		<div>
			<img src={user.avatar ? `data:image/*;base64,${user.avatar}` : "/poster/assets/profile.png"} alt="Avatar" height="30px" width="30px"/>
			<b>{user.userName}</b>
			<i>{user.firstName}</i>
			<i>{user.lastName}</i>
		</div>
		
	);

};

export default UserCard;