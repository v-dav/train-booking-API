import { useState } from 'react';

const SearchBar = () => {
	const [from, setFrom] = useState('');
	const [to, setTo] = useState('');
	const [date, setDate] = useState('');

	const handleSearch = (e) => {
		e.preventDefault();
		console.log('Searching for trains:', { from, to, date });
	};

	return (
		<div className="search-container">
			<div className="container">
				<form onSubmit={handleSearch} className="search-form">
					<div className="row g-3">
						<div className="col-md-3">
							<input
								type="text"
								className="form-control form-control-lg"
								placeholder="From"
								value={from}
								onChange={(e) => setFrom(e.target.value)}
							/>
						</div>
						<div className="col-md-3">
							<input
								type="text"
								className="form-control form-control-lg"
								placeholder="To"
								value={to}
								onChange={(e) => setTo(e.target.value)}
							/>
						</div>
						<div className="col-md-3">
							<input
								type="date"
								className="form-control form-control-lg"
								value={date}
								onChange={(e) => setDate(e.target.value)}
							/>
						</div>
						<div className="col-md-3">
							<button type="submit" className="btn btn-primary btn-lg w-100">Search</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	);
};

export default SearchBar;
