

(function() {
	
	var pageOrchestrator = new PageOrchestrator();
	var orderId, order, products, services, chosenValidity;
	 
	window.addEventListener("load", () => {
		      pageOrchestrator.start(); // initialize the components
		      pageOrchestrator.refresh();
		     // display initial content
		  }, false);


	function GetOrder(_alert){
		
		this.alert = _alert;

		this.show = function() {
			var self = this;
			makeCall("GET", "GetOrderById?orderId="+orderId, null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              order = JSON.parse(req.responseText);
				  self.update(order);
	            }
	          } 
	        }
	      );			
		}; 
		
		this.update = function(order){
			var table = document.getElementById("ordertable");
			var row, firstcell, datacell, prodcell, prodrow, prodtable;
			
			table.innerHTML = "";
			
			//Table caption
			row = document.createElement("caption");
			row.textContent = "Here is the summary of your order numer #"+order.orderId;
			row.className = "tabletitle";
			table.appendChild(row);
			
			//First row shows the name of the package
			row = document.createElement("tr");
			firstcell = document.createElement("td");
			firstcell.textContent = "Service Package: ";
			firstcell.className = "firstCell";
			row.appendChild(firstcell);
			datacell = document.createElement("td");
			datacell.textContent = order.servicePackage.name;
			row.appendChild(datacell);
			
			table.appendChild(row);
			
			//Second Row shows the selected starting date of subscription
			row = document.createElement("tr");
			firstcell = document.createElement("td");
			firstcell.textContent = "Valid From: ";
			firstcell.className = "firstCell";
			row.appendChild(firstcell);
			datacell = document.createElement("td");
			datacell.textContent = order.startSubscription.day+" / "+order.startSubscription.month+" / "+order.startSubscription.year;
			row.appendChild(datacell);
			
			table.appendChild(row);
			
			//Third row shows the chosen validity period 
			row = document.createElement("tr");
			firstcell = document.createElement("td");
			firstcell.textContent = "Validity Period: ";
			firstcell.className = "firstCell";
			row.appendChild(firstcell);
			datacell = document.createElement("td");
			datacell.textContent = order.validity + " Months";
			row.appendChild(datacell);
			
			table.appendChild(row);
			
			//Fourth row shows the chosen optional products
			row = document.createElement("tr");
			firstcell = document.createElement("td");
			firstcell.textContent = "Chosen Optional Products: ";
			firstcell.className = "firstCell";
			row.appendChild(firstcell);
			datacell = document.createElement("td");
			
			
			var optProds = order.optionalProducts;
			prodtable = document.createElement("table");
			prodtable.className = "optProdTable"
			
			optProds.forEach(function(product){
				prodrow = document.createElement("tr");
				prodcell = document.createElement("td");
				prodcell.className = "optProdCell";
				
				prodcell.textContent = product.name + ": " + product.fee + "€ / Month";
				prodrow.appendChild(prodcell);
				prodtable.appendChild(prodrow);
			});
			datacell.appendChild(prodtable);
			
			row.appendChild(datacell);
			
			table.appendChild(row);
			
			//Last row for the total to be prepaid: total cost per month * months
			row = document.createElement("tr");
			firstcell = document.createElement("td");
			firstcell.textContent = "Total Cost: ";
			firstcell.className = "firstCell";
			row.appendChild(firstcell);
			datacell = document.createElement("td");
			datacell.textContent = parseFloat(order.totalCost) * parseInt(order.validity) + " €";
			row.appendChild(datacell);
			
			table.appendChild(row);
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
		
		
		if(window.sessionStorage.getItem('consumer') == null)
			window.location.href = "loginpage.html";
		else{
			personalMessage = new PersonalMessage(window.sessionStorage.getItem("consumer"), document.getElementById("welcomemessage"));
	     	personalMessage.show();
		}
		
		orderId = window.sessionStorage.getItem('order');
		if(orderId == null){
			window.location.href = "consumerpage.html";
		}
		
		
		document.getElementById("homeIcon").addEventListener('click', (e) => {
			e.preventDefault();
			window.location.href = "consumerpage.html";
		});
		
		getOrder = new GetOrder();
		getOrder.show();
		
		document.getElementById("paybutton").addEventListener("click", (e)=>{
			
			
			makeCall("GET", "PerformPayment?orderId="+order.orderId, null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              	document.getElementById("normalpage").className = "blurred";
					document.getElementById("resultdiv").style.visibility = "visible";
					
					var p = document.getElementById("transactionResultText");
					var img = document.getElementById("resultimg");
					
					console.log(message);
					message = message.replace(/[^a-zA-z0-9]/g, '');
					const ok = 'accepted';
					const ko = 'denied';
					
					if(message === ok){
						p.textContent = "Payment Successful!";
						p.style.color = "green";
						img.src = "http://localhost:8080/Telco_Service_WEB/GetImage?name=success.jpg";
						return;
					}
					else if(message === ko){
						p.textContent = "Transaction Denied!";
						p.style.color = "red";
						img.src = "http://localhost:8080/Telco_Service_WEB/GetImage?name=failiure.jpg";
						return;
					}
					
	              }
				  else{
	              	;  
	              }
	            }
	          }
	      );
			
		});
		
		document.getElementById("homebutton").addEventListener("click", (e) => {
			window.sessionStorage.removeItem('order');
			window.location.href = "consumerpage.html";
		});
		
	    };
	
	    this.refresh = function() {
	      
	    };
	 } 	
	})();