import { useAuth } from '../components/AuthContext';

const MyBookings = () => {
	const { user } = useAuth();

	return (
		<div className="container my-5">
			<h2 className="text-center mb-4">My Bookings</h2>
			<p>Welcome, {user.email}! Here you can view your bookings.</p>
			{/* Add booking list or table here */}
		</div>
	);
};

export default MyBookings;
