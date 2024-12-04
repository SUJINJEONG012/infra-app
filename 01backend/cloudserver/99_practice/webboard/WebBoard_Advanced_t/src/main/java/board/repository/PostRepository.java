package board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import board.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{

	// 쿼리메서드라 만들어줘야함.
	void deleteByIdAndUser_Uid(Long pid, String uid);
	
	// 제목으로 검색
	Page<Post> findByTitleContaining(String searchValue, Pageable pageable);
	// 내용으로 검색
	Page<Post> findByContentContaining(String searchValue, Pageable pageable);
	// 아이디로 검색
	Page<Post> findByUser_UidContaining(String searchValue, Pageable pageable);
	

}
