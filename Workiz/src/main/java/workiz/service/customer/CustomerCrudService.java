package workiz.service.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import workiz.persistence.customer.Customer;
import workiz.persistence.customer.CustomerRepository;

@RestController
public class CustomerCrudService {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping(path = "/api/customer/{id}", produces = "application/json")
	public Customer getCustomer(@PathVariable int id) {

		return customerRepository.findById(id).orElse(null);
	}
	
	
	@DeleteMapping(path = "/api/customer/{id}", produces = "application/json")
	public boolean deleteCustomer(@PathVariable int id) {
		
		Customer c = customerRepository.findById(id).orElse(null);
		
		if(c == null) {
			return false;
		}
		
		customerRepository.delete(c);
		
		return true;
	}
	
	
	@PutMapping(path = "/api/customer/{id}", produces = "application/json")
	public boolean updateCustomer(@PathVariable int id, @RequestBody Customer newC) {
		
		Customer c = customerRepository.findById(id).orElse(null);
		
		if(c == null) {
			return false;
		}

		c.setcName(newC.getcName());
		c.setStreet(newC.getStreet());
		c.setZip(newC.getZip());
		c.setPlace(newC.getPlace());
		
		customerRepository.save(c);
		
		return true;
	}
	
	@PostMapping(path = "/api/customer/", produces = "application/json")
	public Customer createCustomer(@RequestBody Customer newC) {
		Customer c = new Customer();

		c.setcName(newC.getcName());
		c.setStreet(newC.getStreet());
		c.setZip(newC.getZip());
		c.setPlace(newC.getPlace());
		
		customerRepository.save(c);
		
		return c;
	}

}
