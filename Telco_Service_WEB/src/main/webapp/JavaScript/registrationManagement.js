/**
 * Login management
 */

(function() { // avoid variables ending up in the global scope


	//Register the event of submitting the login form
	
  document.getElementById("registrationbutton").addEventListener('click', (e) => {
  
    var form = document.getElementById("registrationform"); 
    
    //checkValidity controlla che i campi della form siano compilati correttamente
    if (form.checkValidity()) {    
      		makeCall("POST", 'Registration', form,
        		function(req) {									
          			if (req.readyState === XMLHttpRequest.DONE) {
            			var message = req.responseText;									
            			switch (req.status) {
              				case 200:     
								console.log("OK");         				
                				document.getElementById("resultmessage").textContent = "You successfully registered a new account!";
                				break;
              				case 400: // bad request
                				document.getElementById("resultmessage").textContent = message;
                				break;
              				case 401: // unauthorized
                  				document.getElementById("resultmessage").textContent = message;
                  				break;
              				case 500: // server error
            					document.getElementById("resultmessage").textContent = message;
                				break;
            			}
          			} else
                        document.getElementById("resultmessage").textContent = "Loading...";
        		}
      			);
 
		
		}else { //Non invio al server dati che so già non essere validi!
    	 form.reportValidity();
    }
  });

	//Register the event of going to the home page without logging
	document.getElementById("loginbut").addEventListener('click', (e) => {	
		window.location.href = "loginpage.html";
	});
	
	
	//Animation: mouse goes over the SIGNUP text
	document.getElementById("loginbut").addEventListener('mouseover', (e) => {	
		document.getElementById("loginbut").style="color:blue";
		document.getElementById("loginbut").style.cursor="pointer";
	});
	document.getElementById("loginbut").addEventListener('mouseleave', (e) => {	
		document.getElementById("loginbut").style="color:rgb(62, 65, 65)";
		document.getElementById("loginbut").style.cursor="default";
	});
	
	//Animation: mouse goes over the CONTINUE AS GUEST text
	document.getElementById("registrationbutton").addEventListener('mouseover', (e) => {	
		document.getElementById("registrationbutton").style="background-color:#123b93";
		document.getElementById("registrationbutton").style.cursor="pointer";
	});
	document.getElementById("registrationbutton").addEventListener('mouseleave', (e) => {	
		document.getElementById("registrationbutton").style="background-color:#2663e5";
		document.getElementById("registrationbutton").style.cursor="default";
	});
	



})();