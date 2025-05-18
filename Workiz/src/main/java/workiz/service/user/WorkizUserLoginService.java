package workiz.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import workiz.business.user.LoginValidation;
import workiz.persistence.user.WorkizUser;

@RestController
public class WorkizUserLoginService {
		
	@Autowired
	private LoginValidation loginValidation;
	
	@PostMapping(path = "/api/login", produces = "application/json")
	public WorkizUser login(@RequestBody WorkizUser user) {
		
		return loginValidation.validateLogin(user.getEmail(), user.getPassword());
	}
}
