import { waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import { App } from "../../../modules/app";
import store from "../../../store";
import { stompClient } from "../../../backend/webSocketService";
import { TextEncoder, TextDecoder } from 'util';

describe("App", () => {
    Object.assign(global, { TextDecoder, TextEncoder });

    it("renders correctly", () => {
        const tree = renderer
            .create(
                <Provider store={store}>
                    <MemoryRouter>
                        <App/>
                    </MemoryRouter>
                </Provider>
            )
            .toJSON();
        expect(tree).toMatchSnapshot();
    });

    it("sets stompClient correctly", async () => {
        renderer
            .create(
                <Provider store={store}>
                    <MemoryRouter>
                        <App/>
                    </MemoryRouter>
                </Provider>
            );

        await waitFor(() => {
            expect(stompClient).not.toBeUndefined();
        });
    });
 
});
