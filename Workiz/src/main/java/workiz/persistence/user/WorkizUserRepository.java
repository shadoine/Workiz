package workiz.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkizUserRepository extends JpaRepository<WorkizUser, Integer>{

}
