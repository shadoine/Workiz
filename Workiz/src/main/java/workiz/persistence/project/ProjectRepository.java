package workiz.persistence.project;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{

	List<Project> findByPNameContainingIgnoreCaseAndCustomerCNameContainingIgnoreCase(String pName, String cName);

	List<Project> findByPNameContainingIgnoreCase(String pName);

	List<Project> findByCustomerCNameContainingIgnoreCase(String cName);
	
	@Query("SELECT w.project FROM Work w WHERE w.user.id = ?1 ORDER BY w.project.pDuration DESC")
	List<Project> findProjectByUserId(int userId);
	
	@Query("SELECT w.project FROM Work w WHERE w.user.id = ?1 AND LOWER(w.project.pName) LIKE LOWER(CONCAT('%', ?2,'%')) ORDER BY w.project.pDuration DESC")
	List<Project> findProjectByUserIdAndPName(int userId, String pName);

	List<Project> findByStatusNot(String string);
	
}
