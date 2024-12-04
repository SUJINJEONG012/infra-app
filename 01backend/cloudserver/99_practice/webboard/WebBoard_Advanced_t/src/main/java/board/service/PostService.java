package board.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import board.dto.PostDto;
import board.dto.PostWithCommentsDto;
import board.dto.UserDto;
import board.dto.response.PostResponse;
import board.entity.Post;
import board.entity.User;
import board.repository.PostRepository;
import board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {
	
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	
	/*
	 * .map은  Java Stream API에서 사용하는 메서드
	 * Optional 객체나 스트림의 각 원소에 대해 함수를 적용하여 변환하는 기능
	 * Optional이 값이 있을 때 PostDto로 변환
	 * 값이 없으면 예외 던짐
	 * */
	public PostDto getPost (Long pid) {
    	return postRepository.findById(pid)
    			.map(PostDto::from)
    			.orElseThrow();
    }
	
	@Transactional
    public void registerPost(PostDto postDto) {
		// toEntity => user 
		User user = userRepository.getReferenceById(postDto.getUserDto().getUid());
		log.info("user: " + user);
		// PostDto를 Post 엔티티로 변환 (user객체도넣어주기)
		Post post = postDto.toEntity(user);
		//디비에저장
		postRepository.save(post);
	}
	
	@Transactional
    public void updatePost(Long pid, PostDto postDto) {
    	
		// toEntity => use 찾기
		User user = userRepository.getReferenceById(postDto.getUserDto().getUid());
		Post post = postRepository.getReferenceById(pid);
		
		// Post 수정 => 작성한 사람만 수정할수있음.
//		if(user == post.getUser()) {
//			 // 수정가능, 일단생략
//		}
		
		post.updateTitleAndContentAndCategoryType(
					postDto.getTitle(), 
					postDto.getContent(), 
					postDto.getCategoryType());
	}
	
	@Transactional
    public void deletePost(long pid, String uid) {
		postRepository.deleteByIdAndUser_Uid(pid,uid);
    }

//	public List<Post> getPosts() {
    public List<PostDto> getPosts() {
		return postRepository.findAll().stream()
										.map(post -> PostDto.from(post))
										.toList();
	}
    
    @Transactional
	public void deletePost(Long pid, UserDto userDto) {
    	
    	// post존재+ post 
    	postRepository.deleteByIdAndUser_Uid(pid,userDto.getUid());
	}
    
    // 변경해주고
    @Transactional
	public PostWithCommentsDto getPostWithComments(Long pid) {
		return postRepository.findById(pid)
									.map(PostWithCommentsDto::from)
									.orElseThrow();
									
	}

    // 페이징 처리 
	public Page<PostDto> searchPosts(Pageable pageable) {
		// response > dto로 변경
		return postRepository.findAll(pageable)
				.map(PostDto::from);
	}

	// 검색기능 & 키워드
	public Page<PostDto> searchPostsWithTypeAndValue(String searchType, String searchValue, Pageable pageable) {
		
		if(searchType ==null || searchType.isBlank()) {
			return postRepository.findAll(pageable)
					.map(PostDto::from);
		}
		

		// 키워드가 비어있다면 전체데이터 반환
		if(searchValue==null || searchValue.isBlank()) {
			return postRepository.findAll(pageable)
					.map(PostDto::from);
		}
		
		// 기능 : searchType에 따라 -> searchValue검색 (쿼리메서드 포함) 
		// 리턴 : Page<PostDto>	
		return 
				switch(searchType) {
				case "title" -> postRepository.findByTitleContaining(searchValue, pageable).map(PostDto::from);
				case "content" -> postRepository.findByContentContaining(searchValue, pageable).map(PostDto::from);
				case "uid" -> postRepository.findByUser_UidContaining(searchValue, pageable).map(PostDto::from);		
				//default -> throw new IllegalArgumentException("Unexception value : " + searchType);
				default -> postRepository.findAll(pageable).map(PostDto::from);
				
				};

	}
	
}
