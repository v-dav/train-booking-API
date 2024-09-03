import SearchBar from '../components/SearchBar';

const Home = ({projectName}) => {
	return (
		<>
			<SearchBar />
			<div className="bg-light py-4 mt-5">
				<div className="container">
					<h3 className="text-center mb-4">Why Choose {projectName}?</h3>
					<div className="row justify-content-center">
						<div className="col-md-3 mb-3 mb-md-0">
							<div className="text-center">
								<i className="fas fa-ticket-alt fa-2x mb-2 text-primary"></i>
								<h5>Easy Booking</h5>
								<p className="small">Book your tickets with just a few clicks.</p>
							</div>
						</div>
						<div className="col-md-3 mb-3 mb-md-0">
							<div className="text-center">
								<i className="fas fa-tags fa-2x mb-2 text-primary"></i>
								<h5>Best Prices</h5>
								<p className="small">We guarantee the best prices for your journeys.</p>
							</div>
						</div>
						<div className="col-md-3">
							<div className="text-center">
								<i className="fas fa-headset fa-2x mb-2 text-primary"></i>
								<h5>24/7 Support</h5>
								<p className="small">Our team is always ready to assist you.</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</>
	);
};

export default Home;
