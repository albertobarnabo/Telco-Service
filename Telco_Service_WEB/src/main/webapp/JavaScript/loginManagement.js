/**
 * Login management
 */

(function() { // avoid variables ending up in the global scope


	//Retreive image files
	var empImage = document.getElementById("employeeImage");
	var consImage = document.getElementById("consumerImage");
	
	empImage.src = "http://localhost:8080/Telco_Service_WEB/GetImage?name=impiegato.jpg";
	consImage.src = "http://localhost:8080/Telco_Service_WEB/GetImage?name=cliente.jpg";
	
	//Set the default login type to consumer
	var def_login = document.getElementById("radio_1");
	def_login.checked = true;
	

	//Register the event of submitting the login form
	
  document.getElementById("loginbutton_id").addEventListener('click', (e) => {
  
    var form = e.target.closest("form");
	var login_type = document.querySelector('input[name="login_type"]:checked');   
    
    //checkValidity controlla che i campi della form siano compilati correttamente
    if (form.checkValidity()) {    
    	
    //contatto il server, che tratterà la richiesta e manderà la risposta
		if(login_type.value == "consumer"){
			
      		makeCall("POST", 'CheckConsumerLogin', e.target.closest("form"),
        		function(req) {
        			//req.readyState == la risposta è arrivata dal server in maniera asincrona!!
          			if (req.readyState == XMLHttpRequest.DONE) {
            			var message = req.responseText;
            			switch (req.status) {
              				case 200: //caso in cui tutto è andato correttamente
              				//Salva lo user in sessionStorage
            					window.sessionStorage.setItem('consumer', message);
            				//Il client si occupa della selezione della prossima vista -> reindirizzo alla homePage
								if(window.sessionStorage.getItem('package') == null)
                					window.location.href = "consumerpage.html";
								else
									window.location.href = "buypage.html";
                				break;
              				case 400: // bad request
                				document.getElementById("errormessage").textContent = message;
                				break;
              				case 401: // unauthorized
                  				document.getElementById("errormessage").textContent = message;
                  				break;
              				case 500: // server error
            					document.getElementById("errormessage").textContent = message;
                				break;
            			}
          			}
        		}
      			);
 
		}else if(login_type.value == "employee"){
			    //contatto il server, che tratterà la richiesta e manderà la risposta
      		makeCall("POST", 'CheckEmployeeLogin', e.target.closest("form"),
        	function(req) {
        		//req.readyState == la risposta è arrivata dal server in maniera asincrona!!
          		if (req.readyState == XMLHttpRequest.DONE) {
            		var message = req.responseText;
            		switch (req.status) {
              			case 200: //caso in cui tutto è andato correttamente
              				//Salva lo user in sessionStorage
            				window.sessionStorage.setItem('employee', message);
            				//Il client si occupa della selezione della prossima vista -> reindirizzo alla homePage
                			window.location.href = "employeepage.html";
                			break;
              			case 400: // bad request
                			document.getElementById("errormessage").textContent = message;
                			break;
              			case 401: // unauthorized
                  			document.getElementById("errormessage").textContent = message;
                  			break;
              			case 500: // server error
            				document.getElementById("errormessage").textContent = message;
                			break;
            		}
          		}
        	}
      		);
		}
		else document.getElementById("errormessage").textContent = "You must select a category!";			
    } else { //Non invio al server dati che so già non essere validi!
    	 form.reportValidity();
    }
  });

	//Register the event of going to the home page without logging
	document.getElementById("continue").addEventListener('click', (e) => {	
		window.location.href = "consumerpage.html";
	});
	

	//Register the event of going to the registration page
	document.getElementById("signup").addEventListener('click', (e) => {	
		window.location.href = "registrationpage.html";
	});
	
	//Animation: mouse goes over the SIGNUP text
	document.getElementById("signup").addEventListener('mouseover', (e) => {	
		document.getElementById("signup").style="color:blue";
		document.getElementById("signup").style.cursor="pointer";
	});
	document.getElementById("signup").addEventListener('mouseleave', (e) => {	
		document.getElementById("signup").style="color:rgb(62, 65, 65)";
		document.getElementById("signup").style.cursor="default";
	});
	
	//Animation: mouse goes over the CONTINUE AS GUEST text
	document.getElementById("continue").addEventListener('mouseover', (e) => {	
		document.getElementById("continue").style="color:blue";
		document.getElementById("continue").style.cursor="pointer";
	});
	document.getElementById("continue").addEventListener('mouseleave', (e) => {	
		document.getElementById("continue").style="color:rgb(62, 65, 65)";
		document.getElementById("continue").style.cursor="default";
	});
	
	//Animation: mouse goes over the LOGIN button
	document.getElementById("loginbutton_id").addEventListener('mouseover', (e) => {	
		document.getElementById("loginbutton_id").style="background-color:#2F3E58";
		document.getElementById("loginbutton_id").style.cursor="pointer";
	});
	
	document.getElementById("loginbutton_id").addEventListener('mouseleave', (e) => {	
		document.getElementById("loginbutton_id").style="background-color:rgb(1, 82, 234)";
		document.getElementById("loginbutton_id").style.cursor="default";
	});


})();