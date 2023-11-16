import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';

import configureMockStore from 'redux-mock-store'
import thunk from 'redux-thunk'

import renderer from "react-test-renderer";
import { notificationMock } from "../../state/Notification.mock";

import { Notification } from "../../../modules/notification";

describe("Notification", () => {
	
	const middlewares = [thunk];
    const mockStore = configureMockStore(middlewares);
    let store;
    store = mockStore(notificationMock);
    
    const notification = {
		id: 1,
		text: "Hola",
		viewed: false,
		creationDate: null,
		postId: 1,
		notifierUserDto: {
			avatar: null
		}
	}
    
    it("renders correctly", () => {
		const tree = renderer
        .create(
				<Provider store={store}>
					<MemoryRouter>
						<Notification id={notification.id} text={notification.text} viewed={notification.viewed} creationDate={notification.creationDate} postId={notification.postId} notifierUserDto={notification.notifierUserDto}/>
					</MemoryRouter>
				</Provider>
			)
			.toJSON();
		expect(tree).toMatchSnapshot();
	});
	
	 it("basic structure notification", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<Notification id={notification.id} text={notification.text} viewed={notification.viewed} creationDate={notification.creationDate} postId={notification.postId} notifierUserDto={notification.notifierUserDto}/>
				</MemoryRouter>
			</Provider>
		);

		const notificationButton = screen.getByTestId('NotificationButton');

        const avatar = screen.getByTestId('Avatar');

        const date = screen.getByTestId('Date');
        
        const warningNotification = screen.getByTestId('WarningNotification');
        
        const notificationText = screen.getByTestId('NotificationText');

	});
	
	it("click notification button", async () => {
		render(
			<Provider store={store}>
				<MemoryRouter>
					<Notification id={notification.id} text={notification.text} viewed={notification.viewed} creationDate={notification.creationDate} postId={notification.postId} notifierUserDto={notification.notifierUserDto}/>
				</MemoryRouter>
			</Provider>
		);

		const notificationButton = screen.getByTestId('NotificationButton');

        fireEvent.click(notificationButton);

	});
    
});