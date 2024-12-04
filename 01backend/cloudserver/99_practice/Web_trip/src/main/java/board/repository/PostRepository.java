package board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import board.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	// 쿼리메서드라 만들어줘야함.
	void deleteByIdAndUser_Uid(Long pid, String uid);

}
