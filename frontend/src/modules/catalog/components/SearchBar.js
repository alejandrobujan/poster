import { useState } from 'react';
import { Range } from 'react-range';
import { useDispatch, useSelector } from 'react-redux';

import CategorySelector from './CategorySelector';

import * as selectors from '../selectors';
import * as actions from '../actions';


const SearchBar = () => {
	const dispatch = useDispatch();
	const [showAdvancedSearch, setShowAdvancedSearch] = useState(false);
	let submit;

	const searchParams = useSelector(selectors.getSearchParams);

	const handleSubmit = event => {
		event.preventDefault();
		dispatch(actions.findPosts(
			{
				keywords: searchParams.keywords,
				filters: {
					...searchParams.filters,
					categoryId: toNumber(searchParams.filters.categoryId),
					type: checkNull(searchParams.filters.type),
					date: checkNull(searchParams.filters.date),
					sortParam: checkNull(searchParams.filters.sortParam),
					sortOrder: checkNull(searchParams.filters.sortOrder)
				},
				page: 0
			}
		));
	}

	const handleApply = () => {
		setShowAdvancedSearch(false);
		submit.click();
	}

	const toNumber = value => value.length > 0 ? Number(value) : null;

	const checkNull = value => value.length > 0 ? value : null;

	const handleSliderChange = (values) => {
		dispatch(actions.setMinPrice(values[0]));
		dispatch(actions.setMaxPrice(values[1]));
	};

	return (
		<form onSubmit={e => handleSubmit(e)}>
			<div className="input-group search-bar flex-nowrap mr-2">
				<input id="keywords" type="text" className="form-control" placeholder="Search..."
					value={searchParams.keywords} onChange={e => dispatch(actions.setKeywords(e.target.value))} />
				<div className="input-group-append">
					<button ref={node => submit = node} type="submit" className="btn btn-primary" onClick={handleSubmit}>
						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-search" viewBox="0 0 16 16">
							<path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z" />
						</svg>
					</button>
					<button type="button" className="btn btn-secondary" onClick={() => setShowAdvancedSearch(!showAdvancedSearch)}>
						<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" className="bi bi-funnel-fill" viewBox="0 0 16 16">
							<path d="M1.5 1.5A.5.5 0 0 1 2 1h12a.5.5 0 0 1 .5.5v2a.5.5 0 0 1-.128.334L10 8.692V13.5a.5.5 0 0 1-.342.474l-3 1A.5.5 0 0 1 6 14.5V8.692L1.628 3.834A.5.5 0 0 1 1.5 3.5v-2z"></path>
						</svg>
					</button>
				</div>
			</div>
			{showAdvancedSearch && (
				<div className="advanced-search">
					<h5 className='text-center'>Filters</h5>
					<div className="row border flex-column flex-sm-row">
						<div className="col p-4 border-right">
							<div className="form-group">
								<label htmlFor="categorySelector">Category:</label>
								<CategorySelector id="categoryId" className="form-control"
									value={searchParams.filters.categoryId} onChange={e => dispatch(actions.setCategoryId(e.target.value))} />

							</div>
						</div>
						<div className="col p-4">
							<div className="form-group">
								<label htmlFor="typeSelect">Type:</label>
								<select className="form-control" id='typeSelect' value={searchParams.filters.type} onChange={e => dispatch(actions.setType(e.target.value))}>
									<option value="">Select</option>
									<option value="Offer">Offer</option>
									<option value="Coupon">Coupon</option>
								</select>
							</div>
						</div>
					</div>
					<div className="row border flex-column flex-sm-row">
						<div className="col p-4 flex-column flex-sm-row">
							Price:
							<div className='p-4'>
								<Range
									step={10}
									min={0}
									max={1000000}
									values={[searchParams.filters.price.gte, searchParams.filters.price.lte]}
									onChange={handleSliderChange}
									renderTrack={({ props, children }) => (
										<div {...props} style={{ ...props.style, height: '6px', width: '100%', backgroundColor: '#ccc' }}>
											{children}
										</div>
									)}
									renderThumb={({ props }) => (
										<div {...props} style={{ ...props.style, height: '20px', width: '20px', backgroundColor: '#007bff', borderRadius: '50%' }} />
									)}
								/>
							</div>
							<div className='row flex-column flex-sm-row'>
								<div className='col input-group'>
									<input type="number" className="form-control" min={0}
										max={1000000} value={searchParams.filters.price.gte} onChange={e => dispatch(actions.setMinPrice(Number(e.target.value)))} />
									<span className="input-group-text">€</span>
								</div>
								-
								<div className='col input-group'>
									<input type="number" className="form-control" min={0}
										max={1000000} value={searchParams.filters.price.lte} onChange={e => dispatch(actions.setMaxPrice(Number(e.target.value)))} />
									<span className="input-group-text">€</span>
								</div>
							</div>

						</div>
					</div>
					<div className="row border flex-column flex-sm-row">
						<div className="col p-4 border-right">
							<div className="form-group">
								<label htmlFor="dateSelect">Creation date:</label>
								<select className="form-control" id="dateSelect" value={searchParams.filters.date} onChange={e => dispatch(actions.setDate(e.target.value))}>
									<option value="">Any date</option>
									<option value="hour">Last hour</option>
									<option value="day">Last 24 hours</option>
									<option value="week">Last week</option>
									<option value="month">Last month</option>
									<option value="year">Last year</option>
								</select>
							</div>
							<div className="form-check text-center">
								<input className="form-check-input" type="checkbox" id="expiredCheckbox" checked={searchParams.filters.expired} onChange={() => dispatch(actions.setExpired(!searchParams.filters.expired))} />
								<label className="form-check-label" htmlFor="expiredCheckbox">
									Include expired posts
								</label>
							</div>
						</div>
						<div className="col p-4 border-right">
							<div className="form-group">
								<label htmlFor="dateSelect">Sort by:</label>
								<select className="form-control" id="sortParamSelect" value={searchParams.filters.sortParam} onChange={e => dispatch(actions.setSortParam(e.target.value))}>
									<option value="creationDate">Creation date</option>
									<option value="expirationDate">Expiration date</option>
									<option value="title">Title</option>
									<option value="price">Price</option>
									<option value="positiveRatings">Positive ratings</option>
									<option value="negativeRatings">Negative ratings</option>
								</select>
							</div>
						</div>
						<div className="col p-4">
							<div className="form-group">
								<label htmlFor="dateSelect">Order:</label>
								<select className="form-control" id="sortOrderSelect" value={searchParams.filters.sortOrder} onChange={e => dispatch(actions.setSortOrder(e.target.value))}>
									<option value="DESC">Descendent</option>
									<option value="ASC">Ascendent</option>
								</select>
							</div>
						</div>
					</div>
					<div className='text-center m-4'>
						<button className="btn btn-primary" onClick={handleApply}>Apply</button>
					</div>
				</div>
			)}
		</form>
	);
};

export default SearchBar;
