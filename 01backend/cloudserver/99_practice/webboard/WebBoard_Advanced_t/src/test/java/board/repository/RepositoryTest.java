package board.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import board.entity.Post;
import board.entity.PostComment;
import board.entity.User;
import board.entity.constant.CategoryType;
import board.entity.constant.UserRoleType;

@DisplayName("Repository 테스트")
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)

class RepositoryTest {
	
	// 생성자 주입
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final PostCommentRepository postCommentRepository;
	
	// PasswordEncoder 인스턴스를 주입받거나 직접 생성
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    
	RepositoryTest(
		@Autowired UserRepository userRepository,
		@Autowired PostRepository postRepository,
		@Autowired PostCommentRepository postCommentRepository
		)
		{
		this.userRepository = userRepository;
		this.postRepository = postRepository;
		this.postCommentRepository = postCommentRepository;
		
		}
	
	@Test
	@Disabled
	void test() {
		System.out.println(userRepository);
		System.out.println(postRepository);
		System.out.println(postCommentRepository);
	}

	@DisplayName("Repository Dummy 데이터 명령")
	@Test
	@Disabled
	//@Rollback(false)
	void insertDummyData() {
		
		// give 기본적인데이터
		// admin 1명
		User admin = User.of(
				"admin2", 
				passwordEncoder.encode("admin"),
				"admin2", 
				
				"admin@admin.com", 
				UserRoleType.ADMIN);
	
		//값을 넣기전에 일단개수받고,
		long prevUserCount = userRepository.count();
		
		// when 실제적으로 실행하는 
		userRepository.save(admin);
		
		// then 결과를 학인하는
		
		assertThat(userRepository.count()).isEqualTo(prevUserCount + 1);
		
		
		
		// user 5명만들기 반복문으로
		List<User> users = IntStream.range(1, 10)
				.mapToObj(i-> User.of("user"+i, "user"+i, "user"+i, "user"+i+"@user.com", UserRoleType.USER))
				.collect(Collectors.toList());
		
		userRepository.saveAll(users);
		
		
		// Post 200개
		List<CategoryType> categories = Arrays.asList(CategoryType.FRONT,
													CategoryType.WEB,
													CategoryType.BACKEND);
		
		// 200개, 카테고리 돌아가면서 넣기
		List<Post> posts = IntStream.rangeClosed(1, 200)
				.mapToObj(i -> Post.of("title" +i, 
						"postContent: "+i, 
						categories.get(i%categories.size()), 
						users.get(i% users.size())))
				.collect(Collectors.toList());
		postRepository.saveAll(posts);
		
		
		// comment => post 1개당 => comment 3개씩
		// post comment : 1, (Post1 ~200), (User1 ~5)

		List<PostComment> postComments = IntStream.rangeClosed(1, 600)
				.mapToObj(i -> PostComment.of("PostComment: " +i, 
						posts.get(i%posts.size()), 
						users.get(i% users.size())))
				.collect(Collectors.toList());
		
		postCommentRepository.saveAll(postComments);
			
	}
	
	
	@DisplayName("Page 기본 사용법")
	@Disabled
	@Test
	public void pageTest() {
	
		// give
		// 페이저블이라는 파라미터값을 들어가는 값이 있어야한다.
		Pageable pageable = PageRequest.of(0, 10);
		
		// when
		Page<Post> page = postRepository.findAll(pageable);
		
		
		// then
		assertThat(page.getTotalElements()).isEqualTo(200); // 게시글이 200개인지 확인
		assertThat(page.getNumber()).isEqualTo(0); // 페이지넘버가 0인지 확인 
		System.out.println(page.getPageable());
		System.out.println("이전페이지 : " + page.hasPrevious());
		System.out.println("다음페이지 : " + page.hasNext());
	
	}
	
	
	
	
	
	
	
	
	//config를 호출해서 불러옴. -> createdBy는 null 이되면 안되니까. 설정값을 
	@EnableJpaAuditing
	@TestConfiguration
	static class TestJPAConfig{
		@Bean
		AuditorAware<String> auditorAware(){
			return ()-> Optional.of("admin");
		}
	}
	
	
}