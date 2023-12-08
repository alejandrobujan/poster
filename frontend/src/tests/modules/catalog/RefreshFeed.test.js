import RefreshFeed from "../../../modules/catalog/components/RefreshFeed";
import configureMockStore from 'redux-mock-store';
import renderer from "react-test-renderer";
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';
import { refreshFeedMock } from "../../state/RefreshFeed.mock";

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
});