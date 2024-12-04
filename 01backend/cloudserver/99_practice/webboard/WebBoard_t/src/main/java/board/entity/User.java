package board.entity;

import java.util.ArrayList;
import java.util.List;

import board.common.util.UserRoleTypeAttributeConverter;
import board.entity.constant.UserRoleType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString(callSuper = true)
@Entity
public class User extends AuditingFields {
	
	@Id
	@Column(name = "uid", length = 20)
    private String uid;
    
	@Column(length = 20)
	private String username;
	
	@Column(length = 20)
    private String password;

    private String email;
    
    @Enumerated(EnumType.STRING)
	@Column(name = "role_type", columnDefinition = "VARCHAR(50)")
    @Convert(converter= UserRoleTypeAttributeConverter.class)
    private UserRoleType userRoleType;
    
    
    // 양방향으로 하고싶을땐 
    @OneToMany(mappedBy = "user", cascade =CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Post> posts= new ArrayList<>();
    
    // protected User() {} => @NoArgsConstructor(access = AccessLevel.PROTECTED) 이걸 사용하면 기본생성자는 입력하지도 않아도 
    
	private User(String uid, String username, String password, String email, UserRoleType userRoleType) {
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.userRoleType = userRoleType;
	}
	
    //기본생성자에 PROTECTED속성을 넣어줘야만 가능
	public static User of(String userId, String username, String password, String email, UserRoleType userRoleType) {
		return new User(userId, username, password, email, userRoleType);
	}

}
