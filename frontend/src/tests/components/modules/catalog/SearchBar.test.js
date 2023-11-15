import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import React from "react";
import '@testing-library/jest-dom';
import '@testing-library/jest-dom/extend-expect';
import configureMockStore from 'redux-mock-store';
import renderer from "react-test-renderer";
import { SearchBar, Feed } from "../../../../modules/catalog";
import { Provider } from 'react-redux';
import { createMemoryHistory } from 'history';
import thunk from 'redux-thunk';
import { searchBarMock } from "../state/SearchBar.mock";
import { appFetch } from "../../../../backend/appFetch";
import { createRouterWrapper } from '../../utils/create-router-wrapper';

/*'./utils/create-router-wrapper';*/

jest.mock("../../../../backend/appFetch");

describe("SearchBar", () => {

	const middlewares = [thunk];
	const mockStore = configureMockStore(middlewares);
	let store;
	store = mockStore(searchBarMock);

	it("renders correctly", () => {
		const tree = renderer
			.create(
				<Provider store={store}>
					<SearchBar />
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});

	it("renders all fields", async () => {
		render(
			<Provider store={store}>
				<SearchBar />
			</Provider>
		);

		/* Form */
		const form = screen.getByTestId('search-posts-form');
		expect(form).toBeInTheDocument();

		/* Search keywords bar */
		const keywordsBar = screen.getByTestId('search-keywords-bar');
		expect(keywordsBar).toBeInTheDocument();

		const advancedSearchbutton = screen.getByTestId("show-advanced-search-button");
		await waitFor(() => fireEvent.click(advancedSearchbutton));

		/* Advanced search fields */
		const advancedSearch = screen.getByTestId('advanced-search-fields');
		expect(advancedSearch).toBeInTheDocument();

		/* Category selector */
		const categorySelector = screen.getByTestId('search-category-selector');
		expect(categorySelector).toBeInTheDocument();

		/* Type selector */
		const typeSelector = screen.getByTestId('search-type-selector');
		expect(typeSelector).toBeInTheDocument();

		screen.findByText("Offer");
		screen.findByText("Coupon");

		/* Price range selector */
		const priceRangeSelector = screen.getByTestId('search-price-range-selector');
		expect(priceRangeSelector).toBeInTheDocument();

		/* Price range selector */
		const minPriceSelector = screen.getByTestId('search-minprice-selector');
		expect(minPriceSelector).toBeInTheDocument();

		/* Price range selector */
		const maxPriceSelector = screen.getByTestId('search-maxprice-selector');
		expect(maxPriceSelector).toBeInTheDocument();

		/* Creation date selector */
		const creationDateSelector = screen.getByTestId('search-creation-date-selector');
		expect(creationDateSelector).toBeInTheDocument();

		screen.findByText("Any date");
		screen.findByText("Last hour");
		screen.findByText("Last 24 hours");
		screen.findByText("Last week");
		screen.findByText("Last month");
		screen.findByText("Last year");

		/* Include expired input */
		const includeExpiredInput = screen.getByTestId('search-include-expired-input');
		expect(includeExpiredInput).toBeInTheDocument();

		/* Sort by selector */
		const sortBySelector = screen.getByTestId('search-sort-by-selector');
		expect(sortBySelector).toBeInTheDocument();

		screen.findByText("Creation date");
		screen.findByText("Expiration date");
		screen.findByText("Title");
		screen.findByText("Price");
		screen.findByText("Positive ratings");
		screen.findByText("Negative ratings");

		/* Order selector */
		const orderSelector = screen.getByTestId('search-order-selector');
		expect(orderSelector).toBeInTheDocument();

		screen.findByText("Ascendent");
		screen.findByText("Descendent");

	});

	it("renders all fields default", async () => {
		render(
			<Provider store={store}>
				<SearchBar />
			</Provider>
		);

		const advancedSearchbutton = screen.getByTestId("show-advanced-search-button");
		await waitFor(() => fireEvent.click(advancedSearchbutton));

		/* Category selector */
		const categorySelector = screen.getByTestId('search-category-selector');
		expect(categorySelector.getAttribute('value')).toBe(null);

		/* Type selector */
		const typeSelector = screen.getByTestId('search-type-selector');
		expect(typeSelector.getAttribute('value')).toBe(null);

		/* Price range selector */
		const minPriceSelector = screen.getByTestId('search-minprice-selector');
		expect(minPriceSelector.getAttribute('value')).toBe("0");

		/* Price range selector */
		const maxPriceSelector = screen.getByTestId('search-maxprice-selector');
		expect(maxPriceSelector.getAttribute('value')).toBe("1000000");

		/* Creation date selector */
		const creationDateSelector = screen.getByTestId('search-creation-date-selector');
		expect(creationDateSelector.getAttribute('value')).toBe(null);

		/* Include expired input */
		const includeExpiredInput = screen.getByTestId('search-include-expired-input');
		expect(includeExpiredInput.getAttribute('value')).toBe(null);

		/* Sort by selector */
		const sortBySelector = screen.getByTestId('search-sort-by-selector');
		expect(sortBySelector.getAttribute('value')).toBe(null);

		/* Order selector */
		const orderSelector = screen.getByTestId('search-order-selector');
		expect(orderSelector.getAttribute('value')).toBe(null);

	});

	it("check router when submit", async () => {
		const history = createMemoryHistory();

		appFetch.mockResolvedValue(JSON.stringify({
			searchParams: [{
				keywords: "Seat",
				filters: [{
					categoryId: "1",
					type: "Offer",
					price: [{
						gte: 50,
						lte: 300
					}],
					date: "Last week",
					expired: false,
					sortParam: "creationDate",
					sortOrder: "DESC"
				}]
			}]
		}));

		render(
			<Provider store={store}>
				<SearchBar />
			</Provider>,
			{ wrapper: createRouterWrapper(history) }
		);

		const advancedSearchbutton = screen.getByTestId("show-advanced-search-button");
		await waitFor(() => fireEvent.click(advancedSearchbutton));
		
		const applyButton = screen.getByTestId("search-apply-button");
		await waitFor(() => fireEvent.click(applyButton));

		expect(appFetch.mock.calls.length).toBe(2);

		expect(history.location.pathname).toBe('/');

	});
});