import * as appFetchModule from "../../backend/appFetch";
import * as userService from "../../backend/userService";

jest.mock("../../backend/appFetch");

describe("userService", () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe("login", () => {
    it("should call appFetch with the correct parameters on successful login", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const reauthenticationCallback = jest.fn();

      const authenticatedUser = { serviceToken: "mockedServiceToken" };

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        successCallback(authenticatedUser);
      });

      await userService.login("mockUserName", "mockPassword", onSuccess, onErrors, reauthenticationCallback);

      expect(appFetchModule.appFetch).toHaveBeenCalledWith(
        "/users/login",
        undefined,
        expect.any(Function),
        expect.any(Function)
      );

      expect(appFetchModule.setReauthenticationCallback).toHaveBeenCalledWith(reauthenticationCallback);
      expect(onSuccess).toHaveBeenCalledWith(authenticatedUser);
      expect(onErrors).not.toHaveBeenCalled();
    });

    it("should call onErrors callback on login failure", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const reauthenticationCallback = jest.fn();

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        errorCallback();
      });

      await userService.login("mockUserName", "mockPassword", onSuccess, onErrors, reauthenticationCallback);

      expect(onErrors).toHaveBeenCalled();
      expect(onSuccess).not.toHaveBeenCalled();
    });
  });

  describe("tryLoginFromServiceToken", () => {
    it("should call onSuccess callback on tryLoginFromServiceToken success", async () => {
      const onSuccess = jest.fn();
      const reauthenticationCallback = jest.fn();
      const authenticatedUser = { serviceToken: "mockedServiceToken" };
  
      appFetchModule.getServiceToken.mockReturnValueOnce("mockedServiceToken");
      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
        successCallback(authenticatedUser);
      });
  
      await userService.tryLoginFromServiceToken(onSuccess, reauthenticationCallback);
  
      expect(appFetchModule.appFetch).toHaveBeenCalledWith(
        "/users/loginFromServiceToken",
        undefined,
        expect.any(Function),
        expect.any(Function)
      );
  
      expect(appFetchModule.setReauthenticationCallback).toHaveBeenCalledWith(reauthenticationCallback);
      expect(onSuccess).toHaveBeenCalledWith(authenticatedUser);
    });
  
    it("should not call onSuccess callback on tryLoginFromServiceToken failure", async () => {
      const onSuccess = jest.fn();
      const reauthenticationCallback = jest.fn();
  
      appFetchModule.getServiceToken.mockReturnValueOnce("mockedServiceToken");
      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        errorCallback(); // Simulating failure
      });
  
      await userService.tryLoginFromServiceToken(onSuccess, reauthenticationCallback);
  
      expect(onSuccess).not.toHaveBeenCalled();
    });
  
    it("should call onSuccess callback when no serviceToken is available", async () => {
      const onSuccess = jest.fn();
      const reauthenticationCallback = jest.fn();
  
      appFetchModule.getServiceToken.mockReturnValueOnce(null);
  
      await userService.tryLoginFromServiceToken(onSuccess, reauthenticationCallback);
  
      expect(onSuccess).toHaveBeenCalled();
    });
  });  

  describe("signUp", () => {
    it("should call appFetch with the correct parameters on successful signUp", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const reauthenticationCallback = jest.fn();
      const user = { username: "mockUserName", password: "mockPassword" };
      const authenticatedUser = { serviceToken: "mockedServiceToken" };

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
        successCallback(authenticatedUser);
      });

      await userService.signUp(user, onSuccess, onErrors, reauthenticationCallback);

      expect(appFetchModule.appFetch).toHaveBeenCalledWith(
        "/users/signUp",
        undefined,
        expect.any(Function),
        expect.any(Function)
      );

      expect(appFetchModule.setReauthenticationCallback).toHaveBeenCalledWith(reauthenticationCallback);
      expect(onSuccess).toHaveBeenCalledWith(authenticatedUser);
      expect(onErrors).not.toHaveBeenCalled();
    });

    it("should call onErrors callback on signUp failure", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const reauthenticationCallback = jest.fn();
      const user = { username: "mockUserName", password: "mockPassword" };

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        errorCallback();
      });

      await userService.signUp(user, onSuccess, onErrors, reauthenticationCallback);

      expect(onErrors).toHaveBeenCalled();
      expect(onSuccess).not.toHaveBeenCalled();
    });
  });

  describe("logout", () => {
    it("should call removeServiceToken on logout", () => {
      userService.logout();
      expect(appFetchModule.removeServiceToken).toHaveBeenCalled();
    });
  });

  describe("updateProfile", () => {
    it("should call appFetch with the correct parameters on successful updateProfile", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const user = { id: 1, username: "mockUserName" };

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
        successCallback();
      });

      await userService.updateProfile(user, onSuccess, onErrors);

      expect(appFetchModule.appFetch).toHaveBeenCalledWith(
        `/users/${user.id}`,
        undefined,
        expect.any(Function),
        expect.any(Function)
      );

      expect(onSuccess).toHaveBeenCalled();
      expect(onErrors).not.toHaveBeenCalled();
    });

    it("should call onErrors callback on updateProfile failure", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const user = { id: 1, username: "mockUserName" };

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        errorCallback();
      });

      await userService.updateProfile(user, onSuccess, onErrors);

      expect(onErrors).toHaveBeenCalled();
      expect(onSuccess).not.toHaveBeenCalled();
    });
  });

  describe("changePassword", () => {
    it("should call appFetch with the correct parameters on successful changePassword", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const id = 1;
      const oldPassword = "oldPassword";
      const newPassword = "newPassword";

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback) => {
        successCallback();
      });

      await userService.changePassword(id, oldPassword, newPassword, onSuccess, onErrors);

      expect(appFetchModule.appFetch).toHaveBeenCalledWith(
        `/users/${id}/changePassword`,
        undefined,
        expect.any(Function),
        expect.any(Function)
      );

      expect(onSuccess).toHaveBeenCalled();
      expect(onErrors).not.toHaveBeenCalled();
    });

    it("should call onErrors callback on changePassword failure", async () => {
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
      const id = 1;
      const oldPassword = "oldPassword";
      const newPassword = "newPassword";

      appFetchModule.appFetch.mockImplementationOnce((url, config, successCallback, errorCallback) => {
        errorCallback();
      });

      await userService.changePassword(id, oldPassword, newPassword, onSuccess, onErrors);

      expect(onErrors).toHaveBeenCalled();
      expect(onSuccess).not.toHaveBeenCalled();
    });
  });
});
