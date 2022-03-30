

(function() {
	
	var pageOrchestrator = new PageOrchestrator();
	var reports, bestSellers, insolvents, suspendedOrders;
	 
	window.addEventListener("load", () => {
		      pageOrchestrator.start(); // initialize the components
		      pageOrchestrator.refresh();
		     // display initial content
		  }, false);


	function GetReport(_alert){
		
		this.alert = _alert;

		this.show = function() {
			var self = this;
			makeCall("GET", "GetServicePackagesReport", null,
	        function(req) {
	          if (req.readyState == 4) {
	            if (req.status == 200) {
	              reports = JSON.parse(req.responseText);
				  //When done, find also the best seller optional products
				  makeCall("GET", "GetBestSellerOptionalProducts", null,
					  function(req) {
						  if (req.readyState == 4) {
							  if (req.status == 200) {
								  bestSellers = JSON.parse(req.responseText);
								  //When done, get all the insolvent users
								  makeCall("GET", "GetInsolventUsers", null,
								  	function(req) {
									  if (req.readyState == 4) {
										  if (req.status == 200) {
											  insolvents = JSON.parse(req.responseText);
											  //When done, get all the suspended orders
								  			  makeCall("GET", "GetSuspendedOrders", null,
													  function(req) {
														  if (req.readyState == 4) {
															  if (req.status == 200) {
																  suspendedOrders = JSON.parse(req.responseText);
																  self.update();
															  }
														  }
													  }
												  );
											  }
										  }
									  }
								  );
							  }
						  }
					  }
				  );				  
	            }
	          } 
	        }
			);
		};

		this.update = function() {
			var table = document.getElementById("reportTable");
			var row, header, datacell;
			
			table.innerHTML = "";
			//We start off with the package report
			//Table caption
			row = document.createElement("caption");
			row.textContent = "Sales Report";
			row.className = "tabletitle";
			table.appendChild(row);
			
			row = document.createElement("tr");
			//Create all the needed headers and append them to the first row
			header = document.createElement("th");
			header.textContent = "Package Number";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Total Purchases";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Purchases with 12 months as validity";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Purchases with 24 months as validity";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Purchases with 36 months as validity";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Total Revenue";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Total Revenue including Optional Products";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Average Number of Optional Products";
			header.className = "header";
			row.appendChild(header);
			
			//Append the row of headers to the table
			table.appendChild(row);
			
			reports.forEach(function(report){
				row = document.createElement("tr");
				
				datacell = document.createElement("td");
				datacell.textContent = "#"+report.packageId;
				datacell.className = "reportCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = report.totalPurchases;
				datacell.className = "reportCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = report.purchases12M;
				datacell.className = "reportCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = report.purchases24M;
				datacell.className = "reportCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = report.purchases36M;
				datacell.className = "reportCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = report.totalRevenue;
				datacell.className = "reportCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = report.totalRevenueWithOptionals;
				datacell.className = "reportCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				if(parseFloat(report.totalPurchases) != 0)
					datacell.textContent = (parseFloat(report.numberOfOptionalProducts)) / (parseFloat(report.totalPurchases));
				else
					datacell.textContent = "0";
				datacell.className = "reportCell";
				row.appendChild(datacell);
				
				table.appendChild(row);
			});
			
			//Create the table for the best seller optional products
			var bestSellerTable = document.getElementById("bestSellerTable");
			
			bestSellerTable.innerHTML = "";
			
			//Caption
			row = document.createElement("caption");
			row.textContent = "Best Seller Optional Products";
			row.className = "tabletitle";
			bestSellerTable.appendChild(row);
			
			row = document.createElement("tr");
			//Headers
			header = document.createElement("th");
			header.textContent = "Optional Product Name";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Total Number of Sales";
			header.className = "header";
			row.appendChild(header);
			
			bestSellerTable.appendChild(row);
			
			//Table data: one for each best Seller
			bestSellers.forEach(function(bestSeller){
				row = document.createElement("tr");
				
				datacell = document.createElement("td");
				datacell.textContent = bestSeller.name;
				datacell.className = "bestSellerCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = bestSeller.totalSales;
				datacell.className = "bestSellerCell";
				row.appendChild(datacell);
				
				bestSellerTable.appendChild(row);
			});
			
			
			//Insolvent Users
			var insolventTable = document.getElementById("insolentUsersTable");
			insolventTable.innerHTML = "";
			
			//Caption
			row = document.createElement("caption");
			row.textContent = "Insolvent Users";
			row.className = "tabletitle";
			insolventTable.appendChild(row);
			
			row = document.createElement("tr");
			//Headers
			header = document.createElement("th");
			header.textContent = "User ID";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Username";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Email";
			header.className = "header";
			row.appendChild(header);
			
			insolventTable.appendChild(row);
			
			//Table data: one for each best Seller
			insolvents.forEach(function(insolvent){
				row = document.createElement("tr");
				
				datacell = document.createElement("td");
				datacell.textContent = insolvent.consumerId;
				datacell.className = "insolventCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = insolvent.username;
				datacell.className = "insolventCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = insolvent.email;
				datacell.className = "insolventCell";
				row.appendChild(datacell);
				
				insolventTable.appendChild(row);
			});
			
			//Suspended Orders
			var suspendedTable = document.getElementById("suspendedOrdersTable");
			suspendedTable.innerHTML = "";
			
			
			//Caption
			row = document.createElement("caption");
			row.textContent = "Suspended Orders";
			row.className = "tabletitle";
			suspendedTable.appendChild(row);
			
			row = document.createElement("tr");
			//Headers
			header = document.createElement("th");
			header.textContent = "Order ID";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Service Package";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Consumer ID";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Total Cost";
			header.className = "header";
			row.appendChild(header);
			
			header = document.createElement("th");
			header.textContent = "Status";
			header.className = "header";
			row.appendChild(header);
			
			suspendedTable.appendChild(row);
			
			//Table data: one for each best Seller
			suspendedOrders.forEach(function(order){
				row = document.createElement("tr");
				
				datacell = document.createElement("td");
				datacell.textContent = '#'+order.orderId;
				datacell.className = "insolventCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = order.servicePackage;
				datacell.className = "insolventCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = order.consumerId;
				datacell.className = "insolventCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = order.totalCost;
				datacell.className = "insolventCell";
				row.appendChild(datacell);
				
				datacell = document.createElement("td");
				datacell.textContent = order.status;
				datacell.className = "insolventCell";
				row.appendChild(datacell);
				
				suspendedTable.appendChild(row);
			});
		}
	}
	
	

	function PersonalMessage(_username, messagecontainer) {
	    this.username = _username;
	    this.show = function() {
	      messagecontainer.textContent = "Welcome back, " + this.username;
	    }
	  }	


		
	function PageOrchestrator() {
	
	   this.start = function() {
		
		
		if(window.sessionStorage.getItem('employee') == null)
			window.location.href = "loginpage.html";
		else{
			personalMessage = new PersonalMessage(window.sessionStorage.getItem("employee"), document.getElementById("welcomemessage"));
	     	personalMessage.show();
		}
		
		
		document.getElementById("homeIcon").addEventListener('click', (e) => {
			e.preventDefault();
			window.location.href = "employeepage.html";
		});
		
		getReport = new GetReport();
		getReport.show();
		
		
		
	    };
	
	    this.refresh = function() {
	      
	    };
	 } 	
	})();