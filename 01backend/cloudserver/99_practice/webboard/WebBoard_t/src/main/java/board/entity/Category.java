package board.entity;

import board.entity.constant.CategoryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cid")
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "category_type", columnDefinition = "VARCHAR(50)")
	private CategoryType categoryType;

	protected Category() {}
	
	private Category(CategoryType categoryType) {
		this.categoryType = categoryType;
	}
	
	public static Category of(CategoryType categoryType) {
		return new Category (categoryType);
	}
	
}
