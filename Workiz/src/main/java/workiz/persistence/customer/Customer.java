package workiz.persistence.customer;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import workiz.persistence.project.Project;

@Entity
public class Customer {
	
	@Id
	@GeneratedValue
	public Integer id;
	public String cName;
	public String street;
	public String zip;
	public String place;
	
	@OneToMany(mappedBy = "customer")
	private List<Project> projects = new ArrayList<>();


	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getStreet() {
		return street;
	}
	
	public void setStreet(String street) {
		this.street = street;
	}
	
	public String getZip() {
		return zip;
	}
	
	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getPlace() {
		return place;
	}
	
	public void setPlace(String place) {
		this.place = place;
	}
	
	public int getNumProjects() {
		return projects.size();
	}

}
