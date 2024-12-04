package board.dto.request;

import board.dto.UserDto;
import board.entity.constant.UserRoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@Getter
public class UserRequest {
	
    private String uid;
	private String username;
    private String password;
    private String email;
    private UserRoleType userRoleType;
    
    public static UserRequest of(String uid, String username, String password, String email) {
    	return new UserRequest(uid, username, password, email, null); 
    }
    
    public static UserRequest of(String uid, String username, String password, String email, UserRoleType userRoleType) {
    	return new UserRequest(uid, username, password, email, userRoleType); 
    }
    
    public UserDto toDto(UserRoleType userRole) {
    	return UserDto.of(uid, 
    					  username, 
    					  password, 
    					  email, 
    					  userRole);
    }
    
}
