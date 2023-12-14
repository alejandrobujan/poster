const Forbidden = () => {
	return (
		<div className="container mt-5">
			<div className="row">
				<div className="col md-8 mx-auto text-center">
					<h1 data-testid="Title" className="display-4">403 - Forbidden</h1>
					<p data-testid="Description" className="text-secondary">You don't have permission to see this page.</p>
				</div>
			</div>
		</div>

	);
};

export default Forbidden;