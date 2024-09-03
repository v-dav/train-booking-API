import { useState, useEffect } from 'react';

const ApiStatus = ({ baseUrl }) => {
	const [status, setStatus] = useState({ message: 'Loading...', color: 'black' });

	useEffect(() => {
		const fetchStatus = async () => {
			try {
				const response = await fetch(`${baseUrl}/status`);
				if (response.ok) {
					const data = await response.json();
					setStatus({ message: data.message || 'API is running', color: 'green' });
				} else {
					setStatus({ message: 'API is down or inaccessible', color: 'red' });
				}
			} catch (error) {
				setStatus({ message: 'Error fetching API status', color: 'red' });
			}
		};

		fetchStatus();
	}, [baseUrl]);

	return (
		<div className="api-status">
			<h4>API Status: <span style={{ color: status.color }}>{status.message}</span></h4>
		</div>
	);
};

export default ApiStatus;
