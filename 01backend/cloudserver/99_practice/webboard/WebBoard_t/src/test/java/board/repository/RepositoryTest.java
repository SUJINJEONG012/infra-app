package board.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.test.annotation.Rollback;

import board.entity.User;
import board.entity.constant.UserRoleType;


@DisplayName("Repository 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Import(RepositoryTest.TestJPAConfig.class)
class RepositoryTest {
	
	private final UserRepository userRepository;
	
	// 생성자 주입
	RepositoryTest(@Autowired UserRepository userRepository){
		this.userRepository =  userRepository;
	}
	
	@DisplayName("Repository 확인")
	@Test
	void testRepository() {
		System.out.println(userRepository);
	
	}
	
	
	@DisplayName("Repository Dummy 데이터")	
//	@Disabled
	@Test
	@Rollback(false)
	void setDummies() throws InterruptedException {
		// ----------
		// given
		// admin 1명
		User admin  = User.of("admin",
								"admin" , 
								"admin", 
								"admin@board.com", UserRoleType.ADMIN);
		// 콘솔에 지금enum에서 ADMIN이라고 나오는걸 @Converter로 해결할 수 있음! => 여기선 동일한데, 디비에 들어갈땐 다름!
		System.out.println(admin);
		long previousUserCount = userRepository.count();
		
		
		// user 10명
		
		// category 3개
		
		// post 200개

		// post 1개당 --> comment 3개씩
		
		
		// ----------
		// when

		userRepository.save(admin);
		
		
		// ----------
		// then
		
		assertEquals(userRepository.count(), previousUserCount +1);
		
		
		
	}
	
	
	// Test 클래스 상단에 @Import
	@EnableJpaAuditing
	@TestConfiguration
	static class TestJPAConfig {
		
		// 자동생성되는걸 지정해주는 
		@Bean
		AuditorAware<String> auditorAware(){
			return () -> Optional.of("admin");
		}
	}
	
}
