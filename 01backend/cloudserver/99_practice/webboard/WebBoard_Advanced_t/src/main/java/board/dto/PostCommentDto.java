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
    private Long pid;
    private UserDto userDto;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;
    
	public static PostCommentDto of(String content, Long pid, UserDto userDto) {
		return PostCommentDto.of(null, content, pid, userDto, null, null, null, null);
	}
    
	public static PostCommentDto of(Long id, String content, Long pid, UserDto userDto, LocalDateTime createdDate, String createdBy,
			LocalDateTime modifiedDate, String modifiedBy) {
		return new PostCommentDto(id, content, pid, userDto, createdDate, createdBy, modifiedDate, modifiedBy);
	}
	
	public static PostCommentDto from(PostComment postComment) {
		return new PostCommentDto(
									postComment.getId(),
									postComment.getContent(),
									postComment.getPost().getId(),
									UserDto.from(postComment.getUser()),
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
