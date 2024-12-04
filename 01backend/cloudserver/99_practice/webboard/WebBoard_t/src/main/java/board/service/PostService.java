package board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import board.dto.PostDto;
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
	
	
	
    public PostDto getPost(Long pid) {
    	Post Post = postRepository.findById(pid).orElseThrow(()-> new RuntimeException("Post not found with uid: " +pid ));
    	//PostDto.from(post) 메서드를 호출하여 엔티티를 DTO로 변환
    	return PostDto.from(Post);
    }
	
    @Transactional
    public void register(PostDto postDto) {
    	
    	User user = userRepository.save(User.of(postDto.getUserDto().getUid(),
    											postDto.getUserDto().getPassword(),
    											postDto.getUserDto().getPassword(),
    											postDto.getUserDto().getEmail(),
    											postDto.getUserDto().getUserRoleType()));
    	log.info("user: " + user);
    	//  user: User(super=board.entity.User@6f8d2116, uid=admin, username=admin.admin.com, password=admin.admin.com, email=admin, userRoleType=ADMIN, posts=[])
    	Post post = postDto.toEntity(user);
    	postRepository.save(post);
    }
    
    
    
    
	
    public void updatePost(Long pid, PostDto postDto) {
    	
    }
	
    public void deletePost(long pid, String uid) {

    }


	public List<Post> getPosts() {
		return postRepository.findAll();
		
	}
	
}
