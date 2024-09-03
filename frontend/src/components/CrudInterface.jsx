import { useState } from 'react';

const CrudInterface = ({ entity }) => {
	const [id, setId] = useState('');
	const [formData, setFormData] = useState({});
	const [result, setResult] = useState(null);

	const handleInputChange = (e) => {
		setFormData({ ...formData, [e.target.name]: e.target.value });
	};

	const handleSubmit = async (operation) => {
		const baseUrl = 'https://traindemo-latest.onrender.com/api/v1';
		let url = `${baseUrl}/${entity}`;
		let method = 'GET';

		switch (operation) {
			case 'create':
				method = 'POST';
				break;
			case 'read':
			case 'delete':
				url += `/${id}`;
				method = operation === 'delete' ? 'DELETE' : 'GET';
				break;
			case 'update':
				url += `/${id}`;
				method = 'PUT';
				break;
			default:
				break;
		}

		try {
			const response = await fetch(url, {
				method,
				headers: {
					'Content-Type': 'application/json',
				},
				body: ['create', 'update'].includes(operation) ? JSON.stringify(formData) : undefined,
			});
			const data = await response.json();
			setResult(data);
		} catch (error) {
			setResult({ error: error.message });
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
			<div className="mb-3">
				<input
					type="text"
					className="form-control"
					placeholder="JSON data (for Create, Update)"
					name="jsonData"
					onChange={handleInputChange}
				/>
			</div>
			<div className="btn-group w-100 mb-3" role="group">
				<button className="btn btn-primary" onClick={() => handleSubmit('create')}>Create</button>
				<button className="btn btn-info" onClick={() => handleSubmit('read')}>Read</button>
				<button className="btn btn-warning" onClick={() => handleSubmit('update')}>Update</button>
				<button className="btn btn-danger" onClick={() => handleSubmit('delete')}>Delete</button>
			</div>
			{result && (
				<div className="mt-3">
					<h4>Result:</h4>
					<pre>{JSON.stringify(result, null, 2)}</pre>
				</div>
			)}
		</div>
	);
};

export default CrudInterface;
