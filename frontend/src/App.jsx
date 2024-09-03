import { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import Home from './pages/Home';
import Login from './pages/Login';
import Register from './pages/Register';
import MyBookings from './pages/MyBookings';
import ProjectNameShowcase from './components/ProjectNameShowcase';
import { AuthProvider } from './components/AuthContext';
import './styles/App.css';

const App = () => {
	const [projectName, setProjectName] = useState(sessionStorage.getItem('selectedProjectName') || '');

	useEffect(() => {
		if (projectName) {
			sessionStorage.setItem('selectedProjectName', projectName);
		}
	}, [projectName]);

	const handleSelectName = (name) => {
		setProjectName(name);
	};

	return (
		<AuthProvider>
			<Router>
				{!projectName ? (
					<ProjectNameShowcase onSelectName={handleSelectName} />
				) : (
					<div className="d-flex flex-column min-vh-100">
						<Header projectName={projectName} />
						<main className="flex-grow-1">
							<Routes>
								<Route path="/" element={<Home projectName={projectName} />} />
								<Route path="/login" element={<Login />} />
								<Route path="/register" element={<Register />} />
								<Route path="/my-bookings" element={<MyBookings />} />
								<Route path="*" element={<Navigate to="/" replace />} />
							</Routes>
						</main>
						<Footer />
					</div>
				)}
			</Router>
		</AuthProvider>
	);
};

export default App;
