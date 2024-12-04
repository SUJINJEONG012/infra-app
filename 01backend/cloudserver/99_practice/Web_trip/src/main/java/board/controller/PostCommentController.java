package board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import board.dto.PostCommentDto;
import board.dto.UserDto;
import board.dto.request.PostCommentRequest;
import board.entity.constant.UserRoleType;
import board.service.PostCommentService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/comments")
@Controller
public class PostCommentController {
	
	private final PostCommentService postCommentService;
	
	
	@PostMapping
	public String registerNewPostComment(
			PostCommentRequest postCommentRequest
			
	) {
		// 로그인 가정
		UserDto userDto = UserDto.of("admin", 
									 "admin", 
									 "admin", 
									 "admin@board.com", 
									 UserRoleType.ADMIN);
		// dto를 만드는것,-> toDto를 만든다. 파라미터로 전달된 
		postCommentService.registerPostComment(postCommentRequest.toDto(userDto));
		
		return "redirect:/posts/" + postCommentRequest.getPid();
	}
	
	@PostMapping("/{pcid}/delete")
	public String deletePostComment(
			@PathVariable Long pcid,
			Long pid
	) {
		// 로그인 가정
		UserDto userDto = UserDto.of("admin", 
									 "admin", 
									 "admin", 
									 "admin@board.com", 
									 UserRoleType.ADMIN);
		
		// 댓글이 존재해야하고, 로그인하고있는 사용자
		postCommentService.deletePostComment(pcid, userDto.getUid());
		return "redirect:/posts/" + pid;
	}
}