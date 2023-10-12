import {useNavigate} from 'react-router-dom';

const BackLink = () => {

    const navigate = useNavigate();
    
    return (

        <button type="button" className="btn btn-link" 
            onClick={() => navigate(-1)}>

            Back

        </button>

    );

};

export default BackLink;