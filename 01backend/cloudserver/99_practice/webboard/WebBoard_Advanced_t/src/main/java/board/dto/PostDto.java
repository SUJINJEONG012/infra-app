package board.dto;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.RequestParam;

import board.entity.Post;
import board.entity.User;
import board.entity.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private CategoryType categoryType;
    private UserDto userDto;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;
    
    /*
     * 상황에 따라 필요한 데이터달라서 of 메서드는 두개 
     * 전체 데이터가 필요할 수 있고,
     * 일부 데이터만 가지고 PostDto 객체를 만들어야 할 수 있다.
     * */
    
    // 일부 데이터만으로 간단하게 사용
    public static PostDto of(String title, String content, CategoryType categoryType, UserDto userDto) {
    	return PostDto.of(null, title, content, categoryType, userDto, null, null, null, null);
    }
    
    // 모든 데이터가 필요한 상황
    public static PostDto of(Long id, String title, String content, CategoryType categoryType, UserDto userDto, LocalDateTime createdDate,
			String createdBy, LocalDateTime modifiedDate, String modifiedBy) {
    	return new PostDto(id, title, content, categoryType, userDto, createdDate, createdBy, modifiedDate, modifiedBy);
    }

	public static PostDto from(Post post) {
		return new PostDto(
							post.getId(),
							post.getTitle(),
							post.getContent(),
							post.getCategoryType(),
							UserDto.from(post.getUser()),
							post.getCreatedDate(),
							post.getCreatedBy(),
							post.getModifiedDate(),
							post.getModifiedBy()
		);
	}
    
    public Post toEntity(User user) {
    	return Post.of(
    					title, 
    					content, 
    					categoryType, 
    					user // 이게 들어가야 하는게 잘 봐야함. 누가작성했는지 알아야하니까
		);
    }
}
