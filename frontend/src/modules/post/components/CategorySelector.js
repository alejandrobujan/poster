import {useSelector} from 'react-redux';

import * as selectors from '../selectors';


const CategorySelector = (selectProps) => {

    const categories = useSelector(selectors.getCategories);

    return (

        <select {...selectProps} value={selectProps.value}>
            <option value={''} style={{"color" : "gray", "fontStyle" : "italic"}}>Select</option>
            {categories && categories.map(category => 
                <option key={category.id} value={category.id}>{category.name}</option>
            )}

        </select>

    );

}


export default CategorySelector;