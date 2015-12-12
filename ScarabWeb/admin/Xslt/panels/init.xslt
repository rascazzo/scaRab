<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="panels[../@outContext = 'init']" >

<form action="{concat('/',//adminlayer/baseapppath,'/initsite')}" method="POST">
				<div class="ad-er-two-content">
					<fieldset>
						<ul>
							<li class="ad-er-input-text">
							    <div class="input-text-wrap">
									<div class="label">
										<label for="usernameId">Username</label>
									</div>
									<div class="input">
										<input type="text" id="usernameId" name="username"/>
									</div>
								</div>
							</li>
							<li class="ad-er-input-text">
								<div class="input-text-wrap">
									<div class="label">
										<label for="passwordId">Password</label>
									</div>
									<div class="input">
										<input type="password" id="passwordId" name="password" />
									</div>
								</div>
							</li>
							<li class="ad-er-input-text">
							    <div class="input-text-wrap">
									<div class="label">
										<label for="confirmpasswordId">Confirm Password</label>
									</div>
									<div class="input">
										<input type="password" id="confirmpasswordId" name="confirmpassword" />
									</div>
								</div>
							</li>
							<li class="ad-er-input-text">
								<div class="input-text-wrap">
									<div class="label">
									<label for="emailId">Email</label>
									</div>
									<div class="input">
										<input type="text" id="emailId" name="email"/>
									</div>
								</div>
							</li>
						</ul>
					</fieldset>
				</div>
				<div class="ad-er-two-content">
					<fieldset>
						<ul>
							<li class="ad-er-input-text">
							    <div class="input-text-wrap">
									<div class="label">
										<label for="titleId">Title</label>
									</div>
									<div class="input">
										<input type="text" id="titleId" name="title"/>
									</div>
								</div>
							</li>
							<li class="ad-er-input-text">
							    <div class="input-text-wrap">
									<div class="label">
										<label for="subtitleId">Subtitle</label>
									</div>
									<div class="input">
										<input type="text" id="subtitleId" name="subtitle"/>
									</div>
								</div>
							</li>
							<li class="ad-er-input-text">
							    <div class="input-text-wrap">
									<div class="label">
										<label for="domainId">Domain</label>
									</div>
									<div class="input">
										<input type="text" id="domainId" name="domain"/>
									</div>
								</div>
							</li>
							<li class="ad-er-input-text">
							    <div class="input-text-wrap"> 
									<div class="label">
										<label for="xsltpathId">Xslt Folder</label>
									</div>
									<div class="input">
										<input type="text" id="xsltpathId" name="xsltpath"/>
									</div>
								</div>
							</li>
							<li class="ad-er-input-text">
								<div class="input-text-wrap"> 
									<div class="label">
										<label for="adminxsltpathId">Admin Xslt Folder</label>
									</div>
									<div class="input">
										<input type="text" id="adminxsltpathId" name="adminxsltpath"/>
									</div>	
								</div>
							</li>
							<li class="ad-er-input-text">
							    <div class="input-text-wrap">
									<div class="label">
										<label for="webcontentpathId">Main WebContent Folder</label>
									</div>
									<div class="input">
										<input type="text" id="webcontentpathId" name="webcontentpath"/>
									</div>
								</div>
							</li>
							<li class="ad-er-input-text">
								<div class="input-text-wrap">
									<div class="label">
										<label for="mainlangId">Main Lang</label>
									</div>
									<div class="input">
										<input type="text" id="mainlangId" name="mainlang"/>
									</div>
								</div>
							</li>
							<li class="ad-er-input-text">
								<div class="input-text-wrap">
									<div class="label">
										<label for="mixedlangId">Mixed Lang</label>
									</div>
									<div class="input">
										<input type="checkbox" id="mixedlangId" name="mixedlang"/>
									</div>
								</div>
							</li>
						</ul>
					</fieldset>
					<!-- <input type="submit" value="Save" id="initMainSave" onclick="scarabfe.ajsubmit(this,event);"/>-->
					<input type="submit" value="Save" id="initMainSave" />
					<input type="reset" value="Reset" />
				</div>
			</form>
</xsl:template>

</xsl:stylesheet>