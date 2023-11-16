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
	const locale = 'es-ES';
	const options = {timeZone: "Europe/Madrid"}
    const date = new Date(millis);
    return `${date.toLocaleDateString(locale, options)} ${date.toLocaleTimeString(locale, options)}`;
}

export const formatDateForInput = millis => {
	const locale = 'es-ES';
	const options = {timeZone: "Europe/Madrid"}
	const date = new Date(millis);
	const dateString = `${date.toLocaleDateString(locale, options)}-${date.toLocaleTimeString(locale, options)}`
	const [datePart, timePart] = dateString.split('-');
	const [day, month, year] = datePart.split('/');
  	const [hours, minutes] = timePart.split(':');
	const monthStr = `${month.padStart(2, '0')}`;
	const dayStr = `${day.padStart(2, '0')}`;
	const hoursStr = `${hours.padStart(2, '0')}`;
	const minutesStr = `${minutes.padStart(2, '0')}`;
	
	return `${year}-${monthStr}-${dayStr}T${hoursStr}:${minutesStr}`;

}


export const parseInputDate = dateString => {
  const [datePart, timePart] = dateString.split('T');
  const [year, month, day] = datePart.split('-');
  const [hours, minutes] = timePart.split(':');

  return new Date(year, month - 1, day, hours, minutes).getTime();
}
