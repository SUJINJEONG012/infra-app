package board.common.util;

import board.entity.constant.UserRoleType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

//컨버터 만들어주기 -> 이걸@Convert로 설정해줘야함
// 내부적으로 관리할 수 있게, user 부분에 호출해서 사용할 수 있도록
@Converter
public class UserRoleTypeAttributeConverter implements AttributeConverter<UserRoleType, String>{

		// 엔티티 >db 동작
		@Override
		public String  convertToDatabaseColumn(UserRoleType attribute) {
			// 문자열이 들어가야함
			return attribute.getRoleType();
		}

		// db => 엔티티 동작
		@Override
		public UserRoleType convertToEntityAttribute(String dbData) {	
			return UserRoleType.getInstance(dbData);
		}
				
	}
	   