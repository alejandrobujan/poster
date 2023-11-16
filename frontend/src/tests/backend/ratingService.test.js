import * as appFetchModule from "../../backend/appFetch";
import * as ratingService from "../../backend/ratingService";

jest.mock("../../backend/appFetch");

describe("ratingService", () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe("ratePostPositive", () => {
    it("should call appFetch with the correct parameters on successful rating", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const postId = 1;

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
        successCallback();
      });

      await ratingService.ratePostPositive(postId, onSuccess, onErrors);

      expect(appFetchModule.appFetch).toHaveBeenCalledWith(
        `/rating/post/${postId}/ratePositive`,
        undefined,
        expect.any(Function),
        expect.any(Function)
      );

      expect(onSuccess).toHaveBeenCalled();
      expect(onErrors).not.toHaveBeenCalled();
    });

    it("should call onErrors callback on rating failure", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const postId = 1;

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        errorCallback();
      });

      await ratingService.ratePostPositive(postId, onSuccess, onErrors);

      expect(onErrors).toHaveBeenCalled();
      expect(onSuccess).not.toHaveBeenCalled();
    });
  });

  describe("ratePostNegative", () => {
    it("should call appFetch with the correct parameters on successful rating", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const postId = 1;

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
        successCallback();
      });

      await ratingService.ratePostNegative(postId, onSuccess, onErrors);

      expect(appFetchModule.appFetch).toHaveBeenCalledWith(
        `/rating/post/${postId}/rateNegative`,
        undefined,
        expect.any(Function),
        expect.any(Function)
      );

      expect(onSuccess).toHaveBeenCalled();
      expect(onErrors).not.toHaveBeenCalled();
    });

    it("should call onErrors callback on rating failure", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const postId = 1;

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        errorCallback();
      });

      await ratingService.ratePostNegative(postId, onSuccess, onErrors);

      expect(onErrors).toHaveBeenCalled();
      expect(onSuccess).not.toHaveBeenCalled();
    });
  });
});
