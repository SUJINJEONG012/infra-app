package board.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
@Entity
public class PostComment extends AuditingFields {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pcid")
    private Long id;

    @Column(length = 200)
    private String content;
    
    @ManyToOne(optional = false) 
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    private Post post;

    @ManyToOne(optional = false)
    @JoinColumn(name = "uid", referencedColumnName = "uid")
    private User user;

	protected PostComment() {}

	private PostComment(String content, Post post, User user) {
		this.content = content;
		this.post = post;
		this.user = user;
	}
    
	public static PostComment of(String content, Post post, User user) {
		return new PostComment(content, post, user);
	}
	
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PostComment that)) return false;
        return this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }
}
