import Feed from "../../../modules/catalog/components/Feed";
import configureMockStore from 'redux-mock-store';
import renderer from "react-test-renderer";
import { Provider } from 'react-redux';
import thunk from 'redux-thunk';
import { feedMock } from "../../state/Feed.mock";



describe("Feed", () => {

    const middlewares = [thunk];
	const mockStore = configureMockStore(middlewares);
	let store;
	store = mockStore(feedMock);
	
	it("renders correctly", () => {
		const tree = renderer
			.create(
				<Provider store={store}>
					<Feed />
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});


});