import {
    init,
    setReauthenticationCallback,
    fetchConfig,
    appFetch,
    handle4xxResponse,
    handleResponse,
    handleOkResponse
  } from "../../backend/appFetch";
  import NetworkError from "../../backend/NetworkError";
  import { config } from "../../config/constants";
  
  global.fetch = jest.fn();
  
  describe("appFetch", () => {
    beforeEach(() => {
      init(null);
      setReauthenticationCallback(null);
      sessionStorage.clear();
    });
  
    test("fetchConfig handles requests with service token", () => {
      sessionStorage.setItem(config.SERVICE_TOKEN_NAME, "mockedToken");
      const method = "GET";
      const body = { key: "value" };
  
      const result = fetchConfig(method, body);
  
      expect(result.method).toBe(method);
      expect(result.headers.Authorization).toBe("Bearer mockedToken");
      expect(result.body).toBe(JSON.stringify(body));
    });
  
    test("fetchConfig handles requests without service token", () => {
      const method = "GET";
      const body = { key: "value" };
  
      const result = fetchConfig(method, body);
  
      expect(result.method).toBe(method);
      expect(JSON.stringify(result.headers)).toBe(JSON.stringify({"Content-Type": "application/json"}));
      expect(result.body).toBe(JSON.stringify(body));
    });
  
    test("appFetch handles successful response", async () => {
      const path = "/example";
      const options = { method: "GET" };
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
  
      fetch.mockResolvedValue({
        ok: true,
        status: 200,
        json: jest.fn().mockResolvedValue({ success: true }),
        headers: { get: () => "application/json" }, 
      });
  
      await appFetch(path, options, onSuccess, onErrors);
  
      expect(onSuccess).toHaveBeenCalledWith({ success: true });
      expect(onErrors).not.toHaveBeenCalled();
    });
  
    test("appFetch handles 4xx response", async () => {
      const path = "/example";
      const options = { method: "GET" };
      const onSuccess = jest.fn();
      const onErrors = jest.fn();

      fetch.mockResolvedValue({
        ok: false,
        status: 401,
        json: jest.fn().mockResolvedValue({ error: "Unauthorized" }),
      });
  
      setReauthenticationCallback(jest.fn());
  
      await appFetch(path, options, onSuccess, onErrors);
  
      expect(onSuccess).not.toHaveBeenCalled();
      expect(onErrors).not.toHaveBeenCalled();
    });
  
    test("appFetch handles network error", async () => {
      const path = "/example";
      const options = { method: "GET" };
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
  
      fetch.mockRejectedValue(new Error("Network error"));
  
      init(jest.fn());
  
      await appFetch(path, options, onSuccess, onErrors);
  
      expect(onSuccess).not.toHaveBeenCalled();
      expect(onErrors).not.toHaveBeenCalled();
    });

    test("handleOkResponse handles response without onSuccess", () => {
      const response = {
        ok: true,
        status: 204,
        json: jest.fn().mockResolvedValue({ success: true }),
        headers: { get: () => "application/json" },
      };
    
      const result = handleOkResponse(response);
    
      expect(result).toBe(true);
    });

    test("handleOkResponse handles response with status 204", () => {
      const response = {
        ok: true,
        status: 204,
        headers: { get: () => "application/json" },
      };
      const onSuccess = jest.fn();
    
      const result = handleOkResponse(response, onSuccess);
    
      expect(onSuccess).toHaveBeenCalled();
      expect(result).toBe(true);
    });

    test("handleOkResponse handles response with JSON payload", async () => {
      const jsonPayload = { success: true };
      const response = {
        ok: true,
        json: jest.fn().mockResolvedValue(jsonPayload),
        headers: { get: () => "application/json" },
      };
      const onSuccess = jest.fn();
    
      const result = await handleOkResponse(response, onSuccess);
    
      expect(onSuccess).toHaveBeenCalledWith(jsonPayload);
      expect(result).toBe(true);
    });

    test("handle4xxResponse throws NetworkError for non-JSON response", async () => {
      const response = {
        status: 401,
        json: jest.fn(),
      };

      response.headers = new Headers();
      response.headers.set("content-type", "text/html");
    
      expect(() => handle4xxResponse(response)).toThrow(NetworkError);
    });

    test("handle4xxResponse calls onErrors with payload for 4xx response", async () => {
      const errorPayload = { globalError: "Some error" };
      const response = {
        status: 400,
        json: jest.fn().mockResolvedValue(errorPayload),
      };
      const onErrors = jest.fn();

      response.headers = new Headers();
      response.headers.set("content-type", "application/json");
    
      await handle4xxResponse(response, onErrors);
    
      expect(onErrors).toHaveBeenCalledWith(errorPayload);
    });

    test("handle4xxResponse calls onErrors with payload for weird code response", async () => {
      const errorPayload = { globalError: "Some error" };
      const response = {
        status: 300,
        json: jest.fn().mockResolvedValue(errorPayload),
      };
      const onErrors = jest.fn();

      response.headers = new Headers();
      response.headers.set("content-type", "application/json");
    
      expect(await handle4xxResponse(response, onErrors)).toBeFalsy()
    });

    test("fetchConfig returns config without service token", () => {
      const method = "GET";
      const body = { key: "value" };
    
      const result = fetchConfig(method, body);
    
      expect(result.method).toBe(method);
      expect(result.body).toBe(JSON.stringify(body));
    });

    test("fetchConfig returns config with service token", () => {
      sessionStorage.setItem(config.SERVICE_TOKEN_NAME, "mockedToken");
      const method = "GET";
      const body = { key: "value" };
    
      const result = fetchConfig(method, body);
    
      expect(result.method).toBe(method);
      expect(result.headers.Authorization).toBe("Bearer mockedToken");
      expect(result.body).toBe(JSON.stringify(body));
    });

    test("fetchConfig returns config with FormData", () => {
      const method = "POST";
      const body = new FormData();
    
      const result = fetchConfig(method, body);
    
      expect(result.method).toBe(method);
      expect(result.body).toBe(body);
    });

    test("fetchConfig returns config with JSON stringified body", () => {
      const method = "POST";
      const body = { key: "value" };
    
      const result = fetchConfig(method, body);
    
      expect(result.method).toBe(method);
      expect(result.body).toBe(JSON.stringify(body));
    });

    test("appFetch handles network error", async () => {
      const path = "/example";
      const options = { method: "GET" };
      const onSuccess = jest.fn();
      const onErrors = jest.fn();
    
      fetch.mockRejectedValue(new Error("Network error"));
    
      init(jest.fn());
    
      await appFetch(path, options, onSuccess, onErrors);
    
      expect(onSuccess).not.toHaveBeenCalled();
      expect(onErrors).not.toHaveBeenCalled();
    });

    test('handleOkResponse should handle successful blob response', async () => {
      const response = {
        ok: true,
        status: 200,
        blob: jest.fn(() => Promise.resolve(new Blob(['some blob data']))),
      };

      response.headers = new Headers();
      response.headers.set("content-type", "application/octet-stream");

      const onSuccess = jest.fn();
  
      await handleOkResponse(response, onSuccess);
  
      expect(onSuccess).toHaveBeenCalledWith(new Blob(['some blob data']));
    });
    
  });