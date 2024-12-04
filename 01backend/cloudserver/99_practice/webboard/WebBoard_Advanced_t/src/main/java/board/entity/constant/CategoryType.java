package board.entity.constant;

import java.util.Arrays;

public enum CategoryType {
	FRONT, 
	WEB, 
	BACKEND;
	
	public static CategoryType getInstance(String categoryType) {
		return Arrays.stream(CategoryType.values())
					.filter(category -> category.name().equals(categoryType))
					.findFirst()
					.orElseThrow();
	}
}
