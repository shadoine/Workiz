package workiz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {
	
	@GetMapping("/admin")
	public String forwardAdmin() {
	  return "forward:/admin.html";
	}

	@GetMapping("/user")
	public String forwardUser() {
	  return "forward:/user.html";
	}
}
