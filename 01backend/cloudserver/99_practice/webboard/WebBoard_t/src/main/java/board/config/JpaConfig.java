package board.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

	// 빈으로 설정해야, 데이터가 들어갈수 있음.=> 시큐리티에 들어가면 파악하기 위한 기능
	@Bean
	AuditorAware<String> auditorAware(){
		return () -> Optional.of("admin");
	}
	
}
