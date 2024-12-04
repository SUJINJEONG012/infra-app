package board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import board.dto.PostDto;
import board.dto.PostWithCommentsDto;
import board.dto.UserDto;
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
	
	private final PostRepository postReository;
	private final UserRepository userReository;
	
	/*
	 * .map은  Java Stream API에서 사용하는 메서드
	 * Optional 객체나 스트림의 각 원소에 대해 함수를 적용하여 변환하는 기능
	 * Optional이 값이 있을 때 PostDto로 변환
	 * 값이 없으면 예외 던짐
	 * */
	public PostDto getPost (Long pid) {
    	return postReository.findById(pid)
    			.map(PostDto::from)
    			.orElseThrow();
    }
	
	@Transactional
    public void registerPost(PostDto postDto) {
		// toEntity => user 
		User user = userReository.getReferenceById(postDto.getUserDto().getUid());
		log.info("user: " + user);
		// PostDto를 Post 엔티티로 변환 (user객체도넣어주기)
		Post post = postDto.toEntity(user);
		//디비에저장
		postReository.save(post);
	}
	
	@Transactional
    public void updatePost(Long pid, PostDto postDto) {
    	
		// toEntity => use 찾기
		User user = userReository.getReferenceById(postDto.getUserDto().getUid());
		Post post = postReository.getReferenceById(pid);
		
		// Post 수정 => 작성한 사람만 수정할수있음.
//		if(user == post.getUser()) {
//			 // 수정가능, 일단생략
//		}
		
		post.updateTitleAndContentAndCategoryType(
					postDto.getTitle(), 
					postDto.getContent(), 
					postDto.getCategoryType());
	}
	
	
    public void deletePost(long pid, String uid) {

    }

//	public List<Post> getPosts() {
    public List<PostDto> getPosts() {
		return postReository.findAll().stream()
										.map(post -> PostDto.from(post))
										.toList();
	}
    
    @Transactional
	public void deletePost(Long pid, UserDto userDto) {
    	
    	// post존재+ post 
    	postReository.deleteByIdAndUser_Uid(pid,userDto.getUid());
	}
    
    // 변경해주고
    @Transactional
	public PostWithCommentsDto getPostWithComments(Long pid) {
		return postReository.findById(pid)
									.map(PostWithCommentsDto::from)
									.orElseThrow();
									
	}
	
}
