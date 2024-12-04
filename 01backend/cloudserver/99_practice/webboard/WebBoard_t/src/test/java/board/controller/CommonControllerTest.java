package board.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import board.service.UserService;

@DisplayName("Controller 테스트")
@WebMvcTest(CommonController.class)
class CommonControllerTest {

	private final MockMvc mockMvc;
	
	// 생성자 주입
	CommonControllerTest(@Autowired MockMvc mockMvc){
		this.mockMvc = mockMvc;
	}
	
	 @MockBean
	 private UserService userService;
	
	@DisplayName("controller 객체확인!")
	@Disabled
	@Test
	void testController() {
		System.out.println(mockMvc);
	}
	
	@DisplayName("Common 컨트롤러 상태확인 ")
	@Test
	void testCommonControllerCheckStatus() throws Exception {
		
		// given
		
		
		// when
		
		// then
		mockMvc.perform(get("/"))
			.andExpect(status().isOk()) // 상태확인
			.andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
			.andExpect(view().name("/index"))
			.andExpect(model().attribute("result", "board"))
			.andDo(result ->{
				// 응답 내용을 콘솔에 출력
	            System.out.println("Response: " + result.getResponse().getContentAsString());
			});
			
	
	}

}
