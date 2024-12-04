package board.entity;

import java.util.LinkedHashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    
	@ManyToOne(cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "category_type")
    private Category category;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private final Set<PostComment> postComments = new LinkedHashSet<>();
    
    @ManyToOne(cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "uid")
    private User user;
    
    protected Post() {}
    
    // 
    private Post(String title, String content, Category category, User user) {
    	this.title = title;
    	this.content = content;
    	this.category = category;
    	this.user = user;
    }
    
    public static Post of(String title, String content, Category category, User user) {
    	return new Post(title, content, category, user);
    }
}
