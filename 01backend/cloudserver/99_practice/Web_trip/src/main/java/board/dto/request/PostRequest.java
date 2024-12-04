package board.dto.request;

import board.dto.PostDto;
import board.dto.UserDto;
import board.entity.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class PostRequest {
    private String title;
    private String content;
    private CategoryType categoryType;
    
    // 이 메서드는 객체를 생성한다
    public static PostRequest of(String title, String content, CategoryType categoryType) {
    	return new PostRequest(title, content, categoryType); 
    }
    
    // PostRequest 객체를 PostDto로 변환하는역할
    // toDto을 통해 서비스계층에서 사용할 객체 PostDto로 쉽게변환
    public PostDto toDto(UserDto userDto) {
    	// PostDto를 만들어주는 메서드 ?
    	return PostDto.of(title, content, categoryType, userDto);
    }
    
}
