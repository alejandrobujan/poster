

const Errors = ({id, errors, onClose}) => {

    if (!errors) {
        return null;
    }

    let globalError;
    let fieldErrors;

    if (errors.globalError) {
        globalError = errors.globalError;
    } else if (errors.fieldErrors) {
        fieldErrors = [];
        errors.fieldErrors.forEach(e => {
            fieldErrors.push(`${e.fieldName}: ${e.message}`)
        });

    }

    if(onClose) {
        return (
            <div id={id} className="alert alert-danger alert-dismissible fade show" role="alert">
                {globalError ? globalError : ''}
                {fieldErrors ?
                    <ul>
                        {fieldErrors.map((fieldError, index) =>
                            <li key={index}>{fieldError}</li>
                        )}
                    </ul>
                    :
                    ''
                }
                <button type="button" className="close" data-dismiss="alert" aria-label="Close"
                        onClick={() => onClose()}>
                    <span aria-hidden="true">&times;</span>
                </button>

            </div>

        );
    }else {
        return (
            <div id={id} className="alert alert-danger" role="alert">
                {globalError ? globalError : ''}
                {fieldErrors ?
                    <ul>
                        {fieldErrors.map((fieldError, index) =>
                            <li key={index}>{fieldError}</li>
                        )}
                    </ul>
                    :
                    ''
                }
            </div>
        );
    }

}

export default Errors;