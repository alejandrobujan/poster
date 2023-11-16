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

export const formatDateForInput = millis => {
	const date = new Date(millis);
	const year = date.getFullYear();
	const month = `${(date.getMonth() + 1).toString().padStart(2, '0')}`;
	const day = `${date.getDate().toString().padStart(2, '0')}`;
	const hours = `${date.getHours().toString().padStart(2, '0')}`;
	const minutes = `${date.getMinutes().toString().padStart(2, '0')}`;
	
	return `${year}-${month}-${day}T${hours}:${minutes}`;
}

export const parseInputDate = dateString => {
  const [datePart, timePart] = dateString.split('T');
  const [year, month, day] = datePart.split('-');
  const [hours, minutes] = timePart.split(':');

  return new Date(year, month - 1, day, hours, minutes).getTime();
}
