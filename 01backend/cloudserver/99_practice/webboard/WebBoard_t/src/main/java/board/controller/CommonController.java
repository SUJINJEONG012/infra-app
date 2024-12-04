package board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import board.service.UserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommonController {
	
	private final UserService userService;
	
	@GetMapping({"/"})
	public String mainPage(Model model) {
		model.addAttribute("result", "board");  // result 속성에 "board" 값 추가
      
		return "/index";
	}
	
}
