import * as appFetchModule from "../../backend/appFetch";
import * as catalogService from "../../backend/catalogService";

jest.mock("../../backend/appFetch");

describe("catalogService", () => {
    beforeEach(() => {
      jest.clearAllMocks();
    });
  
    describe("findPosts", () => {
      it("should call appFetch with the correct parameters on successful findPosts", async () => {
        const onSuccess = jest.fn();
        const params = { category: "mockCategory", sortBy: "mockSortBy" };
  
        appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
          successCallback();
        });
  
        await catalogService.findPosts(params, onSuccess);
  
        expect(appFetchModule.appFetch).toHaveBeenCalledWith(
          "/catalog/feed",
          undefined,
          expect.any(Function)
        );
  
        expect(onSuccess).toHaveBeenCalled();
      });
    });
  
    describe("findAllCategories", () => {
      it("should call appFetch with the correct parameters on successful findAllCategories", async () => {
        const onSuccess = jest.fn();
  
        appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
          successCallback();
        });
  
        await catalogService.findAllCategories(onSuccess);
  
        expect(appFetchModule.appFetch).toHaveBeenCalledWith(
          "/catalog/categories",
          undefined,
          expect.any(Function)
        );
  
        expect(onSuccess).toHaveBeenCalled();
      });
    });
  
    describe("findPostById", () => {
      it("should call appFetch with the correct parameters on successful findPostById", async () => {
        const onSuccess = jest.fn();
        const postId = 1;
  
        appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
          successCallback();
        });
  
        await catalogService.findPostById(postId, onSuccess);
  
        expect(appFetchModule.appFetch).toHaveBeenCalledWith(
          `/catalog/postDetail/${postId}`,
          undefined,
          expect.any(Function)
        );
  
        expect(onSuccess).toHaveBeenCalled();
      });
    });
  });