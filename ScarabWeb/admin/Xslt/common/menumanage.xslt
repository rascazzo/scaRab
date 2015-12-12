<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="*" mode="writeTag">
	<xsl:param name="context"></xsl:param>
	<xsl:param name="element" select="."/>
	<xsl:param name="pos" select="position()"></xsl:param>
	<xsl:element name="{$element}">
		<xsl:apply-templates select="../../attrs/tagAttr[(@id + 1) =  $pos]/attributes" />
	</xsl:element>
	
</xsl:template>

<xsl:template match="*" mode="writeTagVal">
	<xsl:param name="context"></xsl:param>
	<xsl:param name="tagname"/>
	<xsl:param name="tagvalue" />
	<xsl:param name="menutagname"/>
	<xsl:param name="pos"></xsl:param>
	<xsl:choose>
		<xsl:when test="$tagname='form'">
			<xsl:element name="{$tagname}">
				<xsl:apply-templates select="tagAttr[(@id + 1) = $pos]/attributes[name != 'id' and name != 'type'] ">
				</xsl:apply-templates>
				<xsl:element name="input">
					<xsl:attribute name="type">
						<xsl:value-of select="tagAttr/attributes[name = 'type']/value"></xsl:value-of>
					</xsl:attribute>
					<xsl:attribute name="value">
						<xsl:value-of select="$tagvalue"></xsl:value-of>
					</xsl:attribute>
				</xsl:element>
			</xsl:element>
		</xsl:when>
		<xsl:when test="$tagname='childs'">
			<xsl:choose>
						<!-- child case -->
					<xsl:when test="string-length(tagAttr[(@id + 1) = $pos]/attributes[name = 'elements']/value) &gt; 0">
						<div class="sc-adm-lang-group-wrap">
						<xsl:element name="{tagAttr[(@id + 1) = $pos]/attributes[name = 'elements']/value}">
							<xsl:attribute name="class">
								<xsl:value-of select="'sc-adm-lang-group-ch-list'"></xsl:value-of>
							</xsl:attribute>
							<xsl:choose>
								<xsl:when test="$menutagname = 'userlang'">
									<xsl:apply-templates select="//adminlayer/langs/lang" mode="child">
										<xsl:with-param name="tagname" select="$tagname"></xsl:with-param>
										<xsl:with-param name="currentLang" select="//logged/currLang"></xsl:with-param>
										<xsl:with-param name="texttype" select="tagAttr[(@id + 1) = $pos]/attributes[name = 'texttype']/value"></xsl:with-param>
										<xsl:with-param name="attrs" select="tagAttr[(@id + 1) = $pos]/attributes[name != 'id' and name != 'elements' and name != 'texttype']"></xsl:with-param>
									</xsl:apply-templates>
								</xsl:when>
							</xsl:choose>
						</xsl:element>
						</div>
					</xsl:when>
					
			</xsl:choose>		
		</xsl:when>
		<xsl:otherwise>
				<xsl:element name="{$tagname}">	
							<xsl:apply-templates select="tagAttr[(@id + 1) = $pos]/attributes[name != 'id']">
							</xsl:apply-templates>
							<xsl:value-of select="$tagvalue"></xsl:value-of>
				</xsl:element>
		</xsl:otherwise>
	</xsl:choose>
	
</xsl:template>

<xsl:template match="attributes">
	<xsl:param name="value" select="./value"></xsl:param>
	<xsl:param name="name" select="./name"></xsl:param>
	<xsl:param name="queryForGet"></xsl:param>
	<xsl:attribute name="{$name}">
		<xsl:choose>
			<xsl:when test="$name = 'href'">
				<xsl:value-of select="concat($value,$queryForGet)"></xsl:value-of>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$value"></xsl:value-of>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:attribute>
</xsl:template>

<xsl:template match="*" mode="writeLi">
	<xsl:param name="context"></xsl:param>
	<xsl:param name="tagname" select="./name"/>
	<xsl:param name="tagvalue" select="./value"/>
	<xsl:param name="menutagname"/>
	<li class="sc-nav-single">
		<xsl:apply-templates select="../../attrs[tagAttr[(@id +1 ) = position()]]" mode="writeTagVal">
			<xsl:with-param name="context" select="$context"></xsl:with-param>
			<xsl:with-param name="tagname" select="$tagname"></xsl:with-param>
			<xsl:with-param name="tagvalue" select="$tagvalue"></xsl:with-param>
			<xsl:with-param name="pos" select="position()"></xsl:with-param>
			<xsl:with-param name="menutagname" select="$menutagname"></xsl:with-param>
		</xsl:apply-templates>
	</li>
</xsl:template>

<xsl:template match="adminMenus">
	<xsl:param name="context"></xsl:param>
	<div>
		<ul class="sc-nav-single-group-list">
			<span class="sc-nav-single-group-label"><xsl:value-of select="./@name"></xsl:value-of></span>
			<xsl:if test="./@tagname = 'useraction'">
				<div>
					<span><xsl:value-of select="//logged/account/eMail"></xsl:value-of></span>
				</div>	
			</xsl:if>
			<xsl:apply-templates select="tagAttr/li/navTag" mode="writeLi">
				<xsl:with-param name="context" select="$context"></xsl:with-param>
				<xsl:with-param name="menutagname" select="./@tagname"></xsl:with-param>
			</xsl:apply-templates>
		</ul>
	</div>
</xsl:template>

<xsl:template match="adminMenus" mode="ymin">
	<xsl:param name="context"></xsl:param>
	<div id="ad-y-menu-c">
		<xsl:apply-templates select="tagAttr/li/navTag" mode="writeLi">
			<xsl:with-param name="context" select="$context"></xsl:with-param>
			<xsl:with-param name="menutagname" select="./@tagname"></xsl:with-param>
		</xsl:apply-templates>
	</div>
</xsl:template>

<xsl:template match="lang" mode="child">
	<xsl:param name="currentLang"></xsl:param>
	<xsl:param name="texttype"></xsl:param>
	<xsl:param name="tagname"></xsl:param>
	<xsl:param name="attrs"></xsl:param>
	<li class="sc-adm-lang-item">
		<xsl:element name="form">
			<xsl:apply-templates select="$attrs">
			</xsl:apply-templates>
			<xsl:choose>
				<xsl:when test="string-length($texttype) &gt; 0">
					<xsl:element name="{$texttype}">
						<xsl:attribute name="class">
							<xsl:value-of select="'sc-adm-lang-item-e'"></xsl:value-of>
						</xsl:attribute>
						<xsl:element name="input">
							<xsl:attribute name="type">
								<xsl:value-of select="'hidden'"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="name">
								<xsl:value-of select="'langchange'"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="value">
								<xsl:value-of select="(.)"></xsl:value-of>
							</xsl:attribute>
						</xsl:element>
						<xsl:element name="input">
							<xsl:attribute name="style">
								<xsl:value-of select="concat('background:url(','/images/lang/',(.),'.png',') no-repeat',';background-size:contain;width:100%;height:30px;float:left;')" />
							</xsl:attribute>
							<xsl:attribute name="type">
								<xsl:value-of select="'submit'"></xsl:value-of>
							</xsl:attribute>
							<xsl:attribute name="value">
								<xsl:value-of select="''"></xsl:value-of>
							</xsl:attribute>
						</xsl:element>	
					</xsl:element>
				</xsl:when>
			</xsl:choose>
		</xsl:element>
	</li>
</xsl:template>
</xsl:stylesheet>