import { useState } from 'react';
import CrudInterface from '../components/CrudInterface';
import SearchBar from '../components/SearchBar';

const Home = ({ projectName }) => {
	const [activeEntity, setActiveEntity] = useState('trains');

	return (
		<div className="container mt-4">
			<h2 className="text-center mb-4">{projectName} API Capabilities</h2>

			<div className="btn-group w-100 mb-4" role="group">
				<button
					type="button"
					className={`btn btn-outline-primary ${activeEntity === 'trains' ? 'active' : ''}`}
					onClick={() => setActiveEntity('trains')}
				>
					Trains
				</button>
				<button
					type="button"
					className={`btn btn-outline-primary ${activeEntity === 'users' ? 'active' : ''}`}
					onClick={() => setActiveEntity('users')}
				>
					Users
				</button>
				<button
					type="button"
					className={`btn btn-outline-primary ${activeEntity === 'bookings' ? 'active' : ''}`}
					onClick={() => setActiveEntity('bookings')}
				>
					Bookings
				</button>
			</div>

			<CrudInterface entity={activeEntity} />

			<div className="bg-light py-3 mt-4">
				<div className="container">
					<h4 className="text-center mb-3">Why Choose {projectName}?</h4>
					<div className="row justify-content-center">
						<div className="col-md-3 mb-2 mb-md-0">
							<div className="text-center">
								<i className="fas fa-ticket-alt fa-lg mb-2 text-primary"></i>
								<h6>Easy Booking</h6>
								<p className="small mb-0">Book your tickets with just a few clicks.</p>
							</div>
						</div>
						<div className="col-md-3 mb-2 mb-md-0">
							<div className="text-center">
								<i className="fas fa-tags fa-lg mb-2 text-primary"></i>
								<h6>Best Prices</h6>
								<p className="small mb-0">We guarantee the best prices for your journeys.</p>
							</div>
						</div>
						<div className="col-md-3">
							<div className="text-center">
								<i className="fas fa-headset fa-lg mb-2 text-primary"></i>
								<h6>24/7 Support</h6>
								<p className="small mb-0">Our team is always ready to assist you.</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
};

export default Home;
