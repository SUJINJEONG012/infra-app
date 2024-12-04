package board.dto.request;

import board.dto.PostCommentDto;
import board.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class PostCommentRequest {
    private String content;
    private Long pid;
    
    public static PostCommentRequest of(String content, Long pid) {
    	return new PostCommentRequest(content, pid);
    }
    
    public PostCommentDto toDto(UserDto userDto) {
		return PostCommentDto.of(content, 
								 pid, 
								 userDto);
	}
    
}
