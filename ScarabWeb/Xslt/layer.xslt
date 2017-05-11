<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


<xsl:include href="/Xslt/head/general_head.xslt"/>
<xsl:include href="/Xslt/component/common.xslt"/>
<xsl:include href="/Xslt/component/contents.xslt"/>
<xsl:include href="/Xslt/component/main_sidebar.xslt"/>
<xsl:include href="/Xslt/component/controlpanel.xslt"/>
<xsl:include href="/Xslt/widget/social_asin_lib.xslt"/>

<xsl:output method="html" ></xsl:output>

<xsl:variable name="domain">
	<xsl:choose>
		<xsl:when test="layer">
			<xsl:value-of select="//layer/header/domain" />
		</xsl:when>
		<xsl:when test="presentation">
			<xsl:value-of select="//presentation/header/domain" />
		</xsl:when>	
	</xsl:choose>
		
</xsl:variable>


<xsl:template match="/">
	<xsl:choose>
		<xsl:when test="//navigation = 'welcome'">
			<xsl:apply-templates select="presentation" />
		</xsl:when>
		<xsl:otherwise>
		    <xsl:apply-templates select="layer" />
		</xsl:otherwise>
	</xsl:choose>

	
</xsl:template>



<xsl:template match="layer">
	<xsl:variable name="titlePage" select="concat(//layer/title,' | ',//layer/navigation)"></xsl:variable>
	

<html>
<head>
	<xsl:call-template name="head">
		<xsl:with-param name="metatag" select="metatag" />
		<xsl:with-param name="namespace" select="namespace" />
		<xsl:with-param name="titlePage" select="$titlePage" />
		<xsl:with-param name="sidebarnav" select="sidebar" />
		<xsl:with-param name="navigation" select="//layer/navigation" />
		<xsl:with-param name="contextId" select="//layer/contextId" />
		<xsl:with-param name="currlang" select="//layer/logged/currLang"></xsl:with-param>
	</xsl:call-template>
	<script type="text/javascript" src="/jquery/jquery-2.1.4.min.js"></script>
	<script type="text/javascript" src="/js/mobile.js"></script>
	<!-- <script type="text/javascript" src="/js/loader.js"></script>
	<script type="text/javascript" src="/js/titleobj.js"></script>
	<xsl:if test="navigation = 'game'">
		<script type="text/javascript" src="/js/ergame.js"></script>
	</xsl:if>
	<script type="text/javascript" src="/js/rotateKeyboard.js"></script>-->
</head>
<body>
<!-- 
	<xsl:call-template name="facebook" >
	</xsl:call-template>
	 -->
	<div id="container">
		<div id="main-wrap">
			<xsl:apply-templates select="header"/>
			<section id="content" class="site">
				<xsl:apply-templates select="content">
					<xsl:with-param name="logged" select="./logged"/>			
				</xsl:apply-templates>
				
				<!-- <xsl:apply-templates select="controlpanel"/> -->
			</section>
			<xsl:call-template name="footer"/>
		</div>
	</div>
	<div id="aside-wrap">
	
	</div>
	<!-- 
	<script>
	  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	
	  ga('create', 'UA-55880725-1', 'auto');
	  ga('send', 'pageview');
	
	</script> -->	
</body>
</html>

</xsl:template>

<xsl:template match="presentation">
<xsl:variable name="titlePage" select="concat(//presentation/title,' | ',//presentation/navigation)"></xsl:variable>


<html lang="it">
<head>
	<xsl:call-template name="head">
		<xsl:with-param name="metatag" select="metatag" />
		<xsl:with-param name="namespace" select="namespace" />
		<xsl:with-param name="titlePage" select="$titlePage" />
		<xsl:with-param name="sidebarnav" select="sidebar" />
		<xsl:with-param name="currlang" select="presentation/logged/currLang"></xsl:with-param>
	</xsl:call-template>
	<script type="text/javascript" src="/lib/jquery-1.10.js"></script>
	<script type="text/javascript" src="/js/mobile.js"></script>
	<script type="text/javascript" src="/js/loader.js"></script>
	<script type="text/javascript" src="/js/welcomeER.js"></script>
	
</head>
<body>
	<!-- <xsl:call-template name="facebook" >
	</xsl:call-template>-->
	<div id="container" class="site">
		<xsl:apply-templates select="." mode="basic_mark">
		
		</xsl:apply-templates>	
	</div>
</body>
</html>

</xsl:template>
</xsl:stylesheet>
