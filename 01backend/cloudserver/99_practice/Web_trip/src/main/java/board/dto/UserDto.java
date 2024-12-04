package board.dto;

import java.time.LocalDateTime;

import board.entity.User;
import board.entity.constant.UserRoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserDto {
    private String uid;
	private String username;
    private String password;
    private String email;
    private UserRoleType userRoleType;
    private LocalDateTime createdDate;
    private String createdBy;
    private LocalDateTime modifiedDate;
    private String modifiedBy;
    
    public static UserDto of(String uid, String username, String password, String email, UserRoleType userRoleType) {
    	return UserDto.of(uid, username, password, email, userRoleType, null, null, null, null);
    }
    
    public static UserDto of(String uid, String username, String password, String email, UserRoleType userRoleType,
			LocalDateTime createdDate, String createdBy, LocalDateTime modifiedDate, String modifiedBy) {
    	return new UserDto(uid, username, password, email, userRoleType, createdDate, createdBy, modifiedDate, modifiedBy);
    }

    public static UserDto from(User user) {
    	return new UserDto(
	    					user.getUid(),
	    					user.getUsername(),
	    					user.getPassword(),
	    					user.getEmail(),
	    					user.getUserRoleType(),
	    					user.getCreatedDate(),
	    					user.getCreatedBy(),
	    					user.getModifiedDate(),
	    					user.getModifiedBy()
		);
    }
    
    public User toEntity() {
    	return User.of(
						uid, 
						username, 
						password, 
						email, 
						userRoleType
		);
    }
    
    
    
}
