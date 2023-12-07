import { useEffect, useState } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { Feed } from '../../catalog';
import catalog from '../../catalog';
import * as selectors from '../selectors';
import * as actions from '../actions';


const Home = () => {

	const dispatch = useDispatch();
	const firstSearch = useSelector(selectors.getFirstSearch);
	const [referred, setReferred] = useState(true);
	const searchParams = useSelector(catalog.selectors.getSearchParams);

	const toNumber = value => value.length > 0 ? Number(value) : null;
	const checkNull = value => value.length > 0 ? value : null;

	useEffect(() => {
		if (firstSearch) {
			dispatch(catalog.actions.findPosts({ keywords: '', filters: {
				categoryId: null,
				type: null,
				price: {
				  gte: 0,
				  lte: 1000000
				},
				date: null,
				expired: false,
				sortParam: "creationDate",
				sortOrder: "DESC"
			  }, page: 0 }));
			dispatch(actions.setFirstSearch(false));
		}
		dispatch(catalog.actions.setRequestRefresh(false));
		dispatch(catalog.actions.subscribe());
	}, [dispatch, firstSearch]);

	if (referred) {
		dispatch(catalog.actions.findPosts({
			keywords: searchParams.keywords,
			filters: {
				...searchParams.filters,
				categoryId: toNumber(searchParams.filters.categoryId),
				type: checkNull(searchParams.filters.type),
				date: checkNull(searchParams.filters.date)
			},
			page: 0
		}));
		setReferred(false);
	}

	return (
		<div className="text-center">
			<Feed />
		</div>
	)
};

export default Home;
