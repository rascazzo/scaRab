<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:output method="html"></xsl:output>

<xsl:include href="/admin/Xslt/admLayer.xslt"/>

<xsl:template match="adminlayer">
<html>
<head>
	<xsl:apply-templates select="heads">
		<xsl:with-param name="context" select="'init'"></xsl:with-param>
	</xsl:apply-templates>
</head>
<body>
	<div id="ad-er-admin-container">
		<xsl:call-template name="adminMenu">
		
		</xsl:call-template> 
		<div id="ad-er-wrap">
			
		</div>
	</div>
</body>
</html>
</xsl:template>

</xsl:stylesheet>