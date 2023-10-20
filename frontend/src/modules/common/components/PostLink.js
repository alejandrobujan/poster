import {Link} from 'react-router-dom';

const PostLink = ({id, title}) => {

    return (
        <Link to={`/post/post-details/${id}`}>
            {title}
        </Link>
    );

}

export default PostLink;