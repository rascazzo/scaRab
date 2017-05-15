/**
 * js main lib Scarab
 * @author dev@emiliorascazzo.it
 */

/*
 * main object
 */


var scarabfe = {
	ajax_data: null,
	ajax_func: {
		post: [{
			call: {
			},
			form: {
				
			}
		}]
	},
	ajax_datavalue: null,
	jEle: null,
	activeVersions: ["MSXML2.XMLHttp.5.0",
		"MSXML2.XMLHttp.4.0",
		"MSXML2.XMLHttp.3.0",
		"MSXML2.XMLHttp",
		"Microsoft.XMLHttp"],
	reload: function(){
		$('#tgtypeIdFirst').prop("value","on");
		$('#tgtypeIdFirst').prop("checked",true);
		$('#tgtypeIdSecond').prop("value","off");
		$('#tgtypeIdSecond').prop("checked",false);
		$('#tgtypeIdGrp').prop("value","off");
		$('#tgtypeIdGrp').prop("checked",false);
		$('#tagnamerelId').prop("disable",true);
	},
	init : function(){
		this.ajax_data = this.ajax_data || [];
		this.ajax_func = this.ajax_func || [];
		this.ajax_datavalue = this.ajax_datavalue || [];
		this.reload();
	},
	createHttpRequest: function(){	
		if (typeof XMLHttpRequest != "undefined"){
			return new XMLHttpRequest();
		} else if (window.XMLHttpRequest) {
			//min of win 8
        	return new window.XMLHttpRequest;
		} else if (window.ActiveXObject){
			for (var i=0;i< this.activeVersions.length;i++){
				try {
					return new ActiveXObject(this.activeVersions[i]);
				} catch (oError){
					
				}
			}
		}
		throw new Error("scarabfe ajax Requests could be created");
	},
	subLoad: function(f){
		$(f).submit(function(e){
			e.preventDefault();	
			var fields = f.find('input,select,button,textarea');
			var fieldsserie = [];
			var check = true;
			for (var i = 0; i < fields.length; i++){
					if ($(fields[i]).val() && $(fields[i]).val().length && $(fields[i]).val().length > 0 &&  
						$(fields[i]).attr("name") && $(fields[i]).attr("name").length && $(fields[i]).attr("name").length > 0)
						if (i < (fields.length -1)){
							fieldsserie.push($(fields[i]).attr("name")+"="+$(fields[i]).val()+"&");
						}else{ 
							fieldsserie.push($(fields[i]).attr("name")+"="+$(fields[i]).val());
						}
			}
			if (fieldsserie.length && fieldsserie.length == 0){
						check = false;
						$('#sc-adm-messages-box').html("");
						$("<span>Missing ...</span>").appendTo("#sc-adm-messages-box");
						scarabfe.ajaxBoxMessage();
						scarabfe.reload();
						return;
			}
			var data = fieldsserie.join("");
			var currentHttpRequest = scarabfe.createHttpRequest();
			currentHttpRequest.open(f.attr("method"),f.attr("action"),true);
			currentHttpRequest.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			currentHttpRequest.setRequestHeader("Content-length", data.length);
			currentHttpRequest.setRequestHeader("Connection", "close");
			currentHttpRequest.setRequestHeader("Accept", "application/json");
			
			//this.ajax_func.push(currentHttpRequest);
			currentHttpRequest.onreadystatechange = function() {
				if (currentHttpRequest.readyState == 4){
					$('#sc-adm-messages-box').html("");
					$("<span>Completed</span>").appendTo("#sc-adm-messages-box");
					scarabfe.ajaxBoxMessage();
				} else {
					$("#sc-adm-messages-box").html("");
					$("<span>Waiting...</span>").appendTo("#sc-adm-messages-box");
					scarabfe.ajaxBoxMessage();
				}	
				if (currentHttpRequest.status == 200){
					$("<br/>Success").appendTo("#sc-adm-messages-box");
					scarabfe.ajaxBoxMessage();
				} else {
					$("<br/>Failed").appendTo("#sc-adm-messages-box");
					$("<br/>"+currentHttpRequest.status).appendTo("#sc-adm-messages-box");
					scarabfe.ajaxBoxMessage();
				}
			};
			currentHttpRequest.send(data);
			scarabfe.reload();
			
	
		});
	},
	loadjEle: function(ele){
		var loadjEle = $("#"+ele);
		this.ajax_func.post.push({"call":scarabfe.subLoad(loadjEle),"form":ele});
	},
	ajaxBoxMessage: function(){
		$("#sc-adm-messages").show("fast");
				$("#sc-adm-messages-box-cls").click(function(e){
					$("#sc-adm-messages").hide("slow");
				});	
	},
	ajsubmit: function(e){
		
		
	}
};


var fesidebar = {
	form: null,
	formAttr: null,
	tagnameEle: null,
	tagvalueEle: null,
	sitesEle: null,
	attrnameEle: null,
	attrvalueEle: null,
	fatherTagnameEle: null,
	tagnameTypeEleFirst: null,
	tagnameTypeEleSecond: null,
	tagnameTypeEleGrp: null,
	attrsEle: null,
	tagname: null,
	tagvalue: null,
	sites: [],
	selectedSite: null,
	attrname: null,
	attrvalue: null,
	fatherTagname: [],
	selectedFatherTagname: null,
	selectedTagnameType: null,
	tagnameType: null,
	attrs: {table:[]},
	init: function(){
		this.form = $('#createsidebarTNForm');
		this.formAttr = $('#createsidebarATForm');
		this.tagnameEle = $('#tagnameId');
		this.tagvalueEle = $('#tagvalueId');
		this.sitesEle = $('#siteId');
		this.fatherTagnameEle = $('#tagnamerelId');
		this.attrnameEle = $('#attrnameId');
		this.attrvalueEle = $('#attrvalueId');
		this.tagnameTypeEleFirst = $('#tgtypeIdFirst');
		this.tagnameTypeEleSecond = $('#tgtypeIdSecond');
		this.tagnameTypeEleGrp = $('#tgtypeIdGrp');
		this.attrsEle = $('.sc-attr-table');
		$("#tagnamerelId").prop("disable",true);
	},
	showTagRel: function(e){
		if (e && e.value && e.value === 'on'){
			$(".input-slt-fl-r-wrap-cnt.tagnamerel").show('fast');	
			$("#tagnamerelId").prop("disable",false);
		} else {
			$(".input-slt-fl-r-wrap-cnt.tagnamerel").hide('slow');
			$("#tagnamerelId").prop("disable",true);
		}
	},
	hideTagRel: function(e){
		if (e && e.value && e.value === 'on'){
			$(".input-slt-fl-r-wrap-cnt.tagnamerel").hide('slow');
			$("#tagnamerelId").prop("disable",true);
		}	
	},
	allroottagsbysite: function(e,url){
		if (e && e.value && e.value != ''){
			var data = "sitenameid="+$("#siteId").val();
			
			/*
			var currentHttpRequest = scarabfe.createHttpRequest();
				currentHttpRequest.open("get",url+"?"+data,true);
				currentHttpRequest.setRequestHeader("Accept", "application/json");
				currentHttpRequest.onreadystatechange = function() {
					if (currentHttpRequest.readyState == 4){
							
						if (currentHttpRequest.status == 200){
							$("<br/>Success").appendTo("#sc-adm-messages-box");
							//insert option
						} else {
							$("<br/>Failed").appendTo("#sc-adm-messages-box");
							$("<br/>"+currentHttpRequest.status).appendTo("#sc-adm-messages-box");
						}
						scarabfe.ajaxBoxMessage();
						scarabfe.reload();
					}
				};
				currentHttpRequest.send();*/
				$.ajax({
					url: url+"?"+data,
					datatype: "json",
					type: "GET",
					success: function(d,s,j){
						if (!d.success){
							$("<br/>Failed").appendTo("#sc-adm-messages-box");
							$("<br/>"+s).appendTo("#sc-adm-messages-box");
							if (d.message)
								$("<br/>"+d.message).appendTo("#sc-adm-messages-box");
						} else {
							//insert optino
						} 
					},
					error: function(e){
						$("<br/>Error").appendTo("#sc-adm-messages-box");
						scarabfe.ajaxBoxMessage();
					}
				});
		}
	},
	alltagSonbysite: function(e,url){
		if (e && e.value && e.value != ''){
			var data = "sitenameid="+$("#siteId").val()+"&tagnamerel="+e.value ;
			
			
				$.ajax({
					url: url+"?"+data,
					datatype: "json",
					type: "GET",
					success: function(d,s,j){
						if (!d.response.success){
							$("<br/>Failed").appendTo("#sc-adm-messages-box");
							$("<br/>"+s).appendTo("#sc-adm-messages-box");
							if (d.message)
								$("<br/>"+d.message).appendTo("#sc-adm-messages-box");
						} else if (d.response.list){
							if (d.response.list.bodyL)
								$("#tagnumorderId").prop("value",'2');
							else if (d.response.list.bodyL.length)
								$("#tagnumorderId").prop("value",d.response.list.bodyL.length);
							else
								$("#tagnumorderId").prop("value",'2');		
						} 
					},
					error: function(e){
						$("<br/>Error").appendTo("#sc-adm-messages-box");
						scarabfe.ajaxBoxMessage();
					}
				});
		}
	},
	acxtbox: document.getElementsByName("tree"),
	//acxlfbox: document.getElementById("lf-tagnamesys-box"),
	createAttribute: function(e){
			
			var name = t.exposedrtagname;
			if (name.length == 0 || typeof name == "undefined") {
				$("<br/>Error").appendTo("#sc-adm-messages-box");
				scarabfe.ajaxBoxMessage();
				return;
			}
			var basecall = $(this.acxtbox[0]).attr("data-type");
			if ($("#attrnameId").val().length > 0 && $("#attrvalueId").val().length > 0){
				data = "sitenameid="+$("#siteId").val()+"&tagname="+name+"&key="+$("#attrnameId").val()+
						"&value="+$("#attrvalueId").val();
				$.ajax({
					url: "/"+basecall+"/sidebarws/createattributes",
					method: "POST",
					datatype: "json",
					data: data,
					success: function(d,s,j){
						if (d.response.success){
							$("<br/>Completed").appendTo("#sc-adm-messages-box");
							scarabfe.ajaxBoxMessage();
							t.refreshattr(basecall,name);
						}
					},
					error: function(e){
						$("<br/>Error").appendTo("#sc-adm-messages-box");
						scarabfe.ajaxBoxMessage();
					}
				});
			} else {
				$("<br/>Error").appendTo("#sc-adm-messages-box");
				scarabfe.ajaxBoxMessage();
			}
		
	},
	changeValueHideCmp: function(e,idEle,nwvalue,fun){
		var inputEle = $('#'+idEle+'[type = "hidden"]');
		if (inputEle && typeof inputEle != "undefined" && nwvalue.length && nwvalue.length > 0){
			inputEle.attr("value",nwvalue);
			if (e) fun();
		}
	},
	onSiteLoad:function(){
		t.drawtreeLoad();
	} 
	/*
	cSite: false,
	ctname: false,
	ctnamev: false,
	ctnamet: false,
	ctnamer: false,
	showAddTagname: function(){
		if (this.cSite && this.ctname && this.ctnamev && this.ctnamet){
			if ($("#tgtypeIdSecond").val() === 'on' && this.ctnamer){
				$('.input-butt-wrap-cl.addtgn').show('fast');
			} else {
				$('.input-butt-wrap-cl.addtgn').show('fast');
			}
		}
	},
	onChangeSite: function(e){
		if (e && e.value && e.value != ''){
			this.cSite = true;
			this.showAddTagname();
		}else 
			this.cSite = false;	
	},
	onChangeTagrel: function(e){
		if (e && e.value && e.value != ''){
			this.ctnamer= true;
			this.showAddTagname();
		}else 
			this.ctnamer= false;	
	},
	onKeyupTagname: function(e){
		if (e && e.value && e.value != ''){
			this.ctname = true;
			this.showAddTagname();
		}else 
			this.ctname = false;	
		
	},
	onKeyupTagnameVal: function(e){
		if (e && e.value && e.value != ''){
			this.ctnamev = true;
			this.showAddTagname();
		}else 
			this.ctnamev = false;	
	},
	onChangetgTypeFirst: function(e){
		if (e && e.value && e.value === 'on'){
			this.ctnamet = true;
			this.showAddTagname();
		}else 
			this.ctnamet = false;	 
	},
	onChangetgTypeSecond: function(e){
		if (e && e.value && e.value === 'on'){
			this.ctnamet = true;
			this.showAddTagname();
		}	
		else 
			this.ctnamet = false;	  
	},
	onChangetgTypeGrp: function(e){
		if (e && e.value && e.value === 'on'){
			this.ctnamet = true;
			this.showAddTagname();
		}else 
			this.ctnamet = false;	 
	}*/
};
