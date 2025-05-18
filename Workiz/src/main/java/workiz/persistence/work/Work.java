package workiz.persistence.work;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import workiz.persistence.project.Project;
import workiz.persistence.user.WorkizUser;

@Entity
public class Work {
	
	@Id
	@GeneratedValue
	public Integer id;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "dd.MM.yyyy")
	public Date date;
	public String startTime;
	public String endTime;
	public String description;
	public Integer wDuration;
	
	@ManyToOne
	private Project project;
	
	@ManyToOne
	private WorkizUser user;

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public WorkizUser getUser() {
		return user;
	}

	public void setUser(WorkizUser user) {
		this.user = user;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getwDuration() {
		return wDuration;
	}

	public void setwDuration(Integer wDuration) {
		this.wDuration = wDuration;
	}
}
