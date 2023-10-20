export const fileToBase64 = file => {
	return new Promise((resolve, reject) => {
		const reader = new FileReader();

		reader.onload = () => {
			resolve(reader.result.replace(/^data:image\/(png|jpeg|jpg|gif);base64,/, ''));
		};

		reader.onerror = (error) => {
			reject(error);
		};

		reader.readAsDataURL(file);
	});
}

export const isImage = file => {
	return file.type.split('/')[0] === 'image';
}

export const getDate = millis => {
    const date = new Date(millis);
    return `${date.toLocaleDateString()} ${date.toLocaleTimeString()}`;
}