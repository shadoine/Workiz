package workiz.service.work;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import workiz.persistence.work.Work;
import workiz.persistence.work.WorkRepository;

@RestController
public class WorkListService {

	@Autowired
	private WorkRepository workRepository;
	
	@GetMapping(path = "/api/works", produces = "application/json")
	public List<Work> getWork(@RequestParam (required = false) String pName){
		
		if(pName != null) {
			return workRepository.findByProjectPNameContainingIgnoreCase(pName, Sort.by(Sort.Direction.DESC, "date"));
		}
		
		return workRepository.findAll(Sort.by(Sort.Direction.DESC, "date"));
	}
	
	@GetMapping(path ="/api/works/user/{userId}", produces = "application/json")
	public List<Work> getWorksByUser(@PathVariable int userId, @RequestParam (required = false) String pName) {
		
		if(pName != null) {
			return workRepository.findByUserIdAndProjectPNameContainingIgnoreCase(userId, pName, Sort.by(Sort.Direction.DESC, "date"));
		}
		
		return workRepository.findByUserId(userId, Sort.by(Sort.Direction.DESC, "date"));
	}
}
