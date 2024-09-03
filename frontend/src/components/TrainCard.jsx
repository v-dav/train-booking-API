const TrainCard = ({ train }) => {
	return (
		<div className="col-md-6 mb-4">
			<div className="card">
				<div className="card-body">
					<h5 className="card-title">{train.name}</h5>
					<h6 className="card-subtitle mb-2 text-muted">{train.type}</h6>
					<p className="card-text">
						From: {train.departureStation}<br />
						To: {train.arrivalStation}<br />
						Departure: {new Date(train.departureTime).toLocaleString()}<br />
						Arrival: {new Date(train.arrivalTime).toLocaleString()}<br />
						Capacity: {train.capacity}
					</p>
				</div>
			</div>
		</div>
	);
};

export default TrainCard;
