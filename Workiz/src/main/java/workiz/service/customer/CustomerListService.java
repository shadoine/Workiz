package workiz.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import workiz.persistence.customer.Customer;
import workiz.persistence.customer.CustomerRepository;

@RestController
public class CustomerListService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping(path = "/api/customers", produces = "application/json")
	public List<Customer> getCustomer(@RequestParam (required = false) String cName) {
		
		if(cName != null) {
			return customerRepository.findByCNameContainingIgnoreCase(cName);
		}
		
		return customerRepository.findAll();
	}
	
}
