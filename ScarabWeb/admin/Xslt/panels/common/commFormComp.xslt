<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="*" mode="standardSelectItem">
	<option value="{./itemValue}">
		<xsl:if test="./itemValue = //adminlayer/mainpanel/mainsites/site[meta = 'main']/value">
			<xsl:attribute name="selected">
				<xsl:value-of select="'true'"></xsl:value-of>
			</xsl:attribute>
		</xsl:if>
		<xsl:value-of select="./itemLabel"/>
	</option>	
</xsl:template>


<xsl:template match="*" mode="dropdownMenu">
<xsl:param name="header" />
	<xsl:if test="string-length($header) &gt; 0 and position() = 1">
		<li class="dropdown-header">
			<xsl:value-of select="$header"/>
		</li>
	</xsl:if>
	<li>
			<a href="#"><xsl:value-of select="./name"/></a>
	</li>	
</xsl:template>

<xsl:template match="site" mode="dropdownSelectMenu">
<xsl:param name="header" />
<xsl:param name="inputid"/>
	<xsl:if test="string-length($header) &gt; 0 and position() = 1">
		<li class="dropdown-header">
			<xsl:value-of select="$header"/>
		</li>
	</xsl:if>
	<li>
		<a onclick="fesidebar.changeValueHideCmp(this,'{$inputid}','{./value}',fesidebar.onSiteLoad)" class="sd-siteches">
			<xsl:value-of select="./name"/>
		</a>
	</li>				
</xsl:template>

<xsl:template match="site" mode="dropdownSelectMenuActive">
<xsl:param name="inputid"/>
	<xsl:if test="./meta and ./meta = 'main'">	
		fesidebar.changeValueHideCmp(null,'<xsl:value-of select="$inputid"/>','<xsl:value-of select="./value" />',fesidebar.onSiteLoad);
	</xsl:if>
</xsl:template>

</xsl:stylesheet>