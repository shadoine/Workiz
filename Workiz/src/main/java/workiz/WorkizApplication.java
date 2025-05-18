package workiz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import workiz.persistence.customer.Customer;
import workiz.persistence.customer.CustomerRepository;
import workiz.persistence.project.Project;
import workiz.persistence.project.ProjectRepository;
import workiz.persistence.user.WorkizUser;
import workiz.persistence.user.WorkizUserRepository;
import workiz.persistence.work.Work;
import workiz.persistence.work.WorkRepository;

@SpringBootApplication
public class WorkizApplication {	
	
	@Autowired
	private ProjectRepository projectRepository;
	private CustomerRepository customerRepository;
	private WorkRepository workRepository;
	private WorkizUserRepository userRepository;

	
	
	WorkizApplication(ProjectRepository projectRepository, CustomerRepository customerRepository, WorkRepository workRepository, WorkizUserRepository userRepository) {
		this.projectRepository = projectRepository;
	    this.customerRepository = customerRepository;
	    this.workRepository = workRepository;
	    this.userRepository = userRepository;
	}
	
	public static void main(String[] args) {
		SpringApplication.run(WorkizApplication.class, args);
	}
	
	@PostConstruct
	public void createTestData() {
		

		boolean deleteAll = true;
		boolean createData = true;
		
		if(deleteAll) {
			workRepository.deleteAll();
			projectRepository.deleteAll();
			userRepository.deleteAll();
			customerRepository.deleteAll();
		}
		

		if(createData) {
			
			//Customers
			
			Customer c1 = new Customer();
			
			c1.setcName("Webtimal");
			c1.setStreet("Gundeldingerstrasse 170");
			c1.setZip("4053");
			c1.setPlace("Basel");
			
			Customer c2 = new Customer();
			
			c2.setcName("Novartis");
			c2.setStreet("Lichtstrasse 35");
			c2.setZip("4056");
			c2.setPlace("Basel");
			
			Customer c3 = new Customer();
			
			c3.setcName("Coop");
			c3.setStreet("Thiersteinerallee 12");
			c3.setZip("4002");
			c3.setPlace("Basel");
			
			Customer c4 = new Customer();
			
			c4.setcName("UBS");
			c4.setStreet("Bahnhofstrasse 45");
			c4.setZip("8001");
			c4.setPlace("Zürich");
			
			Customer c5 = new Customer();
			
			c5.setcName("Swisscom");
			c5.setStreet("Alte Tiefenaustrasse 6");
			c5.setZip("3050");
			c5.setPlace("Bern");
			
			Customer c6 = new Customer();
			
			c6.setcName("Migros");
			c6.setStreet("Pfingstweidstrasse 101");
			c6.setZip("8005");
			c6.setPlace("Zürich");
			
			customerRepository.save(c1);
			customerRepository.save(c2);
			customerRepository.save(c3);
			customerRepository.save(c4);
			customerRepository.save(c5);
			customerRepository.save(c6);
			
			
			//Projects
			
			Project p1 = new Project();
			
			p1.setpName("Website Relaunch");
			p1.setStatus("in-progress");
			p1.setCustomer(c1);
			p1.setpDuration(300);
			
			Project p2 = new Project();
			p2.setpName("CRM Migration");
			p2.setStatus("planned");
			p2.setCustomer(c2);
			p2.setpDuration(200);
			
			Project p3 = new Project();
			p3.setpName("Intranet Setup");
			p3.setStatus("on-hold");
			p3.setCustomer(c3);
			p3.setpDuration(150);
			
			Project p4 = new Project();
			p4.setpName("Security Audit");
			p4.setStatus("completed");
			p4.setCustomer(c4);
			p4.setpDuration(100);
			
			Project p5 = new Project();
			p5.setpName("Cloud Migration");
			p5.setStatus("in-progress");
			p5.setCustomer(c5);
			p5.setpDuration(220);
			
			Project p6 = new Project();
			p6.setpName("App Development");
			p6.setStatus("planned");
			p6.setCustomer(c6);
			p6.setpDuration(310);
			
			Project p7 = new Project();
			p7.setpName("Data Analysis");
			p7.setStatus("in-progress");
			p7.setCustomer(c1);
			p7.setpDuration(140);
			
			Project p8 = new Project();
			p8.setpName("Social Media Campaign");
			p8.setStatus("planned");
			p8.setCustomer(c2);
			p8.setpDuration(130);
			
			Project p9 = new Project();
			p9.setpName("Support Tool");
			p9.setStatus("in-progress");
			p9.setCustomer(c3);
			p9.setpDuration(170);
			
			Project p10 = new Project();
			p10.setpName("Backup Strategy");
			p10.setStatus("planned");
			p10.setCustomer(c4);
			p10.setpDuration(180);

			projectRepository.save(p1);
			projectRepository.save(p2);
			projectRepository.save(p3);
			projectRepository.save(p4);
			projectRepository.save(p5);
			projectRepository.save(p6);
			projectRepository.save(p7);
			projectRepository.save(p8);
			projectRepository.save(p9);
			projectRepository.save(p10);
			
			
			//Users
			
			WorkizUser u1 = new WorkizUser();
			
			u1.setuName("admin");
			u1.setEmail("admin@workiz.com");
			u1.setPassword("pw123");
			u1.setfName("");
			u1.setlName("");
			u1.setRole("admin");
			
			WorkizUser u2 = new WorkizUser();
			
			u2.setuName("vivien.aregger");
			u2.setEmail("vivien.aregger@workiz.com");
			u2.setPassword("pw1234");
			u2.setfName("Vivien");
			u2.setlName("Aregger");
			u2.setRole("user");
			
			WorkizUser u3 = new WorkizUser();
			
			u3.setuName("yoani.wenger");
			u3.setEmail("yoani.wenger@workiz.com");
			u3.setPassword("pw12345");
			u3.setfName("Yoani");
			u3.setlName("Wenger");
			u3.setRole("user");
			
			WorkizUser u4 = new WorkizUser();
			
			u4.setuName("olivier.zelger");
			u4.setEmail("olivier.zelger@workiz.com");
			u4.setPassword("pw54321");
			u4.setfName("Olivier");
			u4.setlName("Zelger");
			u4.setRole("user");
			
			WorkizUser u5 = new WorkizUser();
			
			u5.setuName("marc.schaaf");
			u5.setEmail("marc.schaaf@workiz.com");
			u5.setPassword("pw4321");
			u5.setfName("Marc");
			u5.setlName("Schaaf");
			u5.setRole("user");
			
			userRepository.save(u1);
			userRepository.save(u2);
			userRepository.save(u3);
			userRepository.save(u4);
			userRepository.save(u5);
			
			
			//Works
			
			//DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
			
			SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
			
			Work w1 = new Work();
			
			w1.setStartTime("08:00");
			w1.setEndTime("12:00");
			w1.setDescription("Requirements analysis");
			w1.setProject(p1);
			w1.setUser(u2);
			w1.setwDuration(240);
			try {
				w1.setDate(dateFormatter.parse("30.04.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w2 = new Work();
			
			w2.setStartTime("09:30");
			w2.setEndTime("11:30");
			w2.setDescription("Kickoff meeting");
			w2.setProject(p2);
			w2.setUser(u3);
			w2.setwDuration(120);
			try {
				w2.setDate(dateFormatter.parse("30.04.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w3 = new Work();
			
			w3.setStartTime("13:00");
			w3.setEndTime("15:30");
			w3.setDescription("Mockup design");
			w3.setProject(p6);
			w3.setUser(u4);
			w3.setwDuration(150);
			try {
				w3.setDate(dateFormatter.parse("01.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w4 = new Work();
			
			w4.setStartTime("10:00");
			w4.setEndTime("13:00");
			w4.setDescription("Security audit");
			w4.setProject(p4);
			w4.setUser(u5);
			w4.setwDuration(180);
			try {
				w4.setDate(dateFormatter.parse("02.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w5 = new Work();
			
			w5.setStartTime("08:15");
			w5.setEndTime("12:15");
			w5.setDescription("Outline backup strategy");
			w5.setProject(p10);
			w5.setUser(u2);
			w5.setwDuration(240);
			try {
				w5.setDate(dateFormatter.parse("02.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w6 = new Work();
			
			w6.setStartTime("13:30");
			w6.setEndTime("16:00");
			w6.setDescription("Client support");
			w6.setProject(p9);
			w6.setUser(u3);
			w6.setwDuration(150);
			try {
				w6.setDate(dateFormatter.parse("03.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w7 = new Work();
			
			w7.setStartTime("07:45");
			w7.setEndTime("11:45");
			w7.setDescription("Bug fixing");
			w7.setProject(p5);
			w7.setUser(u4);
			w7.setwDuration(240);
			try {
				w7.setDate(dateFormatter.parse("03.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w8 = new Work();
			
			w8.setStartTime("10:00");
			w8.setEndTime("12:00");
			w8.setDescription("Team meeting");
			w8.setProject(p3);
			w8.setUser(u5);
			w8.setwDuration(120);
			try {
				w8.setDate(dateFormatter.parse("04.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w9 = new Work();
			
			w9.setStartTime("09:00");
			w9.setEndTime("12:00");
			w9.setDescription("User testing");
			w9.setProject(p7);
			w9.setUser(u2);w9.setwDuration(180);
			try {
				w9.setDate(dateFormatter.parse("01.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w10 = new Work();
			
			w10.setStartTime("14:00");
			w10.setEndTime("17:00");
			w10.setDescription("Code review");
			w10.setProject(p8);
			w10.setUser(u3);
			w10.setwDuration(180);
			try {
				w10.setDate(dateFormatter.parse("30.04.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w11 = new Work();
			
			w11.setStartTime("08:30");
			w11.setEndTime("10:30");
			w11.setDescription("Documentation");
			w11.setProject(p1);
			w11.setUser(u4);
			w11.setwDuration(120);
			try {
				w11.setDate(dateFormatter.parse("02.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w12 = new Work();
			
			w12.setStartTime("13:00");
			w12.setEndTime("15:00");
			w12.setDescription("Demo update");
			w12.setProject(p2);
			w12.setUser(u5);
			w12.setwDuration(120);
			try {
				w12.setDate(dateFormatter.parse("01.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w13 = new Work();
			
			w13.setStartTime("08:00");
			w13.setEndTime("11:00");
			w13.setDescription("Refactor data structures");
			w13.setProject(p6);
			w13.setUser(u2);
			w13.setwDuration(180);
			try {
				w13.setDate(dateFormatter.parse("04.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w14 = new Work();
			
			w14.setStartTime("10:00");
			w14.setEndTime("13:00");
			w14.setDescription("Prepare presentation");
			w14.setProject(p3);
			w14.setUser(u3);
			w14.setwDuration(180);
			try {
				w14.setDate(dateFormatter.parse("03.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			Work w15 = new Work();
			
			w15.setStartTime("12:00");
			w15.setEndTime("14:00");
			w15.setDescription("Finalize migration plan");
			w15.setProject(p5);
			w15.setUser(u4);
			w15.setwDuration(120);
			try {
				w15.setDate(dateFormatter.parse("04.05.2025"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			workRepository.save(w1);
			workRepository.save(w2);
			workRepository.save(w3);
			workRepository.save(w4);
			workRepository.save(w5);
			workRepository.save(w6);
			workRepository.save(w7);
			workRepository.save(w8);
			workRepository.save(w9);
			workRepository.save(w10);
			workRepository.save(w11);
			workRepository.save(w12);
			workRepository.save(w13);
			workRepository.save(w14);
			workRepository.save(w15);
			

		}		
	}
	
	public Date formatDate(String date, SimpleDateFormat dateFormatter) throws ParseException {
		return dateFormatter.parse(date);
	}
}
