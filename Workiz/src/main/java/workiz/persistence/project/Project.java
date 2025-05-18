package workiz.persistence.project;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import workiz.persistence.customer.Customer;
import workiz.persistence.work.Work;

@Entity
public class Project {
	
	@Id
	@GeneratedValue
	public Integer id;
	public String pName;
	public String status;
	public Integer pDuration;
	
	
	@ManyToOne
	private Customer customer;
	
	public Customer getCustomer() {
		return customer;
	}
	
	@OneToMany(mappedBy = "project")
	private List<Work> works;

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}	

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getpDuration() {
		return pDuration;
	}

	public void setpDuration(Integer pDuration) {
		this.pDuration = pDuration;
	}
}
