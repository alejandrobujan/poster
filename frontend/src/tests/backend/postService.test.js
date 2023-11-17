import * as appFetchModule from "../../backend/appFetch";
import * as postService from "../../backend/postService";

jest.mock("../../backend/appFetch");

describe("postService", () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe("createPost", () => {
    it("should call appFetch with the correct parameters on successful createPost", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
        successCallback();
      });

      await postService.createPost("mockTitle", "mockDescription", "mockUrl", 100, 1, [], "mockType", {}, Date.now, onSuccess, onErrors);

      expect(appFetchModule.appFetch).toHaveBeenCalledWith(
        "/posts/post",
        undefined,
        expect.any(Function),
        expect.any(Function)
      );

      expect(onSuccess).toHaveBeenCalled();
      expect(onErrors).not.toHaveBeenCalled();
    });

    it("should call onErrors callback on createPost failure", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        errorCallback();
      });

      await postService.createPost("mockTitle", "mockDescription", "mockUrl", 100, 1, [], "mockType", {}, Date.now, onSuccess, onErrors);

      expect(onErrors).toHaveBeenCalled();
      expect(onSuccess).not.toHaveBeenCalled();
    });
  });

  describe("deletePost", () => {
    it("should call appFetch with the correct parameters on successful deletePost", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
        successCallback();
      });

      await postService.deletePost(1, onSuccess, onErrors);

      expect(appFetchModule.appFetch).toHaveBeenCalledWith(
        "/posts/post/1",
        undefined,
        expect.any(Function),
        expect.any(Function)
      );

      expect(onSuccess).toHaveBeenCalled();
      expect(onErrors).not.toHaveBeenCalled();
    });

    it("should call onErrors callback on deletePost failure", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        errorCallback();
      });

      await postService.deletePost(1, onSuccess, onErrors);

      expect(onErrors).toHaveBeenCalled();
      expect(onSuccess).not.toHaveBeenCalled();
    });
  });

  describe("updatePost", () => {
    it("should call appFetch with the correct parameters on successful updatePost", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();

      const mockPost = { id: 1, title: "Updated Title", description: "Updated Description" };

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
        successCallback();
      });

      await postService.updatePost(mockPost, onSuccess, onErrors);

      expect(appFetchModule.appFetch).toHaveBeenCalledWith(
        `/posts/post/${mockPost.id}`,
        undefined,
        expect.any(Function),
        expect.any(Function)
      );

      expect(onSuccess).toHaveBeenCalled();
      expect(onErrors).not.toHaveBeenCalled();
    });

    it("should call onErrors callback on updatePost failure", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();

      const mockPost = { id: 1, title: "Updated Title", description: "Updated Description" };

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        errorCallback();
      });

      await postService.updatePost(mockPost, onSuccess, onErrors);

      expect(onErrors).toHaveBeenCalled();
      expect(onSuccess).not.toHaveBeenCalled();
    });
  });

  describe("maskPostAsExpired", () => {
    it("should call appFetch with the correct parameters on successful maskPostAsExpired", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
        successCallback();
      });

      await postService.maskPostAsExpired(1, onSuccess, onErrors);

      expect(appFetchModule.appFetch).toHaveBeenCalledWith(
        "/posts/post/1/markAsExpired",
        undefined,
        expect.any(Function),
        expect.any(Function)
      );

      expect(onSuccess).toHaveBeenCalled();
      expect(onErrors).not.toHaveBeenCalled();
    });

    it("should call onErrors callback on maskPostAsExpired failure", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        errorCallback();
      });

      await postService.maskPostAsExpired(1, onSuccess, onErrors);

      expect(onErrors).toHaveBeenCalled();
      expect(onSuccess).not.toHaveBeenCalled();
    });
  });
});
