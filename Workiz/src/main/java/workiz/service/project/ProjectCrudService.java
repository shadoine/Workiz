package workiz.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import workiz.persistence.project.Project;
import workiz.persistence.project.ProjectRepository;

@RestController
public class ProjectCrudService {
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@GetMapping(path = "/api/project/{id}", produces = "application/json")
	public Project getProject(@PathVariable int id) {
		
		return projectRepository.findById(id).orElse(null);
	}
	
	
	@DeleteMapping(path = "/api/project/{id}", produces = "application/json")
	public boolean deleteProject(@PathVariable int id) {
		
		Project p = projectRepository.findById(id).orElse(null);
		
		if(p == null) {
			return false;
		}
		
		projectRepository.delete(p);
		
		return true;
	}
	
	
	@PutMapping(path = "/api/project/{id}", produces = "application/json")
	public boolean updateProject(@PathVariable int id, @RequestBody Project newP) {
		
		Project p = projectRepository.findById(id).orElse(null);
		
		if(p == null) {
			return false;
		}

		p.setpName(newP.getpName());
		p.setStatus(newP.getStatus());
		p.setCustomer(newP.getCustomer());
		
		projectRepository.save(p);
		
		return true;
	}
	
	@PostMapping(path = "/api/project/", produces = "application/json")
	public Project createProject(@RequestBody Project newP) {
		Project p = new Project();

		p.setpName(newP.getpName());
		p.setStatus(newP.getStatus());
		p.setCustomer(newP.getCustomer());
		
		projectRepository.save(p);
		
		return p;
	}
}
