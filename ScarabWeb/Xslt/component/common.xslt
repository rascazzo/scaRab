<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">


<xsl:template match="header">
	<header class="er-main">
		<a href="/" rel="canonical">
			<img src="/images/er_web.jpg" alt="ER" />			
		</a>
		<h1 class="er-site-title">
			<a href="/">emiliorascazzo</a>
		</h1>
		<section id="er-gen-social">
			<div class="er-gen-soc-wrap er-mob">
				<ul>
					<!-- <li>
						<a href="https://www.facebook.com/si.raccontache"><div class="er-icon er-fb-gen"></div></a>
					</li>
					<li>
						<a href="https://twitter.com/Super3milio"><div class="er-icon er-tw-gen"></div></a>
					</li>-->
					<li>
						<a href="https://www.linkedin.com/pub/emilio-rascazzo/3b/ab9/b99"><div class="er-icon er-lin-gen"></div></a>
					</li>
				</ul>
			</div>
		</section>
		<div class="er-wrap er-main-menu er-no">
			<nav class="er-hd-main er-main-menu er-no">
				<ul class="er-main-menu-list">
					<xsl:apply-templates select="//sidebar/first[attributes[name='type' and value = 'staticlink']]" mode="main-nav">
						<xsl:sort select="@id" data-type="number" />
					</xsl:apply-templates>
				</ul>
			</nav>	
		</div>
		<div class="er-hum-min er-pointer"></div>
	</header>
</xsl:template>

<xsl:template name="footer">
	<footer class="site">
		<div class="er-footer content-site center">
			<div class="er-content-footer-wrap">
					<div>
						<span><b>Rascazzo Emilio</b> 2014</span>
					</div>
					<div>
						<span>313, c.so Peschiera - 10141 Torino</span>
					</div>
					<div>	
						<span>c.f. rscmle77a27l129d | P.iva 10946630018</span>
					</div>
					<div>	
						<span>Tutti i diritti riservati</span>
					</div>
				</div>
		</div>	 
	</footer>
</xsl:template>


<xsl:template name="login">
<xsl:param name="new"></xsl:param>
	<aside class="er-sec-login">
		<xsl:choose>
			<xsl:when test="$new = 'true'">
				<form action="/{//adminlayer/userapppath}/login" method="post" id="idloginmainform">
					<fieldset class="er-ft-left">
						<table>
							<thead>
								<tr>
									<th>
										<legend>Entra in ER</legend>
									</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
										<label>Username</label>
									</td>
								</tr>
								<tr>
									<td>		
										<input type="text" name="loginusername" />
									</td>
								</tr>
								<tr>
									<td>
										<label>Password</label>
									</td>
								</tr>
								<tr>
									<td>		
										<input type="password" name="loginpassword" />
									</td>
								</tr>				
							</tbody>
						</table>
					</fieldset>
					<fieldset class="er-ft-right">
						<input type="submit" value="Entra" />
						<a href="/er/content/custom/registration">New User</a>
					</fieldset>
				</form>
			</xsl:when>
			<xsl:when test="$new = 'false'">		
				<form action="/{//adminlayer/userapppath}/logout" method="post" id="idlogoutmainform">
					<input type="submit" value="Esci" />
				</form>
			</xsl:when>
		</xsl:choose>		
		<div class="er-user-message">
		
		</div>
	</aside>
</xsl:template>


<xsl:template match="first" mode="main-nav">
	<li class="er-main-menu-item">
		<div class="er-item-wrap">	
			<a href="{href}">
				<xsl:if test="string-length(attributes[name= 'icon']/value) &gt; 0">
					<div class="{attributes[name= 'icon']/value} er-ft-left">
					</div>
				</xsl:if>
				<span class="er-title er-ft-left">
					<xsl:value-of select="text"/>
				</span>
			</a>
		</div>	
	</li>
</xsl:template>

</xsl:stylesheet>