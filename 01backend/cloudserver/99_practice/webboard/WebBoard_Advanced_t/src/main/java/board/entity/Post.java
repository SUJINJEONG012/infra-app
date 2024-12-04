package board.entity;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import board.entity.constant.CategoryType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Entity

public class Post extends AuditingFields {
	
	@Id
	@Column(name = "pid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Setter
    private String title;
    
	@Setter
    private String content;
    
	@Setter
    @Enumerated(EnumType.STRING)
	@Column(name = "category_type", columnDefinition = "VARCHAR(50)")
    private CategoryType categoryType;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final Set<PostComment> postComments = new LinkedHashSet<>();
    
    @ManyToOne
    @JoinColumn(name = "uid")
    private User user;
    
    protected Post() {}
    
    private Post(String title, String content, CategoryType categoryType, User user) {
    	this.title = title;
    	this.content = content;
    	this.categoryType = categoryType;
    	this.user = user;
    }
    
    public static Post of(String title, String content, CategoryType categoryType, User user) {
    	return new Post(title, content, categoryType, user);
    }
    
    
    public void updateTitleAndContentAndCategoryType(String title, String content, CategoryType categoryType) {
    		this.title= title;
    		this.content= content;
    		this.categoryType = categoryType;
    }	
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
    
    
}
