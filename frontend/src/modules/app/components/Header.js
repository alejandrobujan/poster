import {useSelector} from 'react-redux';
import {Link} from 'react-router-dom';

import users from '../../users';

const Header = () => {

    const userName = useSelector(users.selectors.getUserName);

    return (

        <nav className="navbar navbar-expand-lg navbar-light bg-light border">
            <Link className="navbar-brand" to="/">Poster project</Link>
            <button className="navbar-toggler" type="button" 
                data-toggle="collapse" data-target="#navbarSupportedContent" 
                aria-controls="navbarSupportedContent" aria-expanded="false" 
                aria-label="Toggle navigation">
                <span className="navbar-toggler-icon"></span>
            </button>
			<Link className="navbar-brand" to="/post/find-posts">
				Posts
			</Link>
            <div className="collapse navbar-collapse" id="navbarSupportedContent">

                <ul className="navbar-nav mr-auto">
                </ul>
                
                {userName ? 

                <ul className="navbar-nav">

                        <li className="nav-item">
                            <Link className="nav-link" to="/post/create-post">
                                Create new post
                            </Link>
                        </li>  
                
                    <li className="nav-item dropdown">

                        <a id="userNameLink" className="dropdown-toggle nav-link" href="/"
                            data-toggle="dropdown">
                            <span className="fa-solid fa-user"></span>&nbsp;
                            {userName}
                        </a>
                        <div className="dropdown-menu dropdown-menu-right">
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