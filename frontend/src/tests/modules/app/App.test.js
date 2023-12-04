import { fireEvent, render, screen, waitFor } from "@testing-library/react";
import React from "react";
import { MemoryRouter } from "react-router-dom";
import {Provider} from 'react-redux';
import renderer from "react-test-renderer";
import { App } from "../../../modules/app";
import store from "../../../store";

describe("App", () => {
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
 
});