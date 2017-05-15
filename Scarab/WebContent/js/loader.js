/*
 * loader di element jquery
 * per HTML5
 */
$(document).ready(function(){
	
$.canvas = { 
	support_canvas: function(){
		return !!document.createElement('canvas').getContext;
	},	
	supports_canvas_text: function(){
		if (!$.canvas.support_canvas()) {return false;}
		var emi_canv = document.createElement('canvas');
		var emi_contx = emi_canv.getContext('2d');
		return typeof emi_contx.fillText == 'function';
	}	
	
};


/*
 * parte custom
 */
/*
if($.canvas.supports_canvas_text()){*/
	/*titleobj.init('titleobj');*/
	/*backblogo.init('backg-logo');
} else {
	var dv = document.getElementById('backg-logo');
	var fig = document.createElement('figure');
	var imger = document.createElement('img');
	imger.setAttribute('src','/images/er.png');
	fig.appendChild(imger);
	dv.appendChild(fig);
}
*/


});