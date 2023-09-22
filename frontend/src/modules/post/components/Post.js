
const Post = ({title, descrition, url, price, categoryId, images}) => {
	<div>
		<ul>
			<li>{title}</li>
			<li>{descrition}</li>
			<li>{url}</li>
			<li>{price}</li>
			<li>{categoryId}</li>
			<li>{images}</li>
		</ul>
	</div>
};

export default Post;