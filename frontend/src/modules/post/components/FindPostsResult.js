import * as actions from '../actions';
import * as selectors from '../selectors';
import {Pager} from '../../common';  

import Posts from './Posts';

const FindPostsResult = () => {

    const postSearch = useSelector(selectors.getPostSearch);
    const dispatch = useDispatch();

    if (!postSearch) {
        return null;
    }

    if (postSearch.result.items.length === 0) {
        return (
            <div className="alert alert-info" role="alert">
                No posts
            </div>
        );
    }

    return (

        <div>
            <Posts posts={postSearch.result.items}/>
            <Pager
                back={{
                    enabled: postSearch.criteria.page >= 1,
                    onClick: () => dispatch(actions.previousFindPostsResultPage(postSearch.criteria))}}
                next={{
                    enabled: postSearch.result.existMoreItems,
                    onClick: () => dispatch(actions.nextFindPostsResultPage(postSearch.criteria))}}/>
        </div>

    );
}

export default FindPostsResult;