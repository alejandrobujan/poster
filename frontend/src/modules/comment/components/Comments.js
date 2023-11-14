import Comment from './Comment';

const Comments = ({comments, postId}) => {
	if (comments.length === 0) {
		return (
			<div data-testid="NoComments" className="alert alert-danger" role="alert">
				No comments found
			</div>
		);
	}
	
	return (
		<div>
			<div data-testid="ManyComments" className="w-100">
				{comments.map(({ id, description, date, answers, parentId, level, userSummaryDto }) =>
					<Comment key={id} id={id} description={description} date={date} answers={answers} parentId={parentId} level={level} user={userSummaryDto} postId={postId}/>
				)}
			</div>
		</div>
	);
}

export default Comments;