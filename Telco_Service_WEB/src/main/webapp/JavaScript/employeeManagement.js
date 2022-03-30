

(function() {
	
	var pageOrchestrator = new PageOrchestrator();
	var serviceType;
	 
	window.addEventListener("load", () => {
		      pageOrchestrator.start(); // initialize the components
		      pageOrchestrator.refresh();
		     // display initial content
		  }, false);

	function CreateOptionalProduct(_creationForm){
		
		this.creationform = _creationForm;
	 	
	 	this.registerEvents = function() {	
	 	 	
			document.getElementById("opt_prod_creation_button").addEventListener("click", (e) => {
			e.preventDefault();
	         
	          makeCall("POST", "CreateOptionalProduct", this.creationform,
	            function(req) {
	              if (req.readyState == XMLHttpRequest.DONE) {
	                var message = req.responseText; // error message
		                 switch (req.status) {
			              case 200:    
			            	pageOrchestrator.refresh();	
							document.getElementById("optionalProductCreationResult").textContent = "Otional Product Created Correctly!";	
							document.getElementById("optionalProductCreationResult").style.color = "green";	                
			                break;
			              case 400: // bad request
			                document.getElementById("optionalProductCreationResult").textContent = message;
							document.getElementById("optionalProductCreationResult").style.color = "red";
			                break;
			              case 401: // unauthorized
			                document.getElementById("optionalProductCreationResult").textContent = message;
							document.getElementById("optionalProductCreationResult").style.color = "red";
			                  break;
			              case 500: // server error
			            	document.getElementById("optionalProductCreationResult").textContent = message;
							document.getElementById("optionalProductCreationResult").style.color = "red";
			                break;
			            }
	              }
	            });
	        });
	    }
	}
	
	function CreateService(_creationForm){
		
		this.creationForm = _creationForm;
		
		this.registerEvents = function() {
			
			document.getElementById("service_creation_button").addEventListener("click", (e) => {
				e.preventDefault();
							
				makeCall("POST", "CreateService?type="+serviceType, this.creationForm,
		            function(req) {
		              if (req.readyState == XMLHttpRequest.DONE) {
		                var message = req.responseText; // error message
		                 switch (req.status) {
			              case 200:    
			            	pageOrchestrator.refresh();	
							document.getElementById("serviceCreationResult").textContent = "Service Created Correctly!";	
							document.getElementById("serviceCreationResult").style.color = "green";	                
			                break;
			              case 400: // bad request
			                document.getElementById("serviceCreationResult").textContent = message;
							document.getElementById("serviceCreationResult").style.color = "red";
			                break;
			              case 401: // unauthorized
			                document.getElementById("serviceCreationResult").textContent = message;
							document.getElementById("serviceCreationResult").style.color = "red";
			                  break;
			              case 500: // server error
			            	document.getElementById("serviceCreationResult").textContent = message;
							document.getElementById("serviceCreationResult").style.color = "red";
			                break;
						  case 409: // server error
			            	document.getElementById("serviceCreationResult").textContent = message;
							document.getElementById("serviceCreationResult").style.color = "red";
			                break;
			            }
		              }
		            });	
				
			});
			
		}
	}
	
	function CreateServicePackage(_creationForm){
		
		this.creationForm = _creationForm;
		
		this.registerEvents = function() {
			
			document.getElementById("createPackageButtonId").addEventListener("click", (e) => {
				e.preventDefault();
							
				makeCall("POST", "CreateServicePackage", this.creationForm,
		            function(req) {
		              if (req.readyState == XMLHttpRequest.DONE) {
		                var message = req.responseText; // error message
		                 switch (req.status) {
			              case 200:    
			            	pageOrchestrator.refresh();	
							document.getElementById("servicePackageCreationResult").textContent = "Service Package Created Correctly!";	
							document.getElementById("servicePackageCreationResult").style.color = "green";	                
			                break;
			              case 400: // bad request
			                document.getElementById("servicePackageCreationResult").textContent = message;
							document.getElementById("servicePackageCreationResult").style.color = "red";
			                break;
			              case 401: // unauthorized
			                document.getElementById("servicePackageCreationResult").textContent = message;
							document.getElementById("servicePackageCreationResult").style.color = "red";
			                  break;
			              case 500: // server error
			            	document.getElementById("servicePackageCreationResult").textContent = message;
							document.getElementById("servicePackageCreationResult").style.color = "red";
			                break;
						  case 409: // server error
			            	document.getElementById("servicePackageCreationResult").textContent = message;
							document.getElementById("servicePackageCreationResult").style.color = "red";
			                break;
			            }
		              }
		            });	
				
			});
			
		}
	}
	
	function GetServices(){
	 	 	
	 	this.show = function(next) {
		  document.getElementById("serviceSelectId").innerHTML = "";	
	      var self = this;
	      makeCall("GET", "GetServices", null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              var services = JSON.parse(req.responseText);
	              	
					services.forEach(function(service) {
						option = document.createElement('option');
					    option.value = service.serviceId;
					    option.textContent = service.name;
					    var x = document.getElementById("serviceSelectId");
					    x.add(option);
					})
	              
	              if (next) next(); // show the default element of the list if present
	            }
	          } else {
	            ;
	          }
	        }
	      );
	    }; 	
	 }

	function GetOptionalProducts(){
	 	 	
	 	this.show = function(next) {
		  document.getElementById("optionalSelectId").innerHTML = "";	
	      var self = this;
	      makeCall("GET", "GetOptionalProducts", null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              var products = JSON.parse(req.responseText);
	              	
					products.forEach(function(product) {
						option = document.createElement('option');
					    option.value = product.optionalProductId;
					    option.textContent = product.name;
					    var x = document.getElementById("optionalSelectId");
					    x.add(option);
					})
	              
	              if (next) next(); // show the default element of the list if present
	            }
	          } else {
	            ;
	          }
	        }
	      );
	    }; 	
	 }
	
	      
	function SetCursor(){
		this.registerEvents = function(){
			var min = document.getElementById("minutes");
			var sms = document.getElementById("sms");
			var gigabytes = document.getElementById("gigabytes");
			var minFee = document.getElementById("minutesFee");
			var smsFee = document.getElementById("smsFee");
			var gigabytesFee = document.getElementById("gigabytesFee");
				
			if(serviceType=="fixed_phone"){
				sms.readOnly = true;
				smsFee.readOnly = true;
				gigabytes.readOnly = true;
				gigabytesFee.readOnly = true;
				minutes.readOnly = false;
				minutesFee.readOnly = false;
				
				sms.addEventListener('mouseover', (e) => {
					sms.style.cursor = "not-allowed";	
				});
				smsFee.addEventListener('mouseover', (e) => {
					smsFee.style.cursor = "not-allowed";
				});
				gigabytes.addEventListener('mouseover', (e) => {
					gigabytes.style.cursor = "not-allowed";
				});
				gigabytesFee.addEventListener('mouseover', (e) => {
					gigabytesFee.style.cursor = "not-allowed";
				});
				minutes.addEventListener('mouseover', (e) => {
					minutes.style.cursor = "auto";
				});
				minutesFee.addEventListener('mouseover', (e) => {
					minutesFee.style.cursor = "auto";
				});
			}
			else if(serviceType=="fixed_internet" || serviceType=="mobile_internet"){
				sms.readOnly = true;
				smsFee.readOnly = true;
				gigabytes.readOnly = false;
				gigabytesFee.readOnly = false;
				minutes.readOnly = true;
				minutesFee.readOnly = true;
				
				sms.addEventListener('mouseover', (e) => {
					sms.style.cursor = "not-allowed";
				});
				smsFee.addEventListener('mouseover', (e) => {
					smsFee.style.cursor = "not-allowed";
				});
				minutes.addEventListener('mouseover', (e) => {
					minutes.style.cursor = "not-allowed";
				});
				minutesFee.addEventListener('mouseover', (e) => {
					minutesFee.style.cursor = "not-allowed";
				});
				gigabytes.addEventListener('mouseover', (e) => {
					gigabytes.style.cursor = "auto";
				});
				gigabytesFee.addEventListener('mouseover', (e) => {
					gigabytesFee.style.cursor = "auto";
				});
			}
			else if(serviceType=="mobile_phone"){
				sms.readOnly = false;
				smsFee.readOnly = false;
				gigabytes.readOnly = true;
				gigabytesFee.readOnly = true;
				minutes.readOnly = false;
				minutesFee.readOnly = false;
				
				gigabytes.addEventListener('mouseover', (e) => {
					gigabytes.style.cursor = "not-allowed";
				});
				gigabytesFee.addEventListener('mouseover', (e) => {
					gigabytesFee.style.cursor = "not-allowed";
				});
				minutes.addEventListener('mouseover', (e) => {
					minutes.style.cursor = "auto";
				});
				minutesFee.addEventListener('mouseover', (e) => {
					minutesFee.style.cursor = "auto";
				});
				sms.addEventListener('mouseover', (e) => {
					sms.style.cursor = "auto";
				});
				smsFee.addEventListener('mouseover', (e) => {
					smsFee.style.cursor = "auto";
				});
			}
		
	
		}
	}
		
	


	function PersonalMessage(_username, messagecontainer) {
	    this.username = _username;
	    this.show = function() {
	      messagecontainer.textContent = "Welcome back, " + this.username;
	    }
	  }	

	
	function SetToPointer() {
		this.registerEvents = function() {
			document.getElementById("logoutbutton").addEventListener('mouseover', (e) => {
				document.getElementById("logoutbutton").style.cursor = "pointer";
			});
			
			document.getElementById("salesreport").addEventListener('mouseover', (e) => {
				document.getElementById("salesreport").style.cursor = "pointer";
			});
			
			document.getElementById("opt_prod_creation_button").addEventListener('mouseover', (e) => {
				document.getElementById("opt_prod_creation_button").style.cursor = "pointer";
			});
			
			document.getElementById("service_creation_button").addEventListener('mouseover', (e) => {
				document.getElementById("service_creation_button").style.cursor = "pointer";
			});
			
			document.getElementById("createPackageButtonId").addEventListener('mouseover', (e) => {
				document.getElementById("createPackageButtonId").style.cursor = "pointer";
			});
		}
	}
	
	function PageOrchestrator() {
	
	   this.start = function() {
			   
		if (sessionStorage.getItem("employee") == null) {
			window.location.href = "loginpage.html";
	    } else{
			
			personalMessage = new PersonalMessage(window.sessionStorage.getItem("employee"), document.getElementById("welcomemessage"));
	     	personalMessage.show();
		}
		
		//Graphics for the create service component
		serviceType = "fixed_phone";
		
		setCursor = new SetCursor();
		setCursor.registerEvents();
		
		document.getElementById("select_service_type").addEventListener("change", (e) => {
			e.preventDefault();
			serviceType = e.target.value;
			var imageType = document.getElementById("imagetype");
			imageType.src = "http://localhost:8080/Telco_Service_WEB/GetImage?name="+serviceType+".jpg";
			setCursor.registerEvents();
		});
		
			  
		document.getElementById("logoutbutton").addEventListener('click', (e) => {
			e.preventDefault();
			window.sessionStorage.removeItem('employee');
			makeCall("POST", 'Logout', null,
        	function(req) {
        		//req.readyState == la risposta è arrivata dal server in maniera asincrona!!
          		if (req.readyState == XMLHttpRequest.DONE) {
            		switch (req.status) {
              			case 200: 
            				window.location.href = "loginpage.html";		
                			break;
            		}
          		}
        	});
			
		});
		
		document.getElementById("salesreport").addEventListener('click', (e) => {
			e.preventDefault();
			window.location.href = "salesreportpage.html";
		});
	
		createOptionalProduct = new CreateOptionalProduct(document.getElementById("optionalProductForm"));
		createOptionalProduct.registerEvents();
		
		createService = new CreateService(document.getElementById("serviceCreationForm"));
		createService.registerEvents();
		
		createServicePackage = new CreateServicePackage(document.getElementById("servicePackageCreationForm"));
		createServicePackage.registerEvents();
		
		setToPointer = new SetToPointer();
		setToPointer.registerEvents();
		
		services = new GetServices();
		
		optionalProducts = new GetOptionalProducts();
		
	    };
	
	
	    this.refresh = function() {
	      optionalProducts.show();
		  services.show();
	    };
	 } 
	
})();