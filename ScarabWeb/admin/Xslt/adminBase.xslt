<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html" ></xsl:output>


<xsl:include href="/admin/Xslt/admLayer.xslt"/>
<xsl:include href="/admin/Xslt/main-panel.xslt"/>

<xsl:template match="adminlayer">
<html>
<head>
	<!-- meta -->
	<meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <!-- Bootstrap -->
    <link href="/Scarab/outw?webfile=admin/css/bootstrap.min.css" rel="stylesheet"/>
	<xsl:apply-templates select="heads">
		<xsl:with-param name="context" select="'init'"></xsl:with-param>
	</xsl:apply-templates>
</head>
<body>
	<div id="sc-adm-messages">
		<div class="sc-adm-cont-msg">
			<span id="sc-adm-messages-box"></span>
			<span id="sc-adm-messages-box-cls">Close</span>
		</div>
	</div>
	<div id="ad-er-admin-container">		
		<xsl:call-template name="adminMenu">
			<xsl:with-param name="context" select="'init'"></xsl:with-param>
		</xsl:call-template>
		<div id="main-panel">
			<xsl:apply-templates select="(.)" mode="wrap-content">
				<xsl:with-param name="context" select="'init'"></xsl:with-param>
			</xsl:apply-templates> 
		</div> 
		<div id="main-nav-panel" class="no-mindv">
			<xsl:apply-templates select="./mainAdminnav">
				<xsl:with-param name="context" select="'init'"></xsl:with-param>
			</xsl:apply-templates>  
		</div>
		<div id="main-nav-panel" class="y-mindv">
			<div id="ad-y-menu">
			<button type="button" class="btn btn-default" aria-label="{./mainpanel/mainlabels/label[name = 'menu']/value}">
			  <span class="glyphicon glyphicon-asterisk" aria-hidden="true"></span>
			</button>
				<xsl:apply-templates select="./mainAdminnav" mode="ymin">
					<xsl:with-param name="context" select="'init'"></xsl:with-param>
				</xsl:apply-templates>
			</div>	 
		</div>
	</div>
	<script src="/Scarab/outw?webfile=admin/js/bootstrap.min.js"></script>	
</body>
</html>
</xsl:template>

</xsl:stylesheet>	
