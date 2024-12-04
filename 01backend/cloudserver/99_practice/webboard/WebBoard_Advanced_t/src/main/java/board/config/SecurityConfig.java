package board.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import board.dto.security.BoardDetails;
import board.service.UserService;


@Configuration
public class SecurityConfig {

	// 요청하면 필터체인
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth
									.requestMatchers(PathRequest.toStaticResources().atCommonLocations())
									.permitAll()
									.requestMatchers("/","/posts", "sign-up")
									.permitAll() // post로 시작되는건 다 허용
									.anyRequest().authenticated() // 그외에는 다 허가가 필요함
								)
			.formLogin(withDefaults())
			
			.logout(logout-> logout.logoutSuccessUrl("/"));
		
		
		return http.build();
	}
	
	
	// 인증한 데이터를 가져오는 로직
	@Bean
	public UserDetailsService userDetailsService(UserService userService) {
		
		// 객체를 찾아와서 매핑시켜주는
		return username -> userService.searchUser(username)
										.map(BoardDetails::from)
										.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
										
				
	}
	
	
	// 현재 db에는 암호화 x -> 이를 그대로 사용하기 위한 encoder 설정
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return NoOpPasswordEncoder.getInstance();
//	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder();
	}
		
	
}
