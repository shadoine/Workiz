//=========================================
//			  	   GLOBAL
//=========================================

let loggedInUser = parseInt(new URL(location.href).searchParams.get("user"));
let projectChoices = null;
let customerChoices = null;
let statusChoices = null;

function getObjectData(element) {
	
	let parent = element.closest("td.actions");
	
	let object = parent.attr("data-object");
	let id = parent.attr("data-id");
	
	return [object, id];
}

function errorMsg(msg) {
	let errorMsg = "<div class='error-msg'>";
	errorMsg += "<span>" + msg +"</span>";
	errorMsg += "</div>";

	$(".msg-container").append(errorMsg);
}


function fireToast(message, type) {

	let Toast = Swal.mixin({
		toast: true,
		position: "top-end",
		showConfirmButton: false,
		timer: 2000,
		timerProgressBar: true,
		customClass: {
			popup: type + "-toast",
		}
	});

	Toast.fire({
		title: message,
	});
}

function fireErrorModal(text) {

	Swal.fire({
		title: "Error",
		text: text ?? "Something went wrong.",
		icon: "error",
		buttonsStyling: false,
	});
}



function getUsersWork(user) {
	if(window.location.pathname.includes("admin")) {
		$.getJSON("http://localhost:8080/api/works").done(getWorkList);
	} else {

		$.getJSON("http://localhost:8080/api/works/user/" + user).done(getWorkList);
	}
}

function getUsersProjects(user) {
	if(window.location.pathname.includes("admin")) {
		$.getJSON("http://localhost:8080/api/projects").done(getProjectList);
	} else {

		$.getJSON("http://localhost:8080/api/projects/user/" + user).done(getProjectList);
	}
}


function formatDuration(minutes) {
	if(!minutes) {
		return "0mins";
	}
		
	if(minutes < 60) {
		return minutes + "min";
	}
	
	return Math.floor(minutes / 60) + "h " + minutes % 60 + "min";
}

//=========================================
//				   FILTER
//=========================================




$(".filter-toggle").click(function() {
	let container = $(this).parent();
	
	container.toggleClass("active");
	
});





$(".apply-filter-btn").click(function(e) {

	e.preventDefault();
	
	let filter = $(this).closest(".filter-btns").data("filter");
	
	switch(filter) {
		case "work":
			applyWorkFilter(loggedInUser);
			break;
			
		case "project":
			applyProjectFilter(loggedInUser);
			break;
		
		default:
			applyCustomerFilter(loggedInUser);
	}
});

$(".reset-filter-btn").click(function(e) {
	
	let filter = $(this).closest(".filter-btns").data("filter");
	
	switch(filter) {
		case "work":
			getUsersWork(loggedInUser);
			break;
			
		case "project":
			getUsersProjects(loggedInUser);
			break;
		
		default:
			$.getJSON("http://localhost:8080/api/customers").done(getCustomerList);
			
	}
});


function applyWorkFilter(user) {
	
	let pName = $("#work-filter-pname").val();
	
	if(window.location.pathname.includes("admin")) {
		$.getJSON("http://localhost:8080/api/works", {"pName":pName}).done(getWorkList);
	} else {

		$.getJSON("http://localhost:8080/api/works/user/" + user, {"pName":pName}).done(getWorkList);
	}	
}

function applyProjectFilter(user) {

	let pName = $("#project-filter-pname").val();
	let cName = $("#project-filter-cname").val();
	let status = $("#project-filter-status").val();
	
	
	let filters = {};
	
	if(pName) {
		filters.pName = pName;
	}
	
	if(cName) {
		filters.cName = cName;
	}
	
	if(window.location.pathname.includes("admin")) {
		$.getJSON("http://localhost:8080/api/projects", filters).done(getProjectList);
	} else {

		$.getJSON("http://localhost:8080/api/projects/user/" + user, filters).done(getProjectList);
	}
}

function applyCustomerFilter(user) {
	
	let cName = $("#customer-filter-cname").val();
	
	$.getJSON("http://localhost:8080/api/customers", {"cName":cName}).done(getCustomerList);
}



//=========================================
//				   LOGIN
//=========================================

function fillLogin(user) {
	$(".login #email").val(user["email"]);
	$(".login #password").val(user["password"]);
}


function addToUserSelection(users) {
    
    let userSelect = $("#user").empty();
    
    for (let user of users) {
		
		let newOption = "<option value='" + user["id"] + "'>" + user["uName"] +"</option>";
		
		userSelect.append(newOption);
    }
	
	let userSelection = userSelect[0];
	
	if(userSelection) {
	    let userChoices = new Choices(userSelection, {
	        searchEnabled: false,
	        itemSelectText: "",
			placeholderValue: "Choose User",
	    });
	}
}


$(document).ready(function() {
	
	$.getJSON("http://localhost:8080/api/users").done(addToUserSelection);

	$("#user").on("change", function () {
		
	    let userId = $(this).val();
		
	    $.getJSON("http://localhost:8080/api/user/" + userId).done(fillLogin);
	});
	
	
	$(".login-btn").on("click", function (e) {
	    e.preventDefault();

		let email = $("#email").val();
		let password = $("#password").val();
		
		$.ajax({
		    url: "http://localhost:8080/api/login",
		    type: "POST",
		    contentType: "application/json",
		    data: JSON.stringify({
		        email: email,
		        password: password,
		    }),
		    success: function(user) {
				
				if(loggedInUser = user["id"]) {

					if(user["role"] == "admin") {
						window.location.href = "admin?user=" + loggedInUser;
					} else {
						window.location.href = "user?user=" + loggedInUser;
					}
				} else {					
					errorMsg("Invalid email or password.");
				}
			},
		    error: function() {
				errorMsg("An error has occurred.");
			}
		});
	});
});



//=========================================
//				 USER VIEW
//=========================================

function addToProjectSelection(projects) {
    
    let projectSelect = $("#project-selection").empty();
    
    for(let project of projects) {
		
		let newOption = "<option value='" + project["id"] + "'>" + project["pName"] +"</option>";
		
		projectSelect.append(newOption);
    }
	
	
	let projectSelection = projectSelect[0];
	
	if(projectSelection) {
	    projectChoices = new Choices(projectSelection, {
	        itemSelectText: "",
	    });
	}
}



function getWorkList(works) {

	$(".work-list tbody").empty();
	
	if(!works.length > 0){
		$(".work-list tbody").append("<tr><td colspan='4'>No results found.</td></tr>");
	}
	
	for ( let work of works ) {
		
		let newRow = "<tr>";
		newRow += "<td>" + work["project"]["pName"] + "</td>";
		newRow += "<td>" + work["date"] + "</td>";
		newRow += "<td>" + formatDuration(work["wDuration"]) + "</td>";
		newRow += "<td class='actions' data-object='work' data-id='" + work["id"] +"'>";
		newRow += "<a class='view'></a>";
		
		
		if(window.location.pathname.includes("user")) {
			newRow += "<a class='edit'></a>";
		}
		
		newRow += "<a class='delete'></a>";
		newRow += "</td>";
		newRow += "</tr>";

		$(".work-list tbody").append(newRow);
		
	}
}


function getProjectList(projects) {
	
	$(".project-list tbody").empty();
	
	if(!projects.length > 0){
		$(".project-list tbody").append("<tr><td colspan='4'>No results found.</td></tr>");
	}
	
	for ( let project of projects ) {
		
		
		let status =  project["status"];
		let statusLabel = "";
		
		switch(status) {
			case "in-progress":
				statusLabel = "In Progress";
				break;
				
			case "on-hold":
				statusLabel = "On Hold";
				break;
			
			default:
				statusLabel = status;
				
		}
		
		
		let newRow = "<tr>";
		newRow += "<td>" + project["pName"] + "</td>";
		newRow += "<td>" + project["customer"]["cName"] + "</td>";
		newRow += "<td>" + formatDuration(project["pDuration"]) + "</td>";
		newRow += "<td>";
		newRow += "<span class='status "  + status + "'>" + statusLabel + "</span>";
		newRow += "</td>";
		
		if(window.location.pathname.includes("admin")) {
			newRow += "<td class='actions' data-object='project' data-id='" + project["id"] + "'>";
			newRow += "<a class='view'></a>";
			newRow += "<a class='edit'></a>";
			newRow += "<a class='delete'></a>";
			newRow += "</td>";
		}

		newRow += "</tr>";

		$(".project-list tbody").append(newRow);
		
	}
}


$(document).ready(function() {
	
	$.getJSON("http://localhost:8080/api/projects", {onlyActive: true}).done(addToProjectSelection);
	
	let dateSelection = $("#date-picker")[0];

	if(dateSelection) {
	    let picker = datepicker("#date-picker", {
	        startDay: 1,
	        customDays: ["Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"],
	        showAllDates: true,
			maxDate: new Date(),
	        formatter: (input, date, instance) => {
				let day = String(date.getDate()).padStart(2, '0');
				let month = String(date.getMonth() + 1).padStart(2, '0');
				let year = date.getFullYear();
				input.value = day + "." + month + "." + year;
				
	        },
	    })
	}
	

	getUsersWork(loggedInUser);
	
	getUsersProjects(loggedInUser);
	
});



//=========================================
//				 ADMIN VIEW
//=========================================




function addToCustomerSelection(customers) {
    
	$("#customer-selection").empty();

	for ( let customer of customers ) {
		
		let newOption = "<option value='" + customer["id"] + "'>" + customer["cName"] +"</option>";
		
		$("#customer-selection").append(newOption);
		
	}

	let customerSelection = $("#customer-selection")[0];

	if(customerSelection) {
	    customerChoices = new Choices(customerSelection, {
	        itemSelectText: "",
	    });
	}
}


function getCustomerList(customers) {
	
	$(".customer-list tbody").empty();
	
	if(!customers.length > 0){
		$(".customer-list tbody").append("<tr><td colspan='4'>No results found.</td></tr>");
	}
	
	for ( let customer of customers ) {
		let newRow = "<tr>";
		newRow += "<td>" + customer["cName"] + "</td>";
		newRow += "<td>" + customer["numProjects"] + "</td>";
		newRow += "<td>" + customer["place"] + "</td>";
		
		newRow += "<td class='actions' data-object='customer' data-id='" + customer["id"] + "'>";
		newRow += "<a class='view'></a>";
		newRow += "<a class='edit'></a>";
		newRow += "<a class='delete'></a>";
		newRow += "</td>";

		newRow += "</tr>";

		$(".customer-list tbody").append(newRow);
		
	}
}



$(document).ready(function() {

	$.getJSON("http://localhost:8080/api/customers").done(addToCustomerSelection);
	
    let statusSelection = $("#status-selection")[0];

    if(statusSelection) {
        statusChoices = new Choices(statusSelection, {
            searchEnabled: false,
            itemSelectText: "",
        });

   }

	$.getJSON("http://localhost:8080/api/customers").done(getCustomerList);
	
});





//=========================================
//				 	CRUD
//=========================================



// Add

$(".add-work form").on("submit", function (e) {
    e.preventDefault();
	
	let project = $("#project-selection").val();
	let date = $("#date-picker").val();
	let start = $("#start-time").val();
	let end = $("#end-time").val();
	let desc = $("#short-desc").val();
	
	let submitBtn = $(this).find("input[type='submit']");
	
	if(submitBtn.hasClass("edit-work-btn")) {
		
		let id = submitBtn.attr("data-id");
		
		$.ajax({
		    url: "http://localhost:8080/api/work/" + id,
		    type: "PUT",
		    contentType: "application/json",
		    data: JSON.stringify({
		        date: date,
		        startTime: start,
		        endTime: end,
		        description: desc,
				project: {
					id: parseInt(project)
				},
				user: {
					id: loggedInUser
				},
		    }),
		    success: function(response) {
				getUsersWork(loggedInUser);
				getUsersProjects(loggedInUser);
				$("#date-picker").val("");
				$("#start-time").val("");
				$("#end-time").val("");
				$("#short-desc").val("");
				$(".add-work .cancel-btn").remove();
				$(".add-work .edit-work-btn").removeClass("edit-work-btn").addClass("add-work-btn").removeAttr("data-id").val("Add");
				
				fireToast("Your work was successfully updated.", "update");
			},
		    error: function(xhr, status, error) {
		        fireErrorModal();
		    }
		});
		
	} else {
		
		$.ajax({
		    url: "http://localhost:8080/api/work/",
		    type: "POST",
		    contentType: "application/json",
		    data: JSON.stringify({
		        date: date,
		        startTime: start,
		        endTime: end,
		        description: desc,
				project: {
					id: parseInt(project)
				},
				user: {
					id: parseInt(loggedInUser)
				},
		    }),
		    success: function(response) {
				console.log("resp:" + response)
				
				if(!response){
					fireErrorModal("Enter only correct data.")
				} else {
					getUsersWork(loggedInUser);
					getUsersProjects(loggedInUser);
					$("#date-picker").val("");
					$("#start-time").val("");
					$("#end-time").val("");
					$("#short-desc").val("");

					fireToast("Your work was successfully added.", "save");
				}

			},
		    error: function(xhr, status, error) {
				fireErrorModal();
		    }
		});
	}	
	

});




$(".add-project form").on("submit", function (e) {
    e.preventDefault();

	let name = $("#project-name").val();
	let customer = $("#customer-selection").val();
	let status = $("#status-selection").val();
	
	
	
	let submitBtn = $(this).find("input[type='submit']");
	
	if(submitBtn.hasClass("edit-project-btn")) {
		
		let id = submitBtn.attr("data-id");
		
		
		$.ajax({
		    url: "http://localhost:8080/api/project/" + id,
		    type: "PUT",
		    contentType: "application/json",
		    data: JSON.stringify({
		       	pName: name,
		        status: status,
				customer: {
					id: customer,
				}
		    }),
		    success: function(response) {
				getUsersProjects(loggedInUser);
				$("#project-name").val("");
				$(".add-project .cancel-btn").remove();
				$(".add-project .edit-project-btn").removeClass("edit-project-btn").addClass("add-project-btn").removeAttr("data-id").val("Add");

				fireToast(name + " was successfully updated.", "update");
			},
		    error: function(xhr, status, error) {
				fireErrorModal();
		    }
		});
		
		
	} else {
		$.ajax({
		    url: "http://localhost:8080/api/project/",
		    type: "POST",
		    contentType: "application/json",
		    data: JSON.stringify({
		        pName: name,
		        status: status,
				customer: {
					id: customer,
				}
		    }),
		    success: function(response) {
				getUsersProjects(loggedInUser);
				$("#project-name").val("");
				
				fireToast(name + " was successfully added.", "save");
			},
		    error: function(xhr, status, error) {
				fireErrorModal();
		    }
		});		
	}
	

});



$(".add-customer form").on("submit", function (e) {
    e.preventDefault();


	let name = $("#customer-name").val();
	let street = $("#street").val();
	let zip = $("#zip").val();
	let place = $("#place").val();
	
	
	let submitBtn = $(this).find("input[type='submit']");
		
	if(submitBtn.hasClass("edit-customer-btn")) {
		
		let id = submitBtn.attr("data-id");
		
		$.ajax({
		    url: "http://localhost:8080/api/customer/" + id,
		    type: "PUT",
		    contentType: "application/json",
		    data: JSON.stringify({
				cName: name,
				street: street,
				zip: zip,
				place: place,
		    }),
		    success: function(response) {
				$.getJSON("http://localhost:8080/api/customers").done(getCustomerList);
				console.log(response);
				$("#customer-name").val("");
				$("#street").val("");
				$("#zip").val("");
				$("#place").val("");
				$(".add-customer .cancel-btn").remove();
				$(".add-customer .edit-customer-btn").removeClass("edit-customer-btn").addClass("add-customer-btn").removeAttr("data-id").val("Add");

				fireToast(name + " was successfully updated.", "update");
			},
		    error: function(xhr, status, error) {
				fireErrorModal();
		    }
		});
		
		
	} else {
		$.ajax({
		    url: "http://localhost:8080/api/customer/",
		    type: "POST",
		    contentType: "application/json",
		    data: JSON.stringify({
				cName: name,
				street: street,
				zip: zip,
				place: place,
		    }),
		    success: function(response) {
				$.getJSON("http://localhost:8080/api/customers").done(getCustomerList);
				console.log(response);
				$("#customer-name").val("");
				$("#street").val("");
				$("#zip").val("");
				$("#place").val("");
				

				fireToast(name + " was successfully added.", "save");
				
			},
		    error: function(xhr, status, error) {
				fireErrorModal();
		    }
		});
	}
});




//View

$(document).ready(function () {
    $("body").on("click", ".view", function (e) {
        e.preventDefault();

        let objectData = getObjectData($(this));
        let object = objectData[0];
        let id = objectData[1];

        $.getJSON("http://localhost:8080/api/" + object + "/" + id)
            .done(function (data) {
				
				title = "";
				content = "";
				
				if(object == "project") {
					
					let status =  data["status"];
					let statusLabel = "";

					switch(status) {
						case "in-progress":
							statusLabel = "In Progress";
							break;
							
						case "on-hold":
							statusLabel = "On Hold";
							break;
						
						default:
							statusLabel = status;
							
					}

					title = data["pName"];
					
					content += "<div class='cols'>";

						content += "<div class='col'>";
							content += "<span class='status "  + status + "'>" + statusLabel + "</span>";
						content += "</div>";
						
					content += "</div>";
					
					content += "<div class='cols two-cols'>";

						content += "<div class='col'>";
							content += "<p class='data-heading'>Customer</p>";
							content += "<p class='data-content'>" + data["customer"]["cName"] + "</p>";
						content += "</div>";
						
						content += "<div class='col'>";
							content += "<p class='data-heading'>Duration</p>";
							content += "<p class='data-content'>" + formatDuration(data["pDuration"]) + "</p>";
						content += "</div>";
						
					content += "</div>";
					
				} else if (object == "customer") {
					title = data["cName"];
					
					content += "<div class='cols'>";

						content += "<div class='col'>";
							content += "<p class='data-heading'>Adress</p>";
							content += "<p class='data-content'>" + data["street"] + ", " + data["zip"] + " " + data["place"] + "</p>";
						content += "</div>";
						
					content += "</div>";

					content += "<div class='cols'>";
						
						content += "<div class='col'>";
							content += "<p class='data-heading'>Number of Projects</p>";
							content += "<p class='data-content'>" + data["numProjects"] + "</p>";
						content += "</div>";
						
					content += "</div>";
					
				} else {
							
					title = "Work for " + data["project"]["pName"];

					content += "<div class='cols two-cols'>";
					
						content += "<div class='col'>";
							content += "<p class='data-heading'>Employee</p>";
							content += "<p class='data-content'>" + data["user"]["fName"] + " " + data["user"]["lName"] + "</p>";
						content += "</div>";
						
						content += "<div class='col'>";
							content += "<p class='data-heading'>Date</p>";
							content += "<p class='data-content'>" + data["date"] + "</p>";
						content += "</div>";
						
					content += "</div>";
					
					content += "<div class='cols two-cols'>";
						
						content += "<div class='col'>";
							content += "<p class='data-heading'>Time</p>";
							content += "<p class='data-content'>" + data["startTime"] + " - " + data["endTime"] + "</p>";
						content += "</div>";
						
						content += "<div class='col'>";
							content += "<p class='data-heading'>Duration</p>";
							content += "<p class='data-content'>" + formatDuration(data["wDuration"]) + "</p>";
						content += "</div>";
						
					content += "</div>";
					
					let description = data["description"]

					if(description) {
						content += "<div class='cols'>";
							
							content += "<div class='col'>";
								content += "<p class='data-heading'>Description</p>";
								content += "<p class='data-content'>" + description + "</p>";
							content += "</div>";
							
						content += "</div>";
					}
				}
				

                Swal.fire({
                    title: title,
                    html: content,
                    confirmButtonText: "Close",
					buttonsStyling: false,
					customClass: {
						popup: "view-modal",
					}
                });
            })
    });
});





//Edit

$(document).ready(function () {
    $("body").on("click", ".edit", function (e) {
        e.preventDefault();

        let objectData = getObjectData($(this));
        let object = objectData[0];
        let id = objectData[1];

        $.getJSON("http://localhost:8080/api/" + object + "/" + id)
            .done(function (data) {
				
				title = "";
				content = "";
				
				
				if(object == "project") {
					$(".add-project input[type='submit']").removeClass("add-project-btn").addClass("edit-project-btn").attr("data-id", id).val("Save");
					
					let btnContainer = $(".add-project .btn-container");
					
					if(btnContainer.find(".cancel-btn").length == 0) {

						btnContainer.append("<input type='button' value='Cancel' class='cancel-btn'>");
					}
					
					$("#project-name").val(data["pName"]);
					$("#status").val(data["status"]);
					
					if(customerChoices) {
						customerChoices.setChoiceByValue(data["customer"]["id"].toString());
					}
					
					if(statusChoices) {
						statusChoices.setChoiceByValue(data["status"].toString());
					}
					
					
					$('html').animate({
					  scrollTop: $('#project').offset().top
					}, 800);
					
					
				} else if (object == "customer") {
					$(".add-customer input[type='submit']").removeClass("add-customer-btn").addClass("edit-customer-btn").attr("data-id", id).val("Save");

					let btnContainer = $(".add-customer .btn-container");

					if(btnContainer.find(".cancel-btn").length == 0) {

						btnContainer.append("<input type='button' value='Cancel' class='cancel-btn'>");
					}
					
					$("#customer-name").val(data["cName"]);
					$("#street").val(data["street"]);
					$("#zip").val(data["zip"]);
					$("#place").val(data["place"]);
					
					$('html').animate({
					  scrollTop: $('#customer').offset().top
					}, 800);
					
				} else {
					$(".add-work input[type='submit']").removeClass("add-work-btn").addClass("edit-work-btn").attr("data-id", id).val("Save");
					
					let btnContainer = $(".add-work .btn-container");

					if(btnContainer.find(".cancel-btn").length == 0) {

						btnContainer.append("<input type='button' value='Cancel' class='cancel-btn'>");
					}
					
					$("#date-picker").val(data["date"]);
					$("#start-time").val(data["startTime"]);
					$("#end-time").val(data["endTime"]);
					$("#short-desc").val(data["description"]);
					
					if(projectChoices) {
						projectChoices.setChoiceByValue(data["project"]["id"].toString());
					}
					
					$('html').animate({
					  scrollTop: $('#work').offset().top
					}, 800);
				}
            })
    });
});

$(document).ready(function () {
    $("body").on("click", ".add-work .cancel-btn", function () {
		
		$(".add-work .cancel-btn").remove();
		$(".add-work .edit-work-btn").removeClass("edit-work-btn").addClass("add-work-btn").removeAttr("data-id").val("Add");
		$("#date-picker").val("");
		$("#start-time").val("");
		$("#end-time").val("");
		$("#short-desc").val("");
    });
});


$(document).ready(function () {
    $("body").on("click", ".add-project .cancel-btn", function () {
		
		$(".add-project .cancel-btn").remove();
		$(".add-project .edit-project-btn").removeClass("edit-project-btn").addClass("add-project-btn").removeAttr("data-id").val("Add");
		$("#project-name").val("");
    });
});



$(document).ready(function () {
    $("body").on("click", ".add-customer .cancel-btn", function () {
		
		$(".add-customer .cancel-btn").remove();
		$(".add-customer .edit-customer-btn").removeClass("edit-customer-btn").addClass("add-customer-btn").removeAttr("data-id").val("Add");
		$("#customer-name").val("");
		$("#street").val("");
		$("#zip").val("");
		$("#place").val("");
    });
});


//Delete

$(document).ready(function() {
	
	$("body").on("click", ".delete", function (e) { //Problem, da Element bei ready nicht existiert, daher => Event Delegation

		let objectData = getObjectData($(this));
		let object = objectData[0];
		let id = objectData[1];
		
		let objectText = "";
		
		if(object == "work") {
			objectText = "Your work";
		} else {
			objectText = object.charAt(0).toUpperCase() + object.slice(1);
		}
		
		Swal.fire({
		  title: "Are you sure?",
		  html: "<span class='subheading'>If you click on delete, " + objectText + " can no longer be restored!</span>",
		  text: "If you click on delete, your work can no longer be restored.",
		  showCancelButton: true,
		  confirmButtonText: "Delete",
		  buttonsStyling: false,
		  customClass: {
			popup: "delete-modal",
		  }
		}).then((result) => {
		  if (result.isConfirmed) {
			
			$.ajax({
				url: "http://localhost:8080/api/" + object + "/" + id,
				type: "DELETE",
				success: function() {
					
					fireToast(objectText + " was successfully deleted.", "delete")
					
					
					if(window.location.pathname.includes("admin")) {
						$.getJSON("http://localhost:8080/api/customers").done(getCustomerList);
					}
					

					getUsersProjects(loggedInUser);
					getUsersWork(loggedInUser);
				},
				error: function(xhr, status, error) {
					fireErrorModal();
				}
			})			
		  }
		});
	});
});

