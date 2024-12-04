package board.service;

import org.springframework.stereotype.Service;

import board.dto.PostCommentDto;
import board.entity.Post;
import board.entity.PostComment;
import board.entity.User;
import board.repository.PostCommentRepository;
import board.repository.PostRepository;
import board.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PostCommentService {
	
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;
	
	
	public void registerPostComment(PostCommentDto postCommentDto) {
		// 
		User user = userRepository.getReferenceById(postCommentDto.getUserDto().getUid());
		log.info("user : "+ user);
		Post post = postRepository.getReferenceById(postCommentDto.getPid());
		PostComment postComment = postCommentDto.toEntity(post, user);
	
		postCommentRepository.save(postComment);
		
	}
	
	@Transactional
	public void deletePostComment(Long pcid, String uid) {
		
		postCommentRepository.deleteByIdAndUser_Uid(pcid, uid);
	}
	
}
