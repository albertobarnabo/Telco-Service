

(function() {
	
	var pageOrchestrator = new PageOrchestrator();
	var packageId, currentPackage, showPackageDetails, showOptionalProducts, calculateTotal, products, services, chosenValidity;
	 
	window.addEventListener("load", () => {
		      pageOrchestrator.start(); // initialize the components
		      pageOrchestrator.refresh();
		     // display initial content
		  }, false);


	function GetServicePackage(_alert){
		
		this.alert = _alert;

		this.show = function() {
			var self = this;
			makeCall("GET", "GetPackageById?packageId="+packageId, null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              currentPackage = JSON.parse(req.responseText);
				  document.getElementById("greetings").textContent = "Thank you for selecting the " + currentPackage.name + " package!";
				  showPackageDetails.show();
				  showOptionalProducts.show();
				  chosenValidity = 12;
				  calculateTotal.show();	 	
	            }
	          } else {
	            self.alert.textContent = message;
	          }
	        }
	      );			
		}; 
	}
	
	function ShowPackageDetails(){
		this.show = function(){
			services = currentPackage.services;
			var elem, i, row, namecell, datecell, imgCell;
	  		
	      	document.getElementById("serviceDetailsContainer").innerHTML = ""; // empty the table body
	      	// build updated list

	      	services.forEach(function(service) { // self visible here, not this
		        row = document.createElement("tr");
		        row.className = "serviceRow";
		        
				imgCell = document.createElement("img");
				imgCell.className = "imageType";
				imgCell.src = "http://localhost:8080/Telco_Service_WEB/GetImage?name="+service.type+".jpg";
		        row.appendChild(imgCell);
	
		        namecell = document.createElement("td");
		        namecell.textContent = service.name;
		        row.appendChild(namecell);

				if(service.minutes>0){
					datecell = document.createElement("td");
					datecell.textContent = "Minutes: "+service.minutes;
					row.appendChild(datecell);
				}
	        	
				if(service.sms>0){
					datecell = document.createElement("td");
					datecell.textContent = "Sms: "+service.sms;
					row.appendChild(datecell);
				}
				
				if(service.gigabytes>0){
					datecell = document.createElement("td");
					datecell.textContent = "Gigabytes: "+service.gigabytes;
					row.appendChild(datecell);
				}
				
				if(service.minutesFee>0){
					datecell = document.createElement("td");
					datecell.textContent = "Extra minutes fee: "+service.minutesFee +" cent / minute";
					row.appendChild(datecell);
				}
				
				if(service.smsFee>0){
					datecell = document.createElement("td");
					datecell.textContent = "Extra sms fee: "+service.smsFee+" cent / sms";
					row.appendChild(datecell);
				}
				
				if(service.gigabytesFee>0){
					datecell = document.createElement("td");
					datecell.textContent = "Extra gigabytes Fee: "+service.gigabytesFee+" € / gigabyte";
					row.appendChild(datecell);
				}
				        
		        document.getElementById("serviceDetailsContainer").appendChild(row);
	      	});      
		}
	}
	
	function ShowOptionalProducts(){
		this.show = function(){
			products = currentPackage.optionalProducts;
			var option, label;
			var i = 1;
			if(products.length == 0)
				document.getElementById("servicetitle").style.marginTop = "17%";
			else if(products.length == 1)
				document.getElementById("servicetitle").style.marginTop = "13%";
			else if(products.length == 2)
				document.getElementById("servicetitle").style.marginTop = "10%";
			else if(products.length == 3)
				document.getElementById("servicetitle").style.marginTop = "8%";
				
			products.forEach(function(product) {
						option = document.createElement('input');
						option.type = "checkbox";
						option.value = product.optionalProductId;
						option.name="selectedOptionalProducts";
						option.setAttribute('class','checkBox');
						option.setAttribute('id','checkBox'+i);
						option.setAttribute('index', i);
						
						option.addEventListener('change', (e) => {
							e.preventDefault();
							calculateTotal.show();
						});
						
						label = document.createElement('label');
						label.textContent = product.name + ": " + product.fee + " € / Month";
						label.setAttribute('class', 'optionalLabel');
						label.setAttribute('id', 'optionalLabel'+i);
						label.setAttribute('for', 'checkBox'+i);
						
					    document.getElementById("selectionForm").appendChild(option);
						document.getElementById("selectionForm").appendChild(label);
	 					document.getElementById("selectionForm").appendChild(document.createElement('br'));
						document.getElementById("selectionForm").appendChild(document.createElement('br'));
					    i++;
			})
		}
	}

	function PersonalMessage(_username, messagecontainer) {
	    this.username = _username;
	    this.show = function() {
	      messagecontainer.textContent = "Welcome back, " + this.username;
	    }
	  }	

	function CreateOrder(_creationForm){
		
		this.creationForm = _creationForm;
		
		this.registerEvents = function() {
			
			document.getElementById("confirmButton").addEventListener("click", (e) => {
				e.preventDefault();
				
				if(sessionStorage.getItem("consumer") == null){
					window.location.href = "loginpage.html";
					return;
				}
							
				makeCall("POST", "CreateOrder?packageId="+packageId, this.creationForm,
		            function(req) {
		              if (req.readyState == XMLHttpRequest.DONE) {
		                var message = req.responseText; // error message
		                 switch (req.status) {
			              case 200:    
							window.sessionStorage.removeItem('package');
							window.sessionStorage.setItem('order', message);
			            	window.location.href = "confirmationpage.html";             
			                break;
			              case 400: // bad request
			                document.getElementById("alert").textContent = message;
							document.getElementById("alert").style.color = "red";
			                break;
			              case 401: // unauthorized
			                document.getElementById("alert").textContent = message;
							document.getElementById("alert").style.color = "red";
			                  break;
			              case 500: // server error
			            	document.getElementById("alert").textContent = message;
							document.getElementById("alert").style.color = "red";
			                break;
						  case 409: // server error
			            	document.getElementById("alert").textContent = message;
							document.getElementById("alert").style.color = "red";
			                break;
			            }
		              }
		            });		
			});
		}
	}

	function CalculateTotal(){
		
		this.show = function(){
			var total;
			
			document.getElementById('amount').innerHTML = "";
			
			if(chosenValidity == 12)
				total = currentPackage.fee12;
			else if(chosenValidity == 24)
				total = currentPackage.fee24;	
			else
				total = currentPackage.fee36;
			
			
			var checkboxes = document.querySelectorAll("input[type=checkbox]");
			var index, sum;
			
			checkboxes.forEach(function(checkbox){
				if(checkbox.checked == true){
					index = checkbox.getAttribute('index');
					sum = products[index-1].fee;
					total = total + sum;
				}
			});
			
			document.getElementById('amount').textContent = total + ' € / Month';
		}
	}
		
	function PageOrchestrator() {
	
	   this.start = function() {
		
		packageId = window.sessionStorage.getItem('package');
		
		if(packageId == null){
			window.location.href = "consumerpage.html";
		}
			   
		if (sessionStorage.getItem("consumer") == null) {
			document.getElementById("loginbutton").style.visibility = "visible";
			document.getElementById("logoutbutton").style.visibility = "hidden";
	    } else{
			document.getElementById("loginbutton").style.visibility = "hidden";
			document.getElementById("logoutbutton").style.visibility = "visible";
			
			personalMessage = new PersonalMessage(window.sessionStorage.getItem("consumer"), document.getElementById("welcomemessage"));
	     	personalMessage.show();
		}
		
		getServicePackage = new GetServicePackage(document.getElementById("alert"));
		getServicePackage.show();
		
		showPackageDetails = new ShowPackageDetails();
		showOptionalProducts = new ShowOptionalProducts();
		calculateTotal = new CalculateTotal();
		
		createOrder = new CreateOrder(document.getElementById("selectionForm"));
		createOrder.registerEvents();
		
		document.getElementById("loginbutton").addEventListener('click', (e) => {
			e.preventDefault();
			window.location.href = "loginpage.html";
		});
			  
		document.getElementById("logoutbutton").addEventListener('click', (e) => {
			e.preventDefault();
			window.sessionStorage.removeItem('consumer');
			window.location.href = "buypage.html";
		});
		
		document.getElementById("homeIcon").addEventListener('click', (e) => {
			e.preventDefault();
			window.sessionStorage.removeItem('package');
			window.location.href = "consumerpage.html";
		});
		
		document.getElementById("validitySelectId").addEventListener("change", (e) => {
			e.preventDefault();
			chosenValidity = parseInt(e.target.value);
			calculateTotal.show();
		});
		
		//Disables past dates
		var dtToday = new Date();
    
    	var month = dtToday.getMonth() + 1;
    	var day = dtToday.getDate();
    	var year = dtToday.getFullYear();
    	if(month < 10)
     	   month = '0' + month.toString();
	    if(day < 10)
	        day = '0' + day.toString();
	    
	    var minDate = year + '-' + month + '-' + day;
	    document.getElementById('start').setAttribute('min', minDate);
		
		
		
		
	    };
	
	    this.refresh = function() {
	      
	    };
	 } 	
	})();