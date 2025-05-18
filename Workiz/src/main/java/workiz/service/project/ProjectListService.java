package workiz.service.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import workiz.persistence.project.Project;
import workiz.persistence.project.ProjectRepository;

@RestController
public class ProjectListService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@GetMapping(path = "/api/projects", produces = "application/json")
	public List<Project> getProject(@RequestParam (required = false) String pName,
			@RequestParam (required = false) String cName,
			@RequestParam (required = false) boolean onlyActive){
		
		if (pName != null && cName != null) {
			return projectRepository.findByPNameContainingIgnoreCaseAndCustomerCNameContainingIgnoreCase(pName, cName);
		} else if (pName != null) {
			return projectRepository.findByPNameContainingIgnoreCase(pName);
		} else if (cName != null) {
			return projectRepository.findByCustomerCNameContainingIgnoreCase(cName);
		}
		
		
		if(onlyActive) {
			return projectRepository.findByStatusNot("inactive");
		}
		
		return projectRepository.findAll();
	}
	
	
	@GetMapping(path ="/api/projects/user/{userId}", produces = "application/json")
	public List<Project> getProjectsByUser(@PathVariable int userId, @RequestParam (required = false) String pName) {
		
		if (pName != null) {
			return projectRepository.findProjectByUserIdAndPName(userId, pName);
		}
		
		return projectRepository.findProjectByUserId(userId);
	}

}
