const PageNotFound = () => {
	return (
		<div className="container mt-5">
			<div className="row">
				<div className="col md-8 mx-auto text-center">
					<h1 data-testid="Title" className="display-4">404 - Page Not Found</h1>
					<p data-testid="Description" className="text-secondary">The page that you are looking for doesn't exists.</p>
				</div>
			</div>
		</div>

	);
};

export default PageNotFound;