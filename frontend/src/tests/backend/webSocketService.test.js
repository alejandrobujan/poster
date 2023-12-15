import * as webSocketService from "../../backend/webSocketService";

describe('webSocketService', () => {
    describe('onReceive', () => {
    it('should call onUpdate with received data when message is valid', () => {
        const message = { body: JSON.stringify({ data: 'posts.newPost' }) };
        const onUpdate = jest.fn();

        webSocketService.onReceive(message, onUpdate);

        expect(onUpdate).toHaveBeenCalledWith('posts.newPost');
        expect(onUpdate).toHaveBeenCalledTimes(1);
    });

    it('should throw SyntaxError when message body is not valid JSON', () => {
        const message = { body: 'invalid JSON' };
        const onUpdate = jest.fn();

        expect(() => webSocketService.onReceive(message, onUpdate)).toThrow(SyntaxError);
    });

    it('should not call onUpdate when received data is not "posts.newPost"', () => {
        const message = { body: JSON.stringify({ data: 'other.data' }) };
        const onUpdate = jest.fn();

        webSocketService.onReceive(message, onUpdate);

        expect(onUpdate).not.toHaveBeenCalled();
    });

    it('should not call onUpdate when received data is missing', () => {
        const message = { body: JSON.stringify({}) };
        const onUpdate = jest.fn();

        webSocketService.onReceive(message, onUpdate);

        expect(onUpdate).not.toHaveBeenCalled();
    });

    it('should throw SyntaxError when message is empty', () => {
        const message = {};
        const onUpdate = jest.fn();

        expect(() => webSocketService.onReceive(message, onUpdate)).toThrow(SyntaxError);
    });
    });
});