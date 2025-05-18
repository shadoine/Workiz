package workiz.business.work;

import org.springframework.stereotype.Service;

@Service
public class WorkDuration {
	
	public Integer calcWorkDuration(String startTime, String endTime) {

		String[] start = startTime.split(":");
		String[] end = endTime.split(":");

		int startMin = Integer.parseInt(start[0]) * 60 + Integer.parseInt(start[1]);
		int endMin = Integer.parseInt(end[0]) * 60 + Integer.parseInt(end[1]);
		
		return endMin - startMin;
	}
	
	public Integer getProjectDuration(Integer oldPDuration, Integer wDuration) {
		
		if(oldPDuration == null) {
			oldPDuration = 0;
		}
		
		return oldPDuration + wDuration;
	}

	public Integer updateProjectDuration(Integer oldPDuration, Integer oldWDuration, Integer newWDuration) {
		
		if(oldPDuration == null) {
			oldPDuration = 0;
		}
		
		return oldPDuration - oldWDuration + newWDuration;
	}

	public Integer removeFromProjectDuration(Integer oldPDuration, Integer oldWDuration) {
		
		if(oldPDuration == null) {
			oldPDuration = 0;
		}

		return oldPDuration - oldWDuration;
	}
	
	public boolean validateWorkDuration(String startTime, String endTime) {
		
		String[] start = startTime.split(":");
		String[] end = endTime.split(":");

		int startHour = Integer.parseInt(start[0]);
		int startMin = Integer.parseInt(start[1]);
		
		int endHour = Integer.parseInt(end[0]);
		int endMin = Integer.parseInt(end[1]);
		
		if((startHour < 0 || startHour > 23) ||
			(startMin < 0 || startMin > 59) ||
			(endHour < 0 || endHour > 23) ||
			(endMin < 0 || endMin > 59)) {
			
			return false;
		}
		
		if(calcWorkDuration(startTime, endTime) <= 0) {
			return false;
		}
		
		return true;
	}
}
