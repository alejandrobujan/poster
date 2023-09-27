import {useSelector} from 'react-redux';
import {Link} from 'react-router-dom';

import users from '../../users';

const Header = () => {

    const userName = useSelector(users.selectors.getUserName);
    const avatar = useSelector(users.selectors.getAvatar);

    return (

        <nav className="navbar navbar-expand-lg navbar-light bg-light border">
            <Link className="navbar-brand" to="/poster"><img src="/poster/assets/logo.png" alt="Poster" height="50px"/></Link>
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

                        <li className="nav-item">
                            <Link className="nav-link" to="/poster/post/create-post">
                                Create new post
                            </Link>
                        </li>  
                
                    <li className="nav-item dropdown">

                        <a id="userNameLink" className="dropdown-toggle nav-link" href="/"
                            data-toggle="dropdown">
                            <img src={avatar ? `data:image/*;base64,${avatar}` : "/poster/assets/profile.png"} alt="Avatar" height="30px" width="30px"/>&nbsp;
                            {userName}
                        </a>
                        <div className="dropdown-menu dropdown-menu-right">
                            <div className="dropdown-divider"></div>
                            <Link className="dropdown-item" to="/poster/users/logout">
                                Logout
                            </Link>
                        </div>

                    </li>

                </ul>
                
                :

                <ul className="navbar-nav">
                    <li className="nav-item">
                        <Link id="loginLink" className="nav-link" to="/poster/users/login">
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