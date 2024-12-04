package board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import board.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
