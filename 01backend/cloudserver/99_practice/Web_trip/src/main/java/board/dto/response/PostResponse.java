package board.dto.response;

import java.time.LocalDateTime;

import board.dto.PostDto;
import board.entity.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private CategoryType categoryType;
    private LocalDateTime createdDate;
    private String createdBy;
    
    // 기본 생성자 대신, 파라미터로 데이터를 받는 메서드 제공
    public static PostResponse of(Long id, String title, String content, CategoryType categoryType, LocalDateTime createdDate, String createdBy) {
    	return new PostResponse(id, title, content, categoryType, createdDate, createdBy);
    }
    
    // PostDto에서 PostResponse로 변환하는 메서드
    // 서버에서 응답을 반환할 때 사용
    public static PostResponse from(PostDto postDto) {
    	
    	return new PostResponse(
    							postDto.getId(),
    							postDto.getTitle(),
    							postDto.getContent(),
    							postDto.getCategoryType(),
    							postDto.getCreatedDate(),
    							postDto.getCreatedBy());
    }
}
