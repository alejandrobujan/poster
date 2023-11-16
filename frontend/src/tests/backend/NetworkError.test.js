import { NetworkError } from "../../backend";
import '@testing-library/jest-dom/extend-expect';

describe("NetWorkError suite test", () =>{

    it("creates right", async () => {
        const error = new NetworkError();
        expect(error.message).toBe("Network error");
    })
})