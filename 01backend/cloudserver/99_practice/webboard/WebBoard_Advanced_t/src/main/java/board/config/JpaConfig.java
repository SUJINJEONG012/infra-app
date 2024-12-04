package board.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import board.dto.security.BoardDetails;

@EnableJpaAuditing
@Configuration
public class JpaConfig {
//	@Bean
//	AuditorAware<String> auditorAware() {
//		return () -> Optional.of("admin");
//	}
	
	
	@Bean
    public AuditorAware<String> auditorAware() {
        return() -> Optional.ofNullable(SecurityContextHolder.getContext())
        						.map(SecurityContext::getAuthentication)
        						.filter(Authentication::isAuthenticated)
        						.map(Authentication::getPrincipal)
        						.map(principal ->{
        							if(principal instanceof BoardDetails) {
        								return ((BoardDetails)principal).getUid();
        							}
        								else if(principal instanceof String) {
        								return (String) principal;
        							}
        								else {
        								return null;
        							}
        						});
    }
	
}