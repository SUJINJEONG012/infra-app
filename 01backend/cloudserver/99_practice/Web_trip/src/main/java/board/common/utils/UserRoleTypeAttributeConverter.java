package board.common.utils;

import board.entity.constant.UserRoleType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Convert;

/* 컨버터로 인하여 DB에 _언더바로 저장*/
@Convert
public class UserRoleTypeAttributeConverter implements AttributeConverter<UserRoleType, String>{

	@Override
	public String convertToDatabaseColumn(UserRoleType attribute) {
		return attribute.getRoleType();
	}

	@Override
	public UserRoleType convertToEntityAttribute(String dbData) {
		return UserRoleType.getInstance(dbData);
	}
}