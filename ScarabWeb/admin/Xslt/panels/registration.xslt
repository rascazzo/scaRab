<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="panels[../@outContext = 'registration']" >

			<form action="/{baseapppath}/newuser" method="POST">
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
										<label for="domainId">Domain</label>
									</div>
									<div class="input">
										<input type="text" id="domainId" name="domain"/>
									</div>
								</div>
							</li>
							<li>
								  <div class="input-text-wrap">
									<div class="label">
										<label for="privacyId">Privacy</label>
									</div>
									<div class="input">
										<input type="checkbox" id="privacyId" name="privacyconsent"/>
									</div>
								</div>
							</li>
						</ul>
					</fieldset>
					<input type="submit" value="Save" id="initSave" />
					<input type="reset" value="Reset" />
				</div>
			</form>
</xsl:template>

</xsl:stylesheet>