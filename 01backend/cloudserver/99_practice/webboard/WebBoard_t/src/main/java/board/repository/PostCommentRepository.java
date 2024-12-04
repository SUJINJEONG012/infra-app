package board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import board.entity.PostComment;

@Repository
public interface PostCommentRepository extends JpaRepository<PostComment, Long>{

}
