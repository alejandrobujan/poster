
const Pager = ({back, next}) => (

    <nav aria-label="page navigation">
        <ul className="pagination justify-content-center">
            <li className={`page-item ${back.enabled ? "": "disabled"}`}>
                <button className="page-link"
                    onClick={back.onClick}>
                    Back
                </button>
            </li>
            <li className={`page-item ${next.enabled ? "": "disabled"}`}>
                <button className="page-link"
                    onClick={next.onClick}>
                    Next
                </button>
            </li>
        </ul>
    </nav>

);

export default Pager;