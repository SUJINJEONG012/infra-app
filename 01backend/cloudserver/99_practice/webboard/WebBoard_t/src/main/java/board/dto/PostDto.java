package board.dto;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import board.entity.Category;
import board.entity.Post;
import board.entity.PostComment;
import board.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@ToString
@Getter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private Category category;
    private final Set<PostComment> postComments = new LinkedHashSet<>();
    private UserDto userDto;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;
    
    
    public static PostDto of(String title, String content, Category category, UserDto userDto) {
    	return PostDto.of(null, title, content, category, userDto, null, null, null, null);
    }
    
    
    public static PostDto of(Long id, String title, String content, Category category, UserDto userDto, LocalDateTime createdDate,
			String createdBy, LocalDateTime modifiedDate, String modifiedBy) {
    	return new PostDto(id, title, content, category, userDto, createdDate, createdBy, modifiedDate, modifiedBy);
    }

    
    
   // from**은 Entity객체를 DTO로 변환할 때 많이 사용
	public static PostDto from(Post post) {
		return new PostDto(
							post.getId(),
							post.getTitle(),
							post.getContent(),
							Category.of(post.getCategory().getCategoryType()),
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
    					category, 
    					user
    					
		);
    }
    
}
