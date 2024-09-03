import React, { useState } from 'react';

const CrudInterface = ({ entity }) => {
	const [id, setId] = useState('');
	const [jsonInput, setJsonInput] = useState('');
	const [result, setResult] = useState(null);
	const [error, setError] = useState(null);
	const [bookingData, setBookingData] = useState({ userId: '', trainId: '', seatNumber: '' });

	const handleJsonInputChange = (e) => {
		setJsonInput(e.target.value);
		setError(null);
	};

	const handleBookingInputChange = (e) => {
		setBookingData({ ...bookingData, [e.target.name]: e.target.value });
		setError(null);
	};

	const validateAndParseJson = (jsonString) => {
		if (!jsonString.trim()) {
			return {}; // Return an empty object if the input is empty
		}
		try {
			return JSON.parse(jsonString);
		} catch (error) {
			throw new Error('Invalid JSON input. Please check your JSON format.');
		}
	};

	const handleSubmit = async (operation) => {
		setError(null);
		setResult(null);

		const baseUrl = 'https://traindemo-latest.onrender.com/api/v1';
		let url = `${baseUrl}/${entity}`;
		let method = 'GET';
		let body;

		try {
			switch (operation) {
				case 'create':
					method = 'POST';
					if (entity === 'booking') {
						url += `?userId=${bookingData.userId}&trainId=${bookingData.trainId}&seatNumber=${bookingData.seatNumber}`;
					} else {
						body = validateAndParseJson(jsonInput);
					}
					break;
				case 'read':
					if (!id) throw new Error('ID is required for Read operation');
					url += `/${id}`;
					break;
				case 'update':
					if (!id) throw new Error('ID is required for Update operation');
					url += `/${id}`;
					method = 'PUT';
					body = validateAndParseJson(jsonInput);
					break;
				case 'delete':
					if (!id) throw new Error('ID is required for Delete operation');
					url += `/${id}`;
					method = 'DELETE';
					break;
				case 'getAll':
					break;
				default:
					throw new Error('Invalid operation');
			}

			console.log('Sending request:', { url, method, body });

			const response = await fetch(url, {
				method,
				headers: {
					'Content-Type': 'application/json',
				},
				credentials: 'include',
				body: body ? JSON.stringify(body) : undefined,
			});

			console.log('Response status:', response.status);

			if (!response.ok) {
				const errorText = await response.text();
				throw new Error(errorText || `HTTP error! status: ${response.status}`);
			}

			if (method === 'DELETE') {
				setResult({ message: 'Delete operation successful' });
			} else {
				const contentType = response.headers.get("content-type");
				if (contentType && contentType.indexOf("application/json") !== -1) {
					const data = await response.json();
					setResult(data);
				} else {
					const text = await response.text();
					setResult({ message: text });
				}
			}
		} catch (error) {
			console.error('Operation error:', error);
			setError(error.message);
		}
	};

	return (
		<div>
			<h3 className="mb-3">Manage {entity}</h3>
			<div className="mb-3">
				<input
					type="text"
					className="form-control"
					placeholder="ID (for Read, Update, Delete)"
					value={id}
					onChange={(e) => setId(e.target.value)}
				/>
			</div>
			{entity === 'booking' && (
				<div className="mb-3">
					<input
						type="text"
						className="form-control mb-2"
						placeholder="User ID param for Create"
						name="userId"
						value={bookingData.userId}
						onChange={handleBookingInputChange}
					/>
					<input
						type="text"
						className="form-control mb-2"
						placeholder="Train ID param for Create"
						name="trainId"
						value={bookingData.trainId}
						onChange={handleBookingInputChange}
					/>
					<input
						type="text"
						className="form-control"
						placeholder="Seat Number param for Create"
						name="seatNumber"
						value={bookingData.seatNumber}
						onChange={handleBookingInputChange}
					/>
				</div>
			)}
			{entity !== 'booking' && (
				<div className="mb-3">
					<textarea
						className="form-control"
						placeholder="JSON data (for Create, Update)"
						value={jsonInput}
						onChange={handleJsonInputChange}
						rows="5"
					/>
				</div>
			)}
			<div className="btn-group w-100 mb-3" role="group">
				<button className="btn btn-secondary" onClick={() => handleSubmit('getAll')}>Get All</button>
				<button className="btn btn-info" onClick={() => handleSubmit('read')}>Get One</button>
				<button className="btn btn-primary" onClick={() => handleSubmit('create')}>Create</button>
				<button className="btn btn-danger" onClick={() => handleSubmit('delete')}>Delete</button>
			</div>
			{error && (
				<div className="alert alert-danger mt-3" role="alert">
					{error}
				</div>
			)}
			{result && (
				<div className="mt-3">
					<h4>Result:</h4>
					<pre className="bg-light p-3 rounded">{JSON.stringify(result, null, 2)}</pre>
				</div>
			)}
		</div>
	);
};

export default CrudInterface;
