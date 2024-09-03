const Footer = () => {
	return (
		<footer className="footer py-4">
			<div className="container">
				<div className="row align-items-center text-center text-lg-start">
					<div className="col-lg-4 mb-3 mb-lg-0">
						© 2024 Vladimir Davidov for Trainplanet
					</div>
					<div className="col-lg-4 mb-3 mb-lg-0">
						<div className="social-icons d-flex justify-content-center">
							<a href="https://linkedin.com/in/v-dav" target="_blank" rel="noopener noreferrer">
								<i className="fab fa-linkedin"></i>
							</a>
							<a href="https://github.com/v-dav" target="_blank" rel="noopener noreferrer">
								<i className="fab fa-github"></i>
							</a>
						</div>
					</div>
					<div className="col-lg-4">
						<a className="link-light text-decoration-none me-3" href="#!">Privacy Policy</a>
						<a className="link-light text-decoration-none" href="#!">Terms of Use</a>
					</div>
				</div>
			</div>
		</footer>
	);
};

export default Footer;
