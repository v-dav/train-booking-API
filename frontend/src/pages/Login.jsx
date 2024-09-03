import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../components/AuthContext';

const Login = () => {
	const [email, setEmail] = useState('');
	const [password, setPassword] = useState('');
	const [error, setError] = useState('');
	const navigate = useNavigate();
	const { login } = useAuth();

	const handleSubmit = async (e) => {
		e.preventDefault();
		setError('');
		try {
			const response = await fetch('https://traindemo-latest.onrender.com/api/v1/auth/login', {
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				body: JSON.stringify({ email, password }),
			});
			const data = await response.json();
			if (response.ok) {
				login({ email });
				navigate('/');
			} else {
				setError(data.message || 'Login failed');
			}
		} catch (err) {
			setError('An error occurred. Please try again.');
		}
	};

	const handleGoogleLogin = () => {
		window.location.href = 'https://traindemo-latest.onrender.com/api/v1/oauth2/authorization/google';
	};

	return (
		<div className="container my-5">
			<h2 className="text-center mb-4">Login</h2>
			{error && <div className="alert alert-danger">{error}</div>}
			<form onSubmit={handleSubmit} className="mx-auto" style={{ maxWidth: '300px' }}>
				<div className="mb-3">
					<input
						type="email"
						className="form-control"
						placeholder="Email"
						value={email}
						onChange={(e) => setEmail(e.target.value)}
						required
					/>
				</div>
				<div className="mb-3">
					<input
						type="password"
						className="form-control"
						placeholder="Password"
						value={password}
						onChange={(e) => setPassword(e.target.value)}
						required
					/>
				</div>
				<button type="submit" className="btn btn-primary w-100">Login</button>
			</form>
		</div>
	);
};

export default Login;
