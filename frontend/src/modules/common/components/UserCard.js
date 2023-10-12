const UserCard = ({ user }) => {
    return (
        <div className="card border mb-3" style={{ maxWidth: "10rem", borderRadius: "12px" }}>
            <div className="row no-gutters">
                <div className="col-md-4">
                    <img
                        src={user.avatar ? `data:image/*;base64,${user.avatar}` : "/poster/assets/profile.png"}
                        alt="Avatar"
                        className="card-img"
                        style={{ borderRadius: "12px 0 0 12px" }}
                    />
                </div>
                <div className="col-md-8">
                    <div className="card-body text-center p-2">
                        <h6 className="card-title font-weight-bold mb-1">{user.userName}</h6>
                        <p className="card-text mb-0">
                            <i>{`${user.firstName + " " + user.lastName}`}</i>
                        </p>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default UserCard;