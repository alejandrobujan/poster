import Post from './Post';

const Posts = ({ posts }) => {
	if (posts.length === 0) {
		return (
			<div className="alert alert-danger" role="alert">
				No posts found
			</div>
		);
	}

	return (
		<section id="posts">
			<div className="container px-4 px-lg-5 mt-5">
				<div className="row gx-4 gx-lg-5 row-cols-1 row-cols-md-2 row-cols-xl-3 justify-content-center">
					{posts.map(({ id, title, description, url, price, categoryDto, images, type }) =>
						<Post key={id} id={id} title={title} description={description} url={url}
							price={price} categoryDto={categoryDto} images={images} type={type}/>
					)}
				</div>
			</div>
		</section>

	)
};

export default Posts;