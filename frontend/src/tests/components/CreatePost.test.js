import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import store from "../../store";
import {CreatePost} from "../../modules/post";

describe("Logout", () => {

    it("renders correctly", () => {
		const tree = renderer
        .create(
				<Provider store={store}>
					<MemoryRouter>
						<CreatePost/>
					</MemoryRouter>
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});

});