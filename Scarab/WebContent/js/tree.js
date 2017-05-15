var t = {
	sitelist: [],
	sonlist: {
		name: {},
		list: [],
	},
	allroottagsbysite: function(e,url,fun){
		//if (e && e.value && e.value != ''){
		if (e)	{
			var data = "sitenameid="+$('#siteId').val();
			
			
				$.ajax({
					url: url,
					data: data,
					datatype: "json",
					type: "GET",
					success: function(d,s,j){
						if (!d.response.success){
							$("<br/>Failed").appendTo("#sc-adm-messages-box");
							$("<br/>"+s).appendTo("#sc-adm-messages-box");
							if (d.response.message)
								$("<br/>"+d.message).appendTo("#sc-adm-messages-box");
						} else {
							t.sitelist = d.response.list;
							fun();
						} 
					},
					error: function(e){
						$("<br/>Error").appendTo("#sc-adm-messages-box");
					}
				});
		}
	},
	grey: "#E7E7E7",
	lill: "#E6E67A",
	treecl: "#FF9933",
	tcanvas: null,
	init: function(){
		if($.canvas.supports_canvas_text()){
			var cnt = document.getElementById("t");
			var cnvs = document.createElement("canvas");
			cnvs.setAttribute("width","200px");
			cnvs.setAttribute("id","lfreeBox");
			var cnvsCx = cnvs.getContext("2d");
			this.cx = cnvsCx;
			/*
			var scgradient = cnvsCx.createLinearGradient(0,0,200,0);
			scgradient.addColorStop(0,this.grey);
			scgradient.addColorStop(1,"#FFF");
			cnvsCx.fillStyle = scgradient;
			cnvsCx.fillRect(0,0,500,700);
			*/
			cnt.appendChild(cnvs);
			this.tcanvas = cnvs;
			this.drawtreeLoad(cnvs,cnvsCx);
		}
	},
	reload: function(){
		if($.canvas.supports_canvas_text()){
			var cnt = document.getElementById("t");
			var cnvs = null;
			var cnvsCx = null;
			$(".sc-lf-tree").html("");
			if (typeof $('#lfreeBox') != 'undefined'){
				cnvs = document.getElementById("lfreeBox");
				cnvsCx = cnvs.getContext("2d");
				this.cx = cnvsCx;
				this.drawtreeLoad(cnvs,cnvsCx);
			} else {
				this.init();			
			}
		}
	},
	/*long line*/
	draweles: function(ele,cx,numr){
		if (numr > 0){
				cx.moveTo(5,numr*60);	
			cx.lineTo(55,numr*60);
			cx.strokeStyle = this.treecl;
			cx.stroke();
		} else {
			cx.lineTo(55,5);
			cx.strokeStyle = this.treecl;
			cx.stroke();
		}
	},
	/*line*/
	drawbranch: function(ele,cx,numr){
		if (numr > 0){
			cx.lineTo(55,numr*60+55);
			cx.strokeStyle = this.treecl;
			cx.stroke();
		} else {
			cx.lineTo(55,55);
			cx.strokeStyle = this.treecl;
			cx.stroke();
		}
	},
	/*z*/
	drawleaf: function(ele,cx,numr){
		cx.moveTo(0,(5+numr*10));
		cx.lineTo(0,(10+numr*10));
		cx.lineTo(5,(10+numr*10));
		cx.strokeStyle = this.treecl;
		cx.stroke();
	},
	ele: null,
	cx: null,
	cxtbox: null,
	cxlfbox: null,
	basecall: null,
	drawtreeLoad: function(){
		var cxtbox = document.getElementsByName("tree");
		this.cxtbox = cxtbox;
		var cxlfbox = document.getElementById("lf-tagnamesys-box");
		this.cxlfbox = cxlfbox;
		var basecall = $(cxtbox[0]).attr("data-type");
		this.basecall = basecall; 
		this.allroottagsbysite(new Event("click"),"/"+basecall+"/sidebarws/allrtagsbysite",t.drawtree);
	},
	drawtree: function(){
		if (t.sitelist && t.sitelist.bodyL){
			$("#lf-tagnamesys-box").html("");
					$(".sc-tree").show("slow",function(){
						t.cx.beginPath();
						if (t.sitelist.bodyL.length){
							t.tcanvas.setAttribute("height",'"'+60*t.sitelist.bodyL.length+'px');
							for (var i= 0; i < t.sitelist.bodyL.length; i++ ){
								if (i == 0){
									t.cx.moveTo(5,5);
									t.draweles(t.ele,t.cx,i);
									//this.drawbranch(ele,cx,i);
								} else {
									t.draweles(t.ele,t.cx,i);
									//this.drawbranch(ele,cx,i);
								}
								t.drawsingleBtree(i,false);
							}
						} else if (t.sitelist.bodyL){
							t.tcanvas.setAttribute("height","60px");							
							t.drawsingleBtree(0,true);	
						}
					});
				} else{
					$(".sc-tree").hide("fast");
				}
	},
	drawsingleBtree: function(i,o){
		var lfbox = document.createElement("div");
		var lfboxTxt = document.createElement("span");
		var lfboxchk = document.createElement("input");
		lfboxchk.setAttribute("type","checkbox");
		lfboxchk.setAttribute("style","float:right");
		lfboxchk.setAttribute("data",i);
		lfboxchk.setAttribute("class","sd-r-chkh");
		if (!o)
			lfboxchk.setAttribute("onchange","t.drawextendsele(this,'"+t.sitelist.bodyL[i].itemValue.$+"','"+t.basecall+"')");
		else
			lfboxchk.setAttribute("onchange","t.drawextendsele(this,'"+t.sitelist.bodyL.itemValue.$+"','"+t.basecall+"')");	
		lfbox.appendChild(lfboxchk);
		if (!o)
			lfboxTxt.innerHTML = t.sitelist.bodyL[i].itemLabel.$;
		else	
			lfboxTxt.innerHTML = t.sitelist.bodyL.itemLabel.$;
		lfboxTxt.setAttribute("style","float:right;");
		lfbox.appendChild(lfboxTxt);
		lfbox.setAttribute("style","width:100%;height:60px");
		t.cxlfbox.appendChild(lfbox);	
	},
	exposedrtagname: "",
	drawextendsele: function(e,name,basecall){
		var cnvs = document.getElementById("lfreeBox");	
		var cnvsCx = cnvs.getContext("2d");
		
		if (e && e.value && e.value === 'on'){
				t.exposedrtagname = name;		
			$.ajax({
				url: "/"+basecall+"/sidebarws/alltagsonbysite?sitenameid="+$("#siteId").val()+"&tagnamerel="+name,
				datatype: "json",
				method: "GET",
				success: function(d,s,j){
					if (d.response.success.$){
						t.refreshattr(basecall,name);						
					}
				},
				error: function(e){
					$("<br/>Error").appendTo("#sc-adm-messages-box");
				}
			});
			
			
		}	
	},
	refreshattr: function(basecall,name){
		var tblattr= $("#sd-attr-table tbody");
			$.ajax({
				url: "/"+basecall+"/sidebarws/getattributes?sitenameid="+$("#siteId").val()+"&tagname="+name,
				method: "GET",
				success: function(d,s,j){
					$(tblattr).html("");
					if (d.response.success.$ && d.response.list.bodyL){
						if (d.response.list.bodyL.length){
							for(var i=0;i < d.response.list.bodyL.length; i++){
								$('<tr>').appendTo(tblattr);
								$('<td><input type="checkbox"/></td>').appendTo(tblattr);
								$('<td>'+d.response.list.bodyL[i].id.$+'</td>').appendTo(tblattr);
								$('<td>'+d.response.list.bodyL[i].name.$+'</td>').appendTo(tblattr);
								$('<td>'+d.response.list.bodyL[i].value.$+'</td>').appendTo(tblattr);
								$('<tr>').appendTo(tblattr);
							}
						} else {
							$('<tr>').appendTo(tblattr);
							$('<td><input type="checkbox"/></td>').appendTo(tblattr);
							$('<td>'+d.response.list.bodyL.id.$+'</td>').appendTo(tblattr);
							$('<td>'+d.response.list.bodyL.name.$+'</td>').appendTo(tblattr);
							$('<td>'+d.response.list.bodyL.value.$+'</td>').appendTo(tblattr);
							$('<tr>').appendTo(tblattr);
						}
						
					}
				},
				error: function(e){
					$("<br/>Error").appendTo("#sc-adm-messages-box");
				}
			});
	}
	
};

