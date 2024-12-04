package board.dto.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import board.dto.UserDto;
import board.entity.constant.UserRoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class BoardDetails implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String uid;
	private String username;
	private String password;
	private String email;
	private UserRoleType userRoleType;
	
	
	public static BoardDetails of(String uid, String username, String password,String email,UserRoleType userRoleType) {
		return new BoardDetails(	
				uid,
				username,
				password,
				email,
				userRoleType
				);
	}

	
	public static BoardDetails from(UserDto userDto) {
		return BoardDetails.of(
				userDto.getUid(), 
				userDto.getUsername(),
				userDto.getPassword(), 
				userDto.getEmail(),
				userDto.getUserRoleType()
				);
	} 
	
	// dto로 만들면서 넘겨줄수 있어야함.최종적으로 dto로 만들어주는것
	public UserDto toDto() {
		return UserDto.of(
				uid, 
				username, 
				password, 
				email, 
				userRoleType
				
		);
	}
	
	
	// 권한= > ROLE_ 라고적혀야 인식을함. 시큐리티에서는 그래서 String 값이어야만함.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collection = new ArrayList<GrantedAuthority>();
		// 유저냐, 어드민이냐.
		
		collection.add(()-> userRoleType.getRoleType());
		return collection;
	}
	
	// 비밀번호
	@Override
	public String getPassword() { return password; }

	//이름
	@Override
	public String getUsername() { return username; }

	
	
	@Override
	public boolean isAccountNonExpired() {
	    return true;
	}

	@Override
	public boolean isAccountNonLocked() {
	    return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true;
	}

	@Override
	public boolean isEnabled() {
	    return true;
	}
	
}
