package workiz.business.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import workiz.persistence.user.WorkizUser;
import workiz.persistence.user.WorkizUserRepository;

@Service
public class LoginValidation {
	
	@Autowired
	private WorkizUserRepository userRepository;
	
	public WorkizUser validateLogin(String email, String password) {
		
		for (WorkizUser user : userRepository.findAll()) {
			if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
				return user;
			}
		}
		
		return null;
	}

}
