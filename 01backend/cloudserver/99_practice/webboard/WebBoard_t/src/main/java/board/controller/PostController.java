package board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import board.dto.PostDto;
import board.dto.UserDto;
import board.entity.Category;
import board.entity.Post;
import board.entity.constant.CategoryType;
import board.entity.constant.UserRoleType;
import board.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/posts")
@Controller
public class PostController {
	
	private final PostService postService;
	
	@GetMapping()
	public String getPosts(ModelMap map) {
		
		List<Post> posts = postService.getPosts();
		map.addAttribute("posts", posts);
		return "/posts/index";
	}
	
	
	// 글 상세보기
	@GetMapping("/{pid}")
	public String getPostsByPid(@PathVariable Long pid, Model model) {
		// 서비스 호출하여 게시글 정보 가져오기
		PostDto postDto = postService.getPost(pid);
		log.info("postDto : " + postDto);
		model.addAttribute("post", postDto);
		return "/posts/detail";
	}
	
	// 글 작성페이지 이동
	@GetMapping("/form")
	public String write() {
		return "/posts/form";
	}
	
	// 글 쓰기
	@PostMapping("/form")
	public String Register(PostDto postDto) {
		
		UserDto userDto = UserDto.of("admin","admin" ,"admin.admin.com", "admin", UserRoleType.ADMIN);
		postService.register(PostDto.of(postDto.getTitle(), 
										postDto.getContent(), 
										Category.of(CategoryType.BACKEND), 
										userDto));
		
		return "redirect:/posts"; // 게시글 작성 후 리다이렉트
	}
	
	
	// 글 수정 페이지 이동
	@GetMapping("{pid}/from")
	public String getEditForm(@PathVariable Long pid, Model model) {
		// 기존 글 데이터 가져와서 
		PostDto postDto = postService.getPost(pid);
		model.addAttribute("post", postDto);
		return "/posts/form"; // 수정 및 작성에 공용으로 사용하는 뷰
	}
	
	
	// 글  수정
	@PostMapping()
	public String updatePosts() {
		return "";
	}
	
	
	
	
	// 글 삭제
	@PostMapping("/{pid}")
	public String deletePostsByPid() {
		return "";
	}
}