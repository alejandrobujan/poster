import React from "react";
import { render, screen, fireEvent } from '@testing-library/react';
import renderer from "react-test-renderer";
import { Provider } from 'react-redux';
import { MemoryRouter } from "react-router-dom";
import configureMockStore from 'redux-mock-store';
import thunk from 'redux-thunk';
import PostDetails from '../../modules/post/components/PostDetails';


describe('PostDetails Component', () => {
	const middlewares = [thunk];
    const mockStore = configureMockStore(middlewares);
    let store;
    
    const postMock = {
    users: {
        user: {
            serviceToken: "923u2344823903480923",
            userDto: {
                id: 1,
                userName: "Pepe",
                password: "Pepe",
                firstName: "Pepe",
                lastName: "Pepe",
                email: "Pepe@pepe.com",
                avatar: null
            }
        }
    },
    post:{
        post: {
            id: 1,
            title: "Coupon",
            description: "Buena oferta",
            url: "http://cupon",
            price: 10,
            categoryDto: {
                id: 1,
                name: "Belleza"
            },
            userSummaryDto: {
                id: 1,
                userName: "Pepe",
                firstName: "Pepe",
                lastName: "Pepe",
                avatar: null
            },
            images: [],
            creationDate: 1384723847238,
            positiveRatings: 0,
            negativeRatings: 0,
            expired: false,
            validationDate:null,
            type: "Coupon",
            properties: {
                code: "12345"
            }
        }
    },
}

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
