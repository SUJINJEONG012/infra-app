package board.entity;

import java.util.Objects;

import board.common.utils.UserRoleTypeAttributeConverter;
import board.entity.constant.UserRoleType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Entity
public class User extends AuditingFields {
	
	@Id
	@Column(name = "uid", length = 20)
    private String uid;
    
	@Column(length = 20)
	private String username;
	
	@Column(length = 255)
    private String password;

    private String email;
    
	@Column(name = "role_type", columnDefinition = "VARCHAR(50)")
    @Convert(converter = UserRoleTypeAttributeConverter.class)
    private UserRoleType userRoleType;
    
    protected User() {}
    
	private User(String uid, String username, String password, String email, UserRoleType userRoleType) {
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.email = email;
		this.userRoleType = userRoleType;
	}
	
	public static User of(String userId, String username, String password, String email, UserRoleType userRoleType) {
		return new User(userId, username, password, email, userRoleType);
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User that)) return false;
        return this.getUid() != null && this.getUid().equals(that.getUid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getUid());
    }
}