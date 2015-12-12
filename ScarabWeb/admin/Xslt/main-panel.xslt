<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:import href="/admin/Xslt/panels/administrator.xslt"/>
<xsl:import href="/admin/Xslt/panels/login.xslt"/>
<xsl:import href="/admin/Xslt/panels/createsidebar.xslt"/>
<xsl:import href="/admin/Xslt/panels/init.xslt"/>
<xsl:import href="/admin/Xslt/panels/registration.xslt"/>
<xsl:import href="/admin/Xslt/panels/loverview.xslt"/>
<xsl:import href="/admin/Xslt/panels/newtext.xslt"/>
<xsl:import href="/admin/Xslt/panels/newtextseries.xslt"/>

<xsl:template match="adminlayer" mode="wrap-content">
	<xsl:param name="context"></xsl:param>
	<xsl:param name="outContext" select="./mainpanel/@outContext"></xsl:param>
	<script type="text/javascript">
		<![CDATA[
		//scarabfe.init();
		$(document).ready(function(){
			
			fesidebar.init();
		]]>
		<xsl:apply-templates select="./mainpanel/mainsites/site" mode="dropdownSelectMenuActive">
			<xsl:with-param name="inputid" select="'siteId'"/>
		</xsl:apply-templates>	
		});
	</script>		
	<div class="ad-pnl dropdown" id="siteidwrp">
		
		<button id="sc-ad-mnhmger" class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			<span class="glyphicon glyphicon-menu-hamburger" aria-hidden="true"></span>
		</button>
		<ul class="dropdown-menu sc-input-no-fl" aria-labelledby="sc-ad-mnhmger">
		    <xsl:apply-templates select="./mainpanel/mainsites/site" mode="dropdownSelectMenu">
		    	<xsl:with-param name="header" select="./mainpanel/mainlabels/label[name = 'site']/value"></xsl:with-param>
				<xsl:with-param name="inputid" select="'siteId'"/>
		    </xsl:apply-templates>
		</ul>
				
	</div>	
	<xsl:apply-templates select="./mainpanel/panels" >
		<xsl:with-param name="outContext" select="$outContext"></xsl:with-param>
	</xsl:apply-templates>
		
</xsl:template>

<xsl:template match="panels">
	<div id="ad-er-wrap">
		<div class="ad-pnl">
			<xsl:apply-imports></xsl:apply-imports>
		</div>
	</div>
</xsl:template>
</xsl:stylesheet>