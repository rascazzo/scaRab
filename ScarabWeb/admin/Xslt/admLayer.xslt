<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:include href="/admin/Xslt/common/menumanage.xslt"/>

<xsl:template match="heads">
	<xsl:param name="context"></xsl:param>


	<xsl:apply-templates select="heads/head" mode="writeTag">
		<xsl:with-param name="context" select="$context"></xsl:with-param>
 
	</xsl:apply-templates>
	
	
</xsl:template>




<xsl:template name="adminMenu">
	<xsl:param name="context"></xsl:param>

	<div class="ad-er-main-second-wrap">
		<div class="ad-er-scarab-logo">
			<a href="/app/administrator">
			<img src="/Scarab/outw?webfile=images/path30035_2.png" />
			</a>
		</div>
		<div class="sc-main-title-cont">
			<h1 class="sc-main-title">
				<span><a href="/app/admin/it/administrator">Scarab</a></span>
			</h1>
		</div>
	</div>
</xsl:template>


<xsl:template match="mainAdminnav">
	<xsl:param name="context"></xsl:param>
	<nav>
		<xsl:apply-templates select="./adminMenus">
			<xsl:with-param name="context" select="$context"></xsl:with-param>
		</xsl:apply-templates>	
	</nav>
</xsl:template>
</xsl:stylesheet>
