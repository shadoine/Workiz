package workiz.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import workiz.persistence.user.WorkizUser;
import workiz.persistence.user.WorkizUserRepository;

@RestController
public class WorkizUserListService {
	
	@Autowired
	private WorkizUserRepository userRepository;
	
	@GetMapping(path = "/api/users", produces = "application/json")
	public List<WorkizUser> getUser(){
		return userRepository.findAll();
	}
	
	@GetMapping(path = "/api/user/{id}", produces = "application/json")
	public WorkizUser getUser(@PathVariable int id) {
		
		return userRepository.findById(id).orElse(null);
	}
}