package board.dto;

import java.time.LocalDateTime;

import board.entity.Post;
import board.entity.PostComment;
import board.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class PostCommentDto {
	private Long id;
    private String content;
    private Post post;
    private User user;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;
    
	public static PostCommentDto of(String content, Post post, User user) {
		return PostCommentDto.of(null, content, post, user, null, null, null, null);
	}
    
	public static PostCommentDto of(Long id, String content, Post post, User user, LocalDateTime createdDate, String createdBy,
			LocalDateTime modifiedDate, String modifiedBy) {
		return new PostCommentDto(id, content, post, user, createdDate, createdBy, modifiedDate, modifiedBy);
	}
	
	public static PostCommentDto from(PostComment postComment) {
		return new PostCommentDto(
									postComment.getId(),
									postComment.getContent(),
									postComment.getPost(),
									postComment.getUser(),
									postComment.getCreatedDate(),
									postComment.getCreatedBy(),
									postComment.getModifiedDate(),
									postComment.getModifiedBy()
		);
	}
	
	public PostComment toEntity(Post post, User user) {
		return PostComment.of(
								content, 
								post, 
								user
		);
	}
	
}
