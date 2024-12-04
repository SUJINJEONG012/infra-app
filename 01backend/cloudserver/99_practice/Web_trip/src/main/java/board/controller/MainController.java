package board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import board.dto.UserDto;
import board.dto.request.UserRequest;
import board.entity.constant.UserRoleType;
import board.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {

	private final UserService userService;
	
	@GetMapping("/sign-up")
	public String signupPage() {
		return "sign-up";
	} 

	@PostMapping("/sign-up")
	public String signup(UserRequest userRequest) {
		// 일반적인 dto가 아니라 리퀘스크로 받아온다(파라미터). 그리고 만들어준다.
		// 리쿼스트를 받아서 dto를 생성 
		// 컨버터가 정상동작해서 ROLE_ADMINㅇ으로 잘 저장됨.
		UserDto userDto = userRequest.toDto(UserRoleType.ADMIN);
		//방금만들어준 userDto를 넘겨준다.
		userService.registerUser(userDto);
		return "sign-up";
	} 
	
}
