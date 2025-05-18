package workiz.persistence.work;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work, Integer> {

	List<Work> findByProjectPNameContainingIgnoreCase(String pName, Sort sort);
	
	List<Work> findByUserId(int userId, Sort sort);

	List<Work> findByUserIdAndProjectPNameContainingIgnoreCase(int userId, String pName, Sort sort);

}
