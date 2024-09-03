import { Link } from 'react-router-dom';
import { useAuth } from './AuthContext';

const Header = ({ projectName }) => {
	const { user, logout } = useAuth();

	return (
		<header>
			<nav className="navbar navbar-expand-lg navbar-light bg-light">
				<div className="container">
					<Link to="/" className="navbar-brand">{projectName}</Link>
					<button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
						<span className="navbar-toggler-icon"></span>
					</button>
					<div className="collapse navbar-collapse justify-content-end" id="navbarNav">
						<ul className="navbar-nav">
							{user ? (
								<>
									<li className="nav-item">
										<span className="nav-link">Welcome, {user.email}</span>
									</li>
									<li className="nav-item">
										<Link to="/my-bookings" className="nav-link">My Bookings</Link>
									</li>
									<li className="nav-item">
										<button onClick={logout} className="nav-link btn btn-link">Logout</button>
									</li>
								</>
							) : (
								<>
									<li className="nav-item">
										<Link to="/login" className="nav-link btn btn-outline-primary me-2">Login</Link>
									</li>
									<li className="nav-item">
										<Link to="/register" className="nav-link btn btn-primary">Sign Up</Link>
									</li>
								</>
							)}
							<li className="nav-item">
								<a href="https://github.com/v-dav" className="nav-link" target="_blank" rel="noopener noreferrer">
									<i className="fab fa-github"></i>
								</a>
							</li>
							<li className="nav-item">
								<a href="https://traindemo-latest.onrender.com/api/v1/swagger-ui/index.html" className="nav-link">Docs</a>
							</li>
						</ul>
					</div>
				</div>
			</nav>
		</header>
	);
};

export default Header;
