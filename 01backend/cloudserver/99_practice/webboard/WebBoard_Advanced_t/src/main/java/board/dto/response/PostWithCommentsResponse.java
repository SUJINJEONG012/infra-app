package board.dto.response;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import board.dto.PostCommentDto;
import board.dto.PostWithCommentsDto;
import board.entity.constant.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class PostWithCommentsResponse {
	private Long id;
	private String title;
	private String content;
	private CategoryType categoryType;
	private String uid;
	private LocalDateTime createdDate;
	private String createdBy;
	private Set<PostCommentReponse> postCommentReponse;

	public static PostWithCommentsResponse of(Long id, String title, String content, CategoryType categoryType,
			String uid, LocalDateTime createdDate, String createdBy, Set<PostCommentReponse> postCommentReponse) {
		return new PostWithCommentsResponse(id, title, content, categoryType, uid, createdDate, createdBy,
				postCommentReponse);
	}

	public static PostWithCommentsResponse from(PostWithCommentsDto postWithCommentsDto) {

		return new PostWithCommentsResponse(postWithCommentsDto.getId(), postWithCommentsDto.getTitle(),
				postWithCommentsDto.getContent(), postWithCommentsDto.getCategoryType(),
				postWithCommentsDto.getUserDto().getUid(), postWithCommentsDto.getCreatedDate(),
				postWithCommentsDto.getCreatedBy(), getPostCommentReponses(postWithCommentsDto.getPostCommnentDtos())

		);
	}

	private static Set<PostCommentReponse> getPostCommentReponses(Set<PostCommentDto> postCommentDtos) {
		// PostCommentDto 반복 -> Response 변경 -> 중복제거 ?
		Map<Long, PostCommentReponse> map = postCommentDtos.stream().map(PostCommentReponse::from)
				.collect(Collectors.toMap(PostCommentReponse::getId, Function.identity()));

		// id값으로 중복제거 -> return
		return map.values().stream().collect(
				Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(PostCommentReponse::getCreatedDate)
						.reversed().thenComparingLong(PostCommentReponse::getId))));
	}

}
