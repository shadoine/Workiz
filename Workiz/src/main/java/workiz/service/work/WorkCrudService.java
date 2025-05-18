package workiz.service.work;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import workiz.business.work.WorkDuration;
import workiz.persistence.project.Project;
import workiz.persistence.project.ProjectRepository;
import workiz.persistence.user.WorkizUser;
import workiz.persistence.user.WorkizUserRepository;
import workiz.persistence.work.Work;
import workiz.persistence.work.WorkRepository;

@RestController
public class WorkCrudService {

	@Autowired
    private ProjectRepository projectRepository;
		
    @Autowired
	private WorkRepository workRepository;
    
    @Autowired
	private WorkizUserRepository userRepository;
	
	@Autowired
	private WorkDuration workDuration;

   /* WorkCrudService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }*/
	
	@GetMapping(path = "/api/work/{id}", produces = "application/json")
	public Work getWork(@PathVariable int id) {
		
		return workRepository.findById(id).orElse(null);
	}
	
	
	@DeleteMapping(path = "/api/work/{id}", produces = "application/json")
	public boolean deleteWork(@PathVariable int id) {
				
		Work w = workRepository.findById(id).orElse(null);
		
		if(w == null) {
			return false;
		}
		
		Project p = projectRepository.findById(w.getProject().getId()).orElse(null);
		if(p != null) {
			p.setpDuration(workDuration.removeFromProjectDuration(p.getpDuration(), w.getwDuration()));
			projectRepository.save(p);
			
			w.setProject(p);
		}
		
		workRepository.delete(w);
		
		return true;
	}
	
	
	@PutMapping(path = "/api/work/{id}", produces = "application/json")
	public boolean updateWork(@PathVariable int id, @RequestBody Work newW) {
		
		Work w = workRepository.findById(id).orElse(null);
		
		if(w == null) {
			return false;
		}
		
		String startTime = newW.getStartTime();
		String endTime = newW.getEndTime();
		
		
		if(!workDuration.validateWorkDuration(startTime, endTime)) {
			return false;
		}
		
		
		Integer wDuration = workDuration.calcWorkDuration(startTime, endTime);
		
		w.setDate(newW.getDate());
		w.setStartTime(newW.getStartTime());
		w.setEndTime(newW.getEndTime());
		w.setDescription(newW.getDescription());
		w.setProject(newW.getProject());
		
		
		Project p = projectRepository.findById(newW.getProject().getId()).orElse(null);
		if(p != null) {
			p.setpDuration(workDuration.updateProjectDuration(p.getpDuration(), w.getwDuration(), wDuration));
			projectRepository.save(p);
			
			w.setProject(p);
		}
		
		WorkizUser u = userRepository.findById(newW.getUser().getId()).orElse(null);
		if(u != null) {			
			w.setUser(u);
		}
		
		//muss man hier unten machen, weil ich oben noch die alte Duration brauche (w.getwDuration)
		w.setwDuration(wDuration);
		
		workRepository.save(w);
		
		return true;
	}
	
	@PostMapping(path = "/api/work/", produces = "application/json")
	public Work createWork(@RequestBody Work newW) {
		Work w = new Work();

		String startTime = newW.getStartTime();
		String endTime = newW.getEndTime();
		
		
		if(!workDuration.validateWorkDuration(startTime, endTime)) {
			return null;
		}
		
		Integer wDuration = workDuration.calcWorkDuration(startTime, endTime);

		w.setDate(newW.getDate());
		w.setStartTime(startTime);
		w.setEndTime(endTime);
		w.setDescription(newW.getDescription());
		w.setwDuration(wDuration);
		
		// das überschreibt das bestehende Project => Project p = newW.getProject();
		Project p = projectRepository.findById(newW.getProject().getId()).orElse(null);
		if(p != null) {
			p.setpDuration(workDuration.getProjectDuration(p.getpDuration(), wDuration));
			projectRepository.save(p);
			
			w.setProject(p);
		}
		
		WorkizUser u = userRepository.findById(newW.getUser().getId()).orElse(null);
		if(u != null) {
			w.setUser(u);
		}
		
		
		workRepository.save(w);
		
		
		return w;
	}
}
