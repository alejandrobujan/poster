import Post from './Post';

const Posts = ({posts}) => {
	posts.map(post =>
		<Post title={post.title} descrition={post.description} url={post.url}
		price={post.price} categoryId={post.categoryId} images={post.images}/>
	);
};

export default Posts;