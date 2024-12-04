package board.common.utils;

import org.springframework.core.convert.converter.Converter;

import board.entity.constant.CategoryType;


//@Convert
public class CategoryTypeRequestConverter implements Converter<String,CategoryType>{

	@Override
	public CategoryType convert(String source) {
		
		return CategoryType.getInstance(source);
	}

	
	
}
