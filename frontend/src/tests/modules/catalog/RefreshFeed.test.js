import RefreshFeed from "../../../modules/catalog/components/RefreshFeed";
import configureMockStore from 'redux-mock-store';
import renderer from "react-test-renderer";
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';
import { refreshFeedMock } from "../../state/RefreshFeed.mock";
import { render, fireEvent } from '@testing-library/react';

describe("RefreshFeed", () => {
	
	const middlewares = [thunk];
	const mockStore = configureMockStore(middlewares);
	let store;
	store = mockStore(refreshFeedMock);
	
	it("renders correctly", () => {
		const tree = renderer
			.create(
				<Provider store={store}>
					<RefreshFeed />
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});

	describe('refreshPage', () => {
		const { reload } = window.location;
	
		beforeAll(() => {
		  Object.defineProperty(window, 'location', {
			writable: true,
			value: { reload: jest.fn() },
		  });
		});
	
		afterAll(() => {
		  window.location.reload = reload;
		});
	
		it('reloads the window', () => {
			const { getByRole } = render(<RefreshFeed />);
    
			fireEvent.click(getByRole('button'));
		  expect(window.location.reload).toHaveBeenCalled();
		});
	});
});
