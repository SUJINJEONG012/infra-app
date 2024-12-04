package board.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import board.common.constant.SearchType;
import board.dto.PostDto;
import board.dto.UserDto;
import board.dto.request.PostRequest;
import board.dto.response.PostResponse;
import board.dto.response.PostWithCommentsResponse;
import board.dto.security.BoardDetails;
import board.entity.constant.UserRoleType;
import board.service.PagingService;
import board.service.PostService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/posts")
@Controller
public class PostController {

	private final PostService postService;
	private final PagingService pagingService;

	@GetMapping
	public String getPosts(@PageableDefault(size=10, sort="createdDate", direction= Sort.Direction.DESC ) Pageable pageable,	
			@RequestParam (required = false) String searchType,
			@RequestParam (required = false) String searchValue,
			ModelMap map
		) {

		/*
		 * from 이없엇다면 List<PostResponse> postResponses = postDtos.stream() .map(postDto
		 * -> new PostResponse(postDto.getTitle(), postDto.getContent(),
		 * postDto.getCategoryType().name())) .collect(Collectors.toList()); // 변환 후
		 * List<PostResponse>로 수집
		 * 
		 */

		//페이징처리없는 리스트
//		List<PostResponse> posts = postService.getPosts()
//				.stream().map(PostResponse::from).toList();
		
	
		// 페이징만처리
//		Page<PostResponse> posts = postService.searchPosts(pageable)
//											.map(PostResponse::from);
		
		
		//페이징이랑 검색 같이 처리
		Page<PostResponse> posts = postService.searchPostsWithTypeAndValue(searchType,searchValue,pageable)
														.map(PostResponse::from);
		
		
		// 시작페이지, 종료페이지(현재페에지, 전체페이지개수)
		List<Integer> pagingNumbers =  pagingService.getPagingNumber(pageable.getPageNumber(), posts.getTotalPages());
		
	
		//
		
		map.addAttribute("posts", posts);
		map.addAttribute("pagingNumbers", pagingNumbers);
		return "posts/index";
	}

	
	
	
	@GetMapping("/form")
	public String postFormPage() {
		return "/posts/form";
	}

	// 등록처리, BoardDetails => Principal 로 해야하는데 이미 만들었으니
	@PostMapping
	public String registerPost(PostRequest postRequest,  @AuthenticationPrincipal BoardDetails boardDetails) {
//		// 로그인 가정
//		UserDto userDto = UserDto.of("admin", "admin", "admin", "admin@board.com", UserRoleType.ADMIN);

//		// postRequest 매핑되는지 확인
//		System.out.println("postRequest :" + postRequest);
//
//		/*
//		 * postDto.of 가 없었다면 // PostRequest를 PostDto로 변환 PostDto postDto = new PostDto()
//		 * 
//		 */
//
//		// 요청 데이터를 dto로 변환 ? 클라이언트에게 받은 정보 PostRequest 를 dto에 담아서 서비스에 보내줌.
//		PostDto postDtoWithUserDto = PostDto.of(postRequest.getTitle(), postRequest.getContent(),
//				postRequest.getCategoryType(), userDto);

		// 서비스계층에 저장 요청 ?
		postService.registerPost(postRequest.toDto(boardDetails.toDto()));
		return "redirect:/posts";
	}

	// 상세보기
	@GetMapping("/{pid}")
	public String getPost(@PathVariable Long pid, ModelMap map) {

//		PostResponse post = PostResponse.from(postService.getPost(pid));
//		// dto가 넘겨지니까, 맵에대한 부분을 response으로 변경해서 결과값을 => 하지만 맵은 반복문이고, 이건 그냥from으로 주면된다.
//		map.addAttribute("post", post);
		
		
		//PostWithCommentsResponse
		PostWithCommentsResponse post = PostWithCommentsResponse.from(postService.getPostWithComments(pid));
		//두개를 한번에 들고오는
		map.addAttribute("post",post);
		map.addAttribute("comments", post.getPostCommentReponse());
		
		
		return "/posts/detail";
	}
	
	

	// 수정하기 페이지 이동
	@GetMapping("/{pid}/form")
	public String updatePostform(@PathVariable Long pid, ModelMap map) {
		// 게시글 가져오기
		PostResponse post = PostResponse.from(postService.getPost(pid));
		// 게시글이 있다면 수정
		map.addAttribute("post", post);
		return "/posts/form";
//		if(post== null) {
//			return "/posts";
//		}else {
//			
//		}

	}

	// 수정처리
	@PostMapping("/{pid}/edit")
	public String updatePost(@PathVariable Long pid,
			@ModelAttribute PostRequest postRequest,
			@AuthenticationPrincipal BoardDetails boardDetails) {

		// 로그인 가정
		//UserDto userDto = UserDto.of("admin", "admin", "admin", "admin@board.com", UserRoleType.ADMIN);
		
		postService.updatePost(pid, postRequest.toDto(boardDetails.toDto()));
		
		return "redirect:/posts/" + pid;
	}
	
	
	

	@PostMapping("/{pid}/delete")
	public String deletePost(@PathVariable Long pid, 
			@AuthenticationPrincipal BoardDetails boardDetails) {
		//UserDto userDto = UserDto.of("admin", "admin", "admin", "admin@board.com", UserRoleType.ADMIN);

		postService.deletePost(pid, boardDetails.getUid());
		return "redirect:/posts";
	}

}