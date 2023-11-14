import { fileToBase64, isImage, getDate } from '../../backend/utils';

describe('fileToBase64', () => {
  it('should resolve with base64 data when a file is provided', async () => {
    const mockFileReader = {
      onload: null,
      onerror: null,
      readAsDataURL: jest.fn(),
      result: 'mockBase64Data',
    };

    global.FileReader = jest.fn(() => mockFileReader);

    const mockFile = { type: 'image/png' };

    const promise = fileToBase64(mockFile);

    mockFileReader.onload();

    const result = await promise;

    expect(result).toBe('mockBase64Data');

    delete global.FileReader;
  });

  it('should reject with an error when file reading fails', async () => {
    const mockFileReader = {
      onload: null,
      onerror: null,
      readAsDataURL: jest.fn(),
    };

    global.FileReader = jest.fn(() => mockFileReader);

    const mockFile = { type: 'image/png' };

    const promise = fileToBase64(mockFile);

    const mockError = new Error('Mock file reading error');
    mockFileReader.onerror(mockError);

    await expect(promise).rejects.toEqual(mockError);

    delete global.FileReader;
  });
});

describe('isImage', () => {
  it('should return true for image files', () => {
    const imageFile = { type: 'image/png' };
    const result = isImage(imageFile);
    expect(result).toBe(true);
  });

  it('should return false for non-image files', () => {
    const nonImageFile = { type: 'text/plain' };
    const result = isImage(nonImageFile);
    expect(result).toBe(false);
  });
});

describe('getDate', () => {
  it('should return a formatted date string', () => {
    const mockDate = new Date(2023, 10, 13, 12, 34, 56); 
    global.Date = jest.fn(() => mockDate);

    const mockMillis = 123456789;

    const result = getDate(mockMillis);

    expect(result).toBe('11/13/2023 12:34:56 PM');

    delete global.Date;
  });
});
