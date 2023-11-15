import React from "react";
import { render, screen, fireEvent } from '@testing-library/react';
import renderer from "react-test-renderer";
import { Provider } from 'react-redux';
import { MemoryRouter } from "react-router-dom";
import configureMockStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import PostDetails from '../../modules/post/components/PostDetails';
import { postMock } from "../state/Post.mock";

describe('PostDetails Component', () => {
	const middlewares = [thunk];
    const mockStore = configureMockStore(middlewares);
    let store;
	store = mockStore(postMock);
    
  it("renders correctly", () => {
		const tree = renderer
        .create(
				<Provider store={store}>
					<MemoryRouter>
						<PostDetails/>
					</MemoryRouter>
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});  
	
	it("click mark post as valid", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
                    <PostDetails />
				</MemoryRouter>
			</Provider>
		);

        const markAsValidButton = screen.getByTestId('MarkAsValidButton');

        fireEvent.click(markAsValidButton);      
	});
	
});
