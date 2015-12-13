$(function() {
	
	$('.er-big-humb').click(function() {
		var cl_cont = '.nav-site-list';
					
		if ($(cl_cont+"[class*='er-no']").length > 0){
			$(cl_cont).fadeIn("slow", function(){
				$(cl_cont).removeClass('er-no');
			});
   			
   		}else if ($(cl_cont+"[class*='er-no']").length == 0){
   			
   			$(cl_cont).fadeOut("slow",function(){
   				$(cl_cont).addClass('er-no');	
   			});
   
   		}
   			
   			
   		
   		});
});


$(function() {
	
	$('.er-hum-min').click(function() {
		var cl_cont = '.er-main-menu';
					
		if ($(cl_cont+"[class*='er-no']").length > 0){
			$(cl_cont).fadeIn("slow", function(){
				$(cl_cont).removeClass('er-no');
			});
   		}else if ($(cl_cont+"[class*='er-no']").length == 0){
   			
   			$(cl_cont).fadeOut("slow",function(){
   				$(cl_cont).addClass('er-no');	
   			});
   		}
   	});
	
	var loginForm = $('#idloginmainform');
	
	var logoutForm = $('#idlogoutmainform');
	
	var loginAdminForm = $('#loginAdminForm');
	
	var logoutAdminForm = $('.sc-adm-account-form');

	function internalJsLogin(e,form){
		e.preventDefault();
		var username = $('input[name=loginusername]').val();
		var password = $('input[name=loginpassword]').val();
		$.ajax({
			url: form.prop('action'),
			datatype: "json",
			data: "loginusername="+username+"&loginpassword="+password,
			type: "POST",
			success: function(d,s,j){
				if (d.success){
					document.location.href = document.location.href;
					
				} else{
					$('.er-user-message').html('Username o password invalidi');
					$('.er-user-message').fadeIn(50);
				}
			},
			error: function(e){
				$('.er-user-sec-session').html('Login inattivo');
				$('.er-user-message').fadeIn(50);
			}
		});
	}

	loginAdminForm.submit(function(e){
		internalJsLogin(e,loginAdminForm);
	});

	loginForm.submit(function(e){
		internalJsLogin(e,loginForm);
	});
	
	function internalJsLogout(e,form){
		e.preventDefault();

		$.ajax({
			url: form.prop('action'),
			datatype: "json",
			type: "POST",
			success: function(d,s,j){
				if (!d.success){
					alert("error");
				} else {
					document.location.href = document.location.href;
				} 
			},
			error: function(e){
				$('.er-user-sec-session').html('Logout inattivo');
				$('.er-user-message').fadeIn(50);
			}
		});
		
	}
	
	
	logoutForm.submit(function(e){
		internalJsLogout(e,logoutForm);
	});
	
	logoutAdminForm.submit(function(e){
		internalJsLogout(e,logoutAdminForm);
	});
	
	/*
	 * /htmlreinterpreter
	 */
	/*
	var loginForm = $('#er-hrwst-main-form');

	loginForm.submit(function(e){
		e.preventDefault();
		var htmlIn = $('textarea[name=mainhrwstinput]').val();
		$.ajax({
			url: "/app/htmlreinterpreter",
			datatype: "text/html",
			data: "mainhrwstinput="+htmlIn,
			type: "POST",
			success: function(d,s,j){
				$('#idmainhrwstoutput').html(d);
			},
			error: function(e){
				alert(e);
			}
		});
	});
	*/
	
	/*
	$.ajax({
		url: "/app/logged",
		datatype: "json",
		type: "GET",
		success: function(d,s,j){
			if (d.success){
				$('.er-sec-login').fadeOut(50,function(){
					$('.er-user-message').html('Ciao '+d.name);
					$('.er-user-message').fadeIn(50);
				});
				
			} 
		},
		error: function(e){
			$('.er-sec-login').hide();
			$('.er-user-sec-session').html('Login inattivo');
			$('.er-user-message').fadeIn(50);
		}

	});
	*/
});