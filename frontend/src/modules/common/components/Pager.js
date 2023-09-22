import {FormattedMessage} from 'react-intl';

const Pager = ({back, next}) => (

    <nav aria-label="page navigation">
        <ul className="pagination justify-content-center">
            <li className={`page-item ${back.enabled ? "": "disabled"}`}>
                <button className="page-link"
                    onClick={back.onClick}>
                    <FormattedMessage id='project.global.buttons.back'/>
                </button>
            </li>
            <li className={`page-item ${next.enabled ? "": "disabled"}`}>
                <button className="page-link"
                    onClick={next.onClick}>
                    <FormattedMessage id='project.global.buttons.next'/>
                </button>
            </li>
        </ul>
    </nav>

);

export default Pager;