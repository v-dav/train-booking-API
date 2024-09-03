import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const projectNames = [
	'RailRover',
	'TrackMaster',
	'ChooChooConnect',
	'VoyageVault',
	'RailLink Pro',
	'TrainTrove',
	'JourneyJunction',
	'SwitchboardExpress',
	'LocomotionLogic',
	'RailReserve'
];

export default function ProjectNameShowcase({ onSelectName }) {
	const [currentName, setCurrentName] = useState(projectNames[0]);
	const navigate = useNavigate();

	useEffect(() => {
		const interval = setInterval(() => {
			setCurrentName(prevName => {
				const currentIndex = projectNames.indexOf(prevName);
				const nextIndex = (currentIndex + 1) % projectNames.length;
				return projectNames[nextIndex];
			});
		}, 2000);
		return () => clearInterval(interval);
	}, []);

	const handleNameSelect = () => {
		onSelectName(currentName);
		navigate('/');
	};

	return (
		<div className="d-flex flex-column justify-content-center align-items-center min-vh-100 bg-light">
			<h1 className="display-4 mb-4">Choose Your Project Name</h1>
			<div className="bg-white p-4 rounded shadow-lg">
				<p className="h2 text-primary mb-3" style={{ cursor: 'pointer' }} onClick={handleNameSelect}>
					{currentName}
				</p>
			</div>
		</div>
	);
}
