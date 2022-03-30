

(function() {
	
	var pageOrchestrator = new PageOrchestrator();
	var currentPackage, showCurrentPackage, packages, numberOfPackages, packageToShow;
	 
	window.addEventListener("load", () => {
		      pageOrchestrator.start(); // initialize the components
		      pageOrchestrator.refresh();
		     // display initial content
		  }, false);


	function UnpaidOrders(){
		this.show = function(){
			var self = this;
			makeCall("GET", "GetOrdersToBePaid", null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              orders = JSON.parse(req.responseText);
	              if (orders.length == 0) {
					document.getElementById("unpaidOrders").style.visibility = "hidden";
					document.getElementById("slideshow").style.marginTop = "-20px";     
	                return;
	              }
				  else{
	              	self.update(orders);  
	              }
	            }
	          } 
	        }
	      );
		}
		
		this.update = function(orders){
			var table = document.getElementById("unpaidOrders");
			var row, idcell, idh, costcell, costh, statuscell, paycell, packagecell, packageh, statush;
			
			table.innerHTML="";	
			
			row = document.createElement("caption");
			row.className="unpaidTitle";
			row.textContent = "You have one or more orders with pending payment!";
			table.appendChild(row);
			
			row = document.createElement("tr");
			row.className="tableHeader";
			
			idh = document.createElement("th");
			idh.textContent="Order Number";
			row.appendChild(idh);
			
			packageh = document.createElement("th");
			packageh.textContent="Package Name";
			row.appendChild(packageh);
			
			costh = document.createElement("th");
			costh.textContent="Total Cost";
			row.appendChild(costh);
			
			statush = document.createElement("th");
			statush.textContent="Status";
			row.appendChild(statush);
			
			
			table.appendChild(row);
			
			orders.forEach(function(order) { // self visible here, not this
		        row = document.createElement("tr");
		        row.className = "orderRow";
		        
				idcell = document.createElement("td");
				idcell.textContent = "#"+order.orderId;
				idcell.className="tablecol";
				row.appendChild(idcell);
				
				packagecell = document.createElement("td");
				packagecell.textContent = order.servicePackage.name;
				packagecell.className="tablecol";
				row.appendChild(packagecell);
	
		        costcell = document.createElement("td");
		        costcell.textContent = +order.totalCost+"€ / Month";
				costcell.className="tablecol";
		        row.appendChild(costcell);
	
				statuscell = document.createElement("td");
				if(order.status==0){
					statuscell.textContent = "Unpaid";
					statuscell.className="tablecol";
				}
				else if(order.status==1){	
					statuscell.textContent = "Rejected";
					statuscell.className="tablecol";	
				}
	        	row.appendChild(statuscell);

				paycell = document.createElement("td");
				paycell.className="tablecol";
		        var paybutton = document.createElement("input");
				paybutton.type = "button";
				paybutton.value = "Pay Now!"
				paybutton.className="paybutton";
				paybutton.setAttribute('orderId', order.orderId);
				paybutton.addEventListener("click", (e)=>{
					window.sessionStorage.setItem("order", paybutton.getAttribute("orderId"));
					window.location.href = "confirmationpage.html";
				});
				
				paycell.append(paybutton);
	
		        row.appendChild(paycell);
				        
		        table.appendChild(row);
	      	}); 
		}
	}

	function PersonalMessage(_username, messagecontainer) {
	    this.username = _username;
	    this.show = function() {
	      messagecontainer.textContent = "Welcome back, " + this.username;
	    }
	  }	

	function ShowCurrentPackage(){
		this.show = function(){

			var slideShow = document.getElementById("slideshow");
			document.getElementById("numberDisplay").textContent = currentPackage + " / " + numberOfPackages;
			
			if(currentPackage == -1)
				return;
			packageToShow = packages[currentPackage-1];
			
			//Display The title of the package
			var tbd = document.getElementById("packageName");
			if(tbd != null)
				tbd.remove();
			
			var pckName = document.createElement("p");
			pckName.setAttribute('id', 'packageName');
			pckName.setAttribute('class', 'fade');
			pckName.textContent = packageToShow.name;
			slideShow.append(pckName);
			
	     	//Display the services included in the package
			var tbd2 = document.getElementsByClassName("serviceClass");
			while(tbd2.length > 0){
				tbd2[0].remove();
			}
			
			var services = packageToShow.services;
			var p;
			for(i=0; i<services.length; i++){
				p = document.createElement("p");
				p.className = "serviceClass fade";
				p.textContent = services[i].name;
				document.getElementById("servicesContainer").appendChild(p);
				
			}
		}
	}

	function GetServicePackages(_alert){
		
		this.alert = _alert;

		this.show = function(next) {
			var self = this;
			makeCall("GET", "GetServicePackages", null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              packages = JSON.parse(req.responseText);
	              if (packages.length == 0) {
	                self.alert.textContent = "We're sorry but it seems like there are no packages available'";
					currentPackage = -1;
					numberOfPackages = 0;
	                return;
	              }
				  else{
	              	currentPackage = 1;
					numberOfPackages = packages.length;   
	              }
	              showCurrentPackage.show(); 
				  self.showButtons(packages);
	              if (next) next(); // show the default element of the list if present
	            }
	          } else {
	            self.alert.textContent = message;
	          }
	        }
	      );			
		};
		
		this.showButtons = function(packages){
			var dot;
			for(i=0; i<packages.length; i++){
				dot = document.createElement("span");
				dot.className = "dot";
				dot.setAttribute('packageNumber', i+1);
				
				document.getElementById("circles-container").appendChild(dot);
				
				dot.addEventListener('click', (e) => {
					currentPackage = parseInt(e.target.getAttribute('packageNumber'));
					showCurrentPackage.show();
				});
			}
		}
	}
		
	function PageOrchestrator() {
	
	   this.start = function() {
			   
		if (sessionStorage.getItem("consumer") == null) {
			document.getElementById("loginbutton").style.visibility = "visible";
			document.getElementById("logoutbutton").style.visibility = "hidden";
	    } else{
			document.getElementById("loginbutton").style.visibility = "hidden";
			document.getElementById("logoutbutton").style.visibility = "visible";
			
			personalMessage = new PersonalMessage(window.sessionStorage.getItem("consumer"), document.getElementById("welcomemessage"));
	     	personalMessage.show();
		}
		
		var alert = document.getElementById("alert");
		
		servicePackages = new GetServicePackages(alert);
		servicePackages.show();
		
		showCurrentPackage = new ShowCurrentPackage();
		
		unpaidOrders = new UnpaidOrders();
		if(window.sessionStorage.getItem('consumer') != null){
			unpaidOrders.show();
		}
		else {
			document.getElementById("unpaidOrders").style.visibility = "hidden";
			document.getElementById("slideshow").style.marginTop = "-20px"; 
		}
		
		document.getElementById("loginbutton").addEventListener('click', (e) => {
			e.preventDefault();
			window.location.href = "loginpage.html";
		});
			  
		document.getElementById("logoutbutton").addEventListener('click', (e) => {
			e.preventDefault();
			window.sessionStorage.removeItem('consumer');
			makeCall("POST", 'Logout', null,
        	function(req) {
        		//req.readyState == la risposta è arrivata dal server in maniera asincrona!!
          		if (req.readyState == XMLHttpRequest.DONE) {
            		switch (req.status) {
              			case 200: 
            				window.location.href = "consumerpage.html";	
                			break;
            		}
          		}
        	});
		});
		
		document.getElementById("prevArrow").addEventListener('click', (e) => {
			e.preventDefault();
			currentPackage = currentPackage - 1;
			if(currentPackage <= 0)
				currentPackage = numberOfPackages;
			showCurrentPackage.show();
			
		});
		
		document.getElementById("nextArrow").addEventListener('click', (e) => {
			e.preventDefault();
			currentPackage = currentPackage + 1;
			if(currentPackage > numberOfPackages)
				currentPackage = 1;
			showCurrentPackage.show();
		});
		
		document.getElementById("buyButton").addEventListener('click', (e) => {
			e.preventDefault();
			
			window.sessionStorage.setItem('package', packageToShow.packageId);
			window.location.href = "buypage.html";
		});
		
	    };
	
	
	    this.refresh = function() {
	      
	    };
	 } 	
	})();