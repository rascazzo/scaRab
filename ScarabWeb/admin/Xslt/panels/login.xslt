<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="panels[../@outContext = 'loginAdmin']">
	<div class="sc-adm-mn-lgn">
			<form action="/{//adminlayer/userapppath}/login" method="POST" id="loginAdminForm" class="sc-main-panel">
				<div class="ad-er-two-content">
					<fieldset>
						<ul>
							<li class="ad-er-input-text">
							    <div class="input-text-wrap input-text-wrap-cl ">
									<div class="label">
										<label for="usernameId">Username</label>
									</div>
									<div class="input sc-input">
										<input type="text" id="usernameId" name="loginusername"/>
									</div>
								</div>
							</li>
							<li class="ad-er-input-text input-text-wrap-cl ">
								<div class="input-text-wrap">
									<div class="label">
										<label for="passwordId">Password</label>
									</div>
									<div class="input sc-input">
										<input type="password" id="passwordId" name="loginpassword" />
									</div>
								</div>
							</li>
							
						</ul>
					</fieldset>
				</div>
				<div>
					
					<input type="submit" value="Login" id="initLogin"/>
				</div>
				<div class="ad-er-two-content" >	
					<input type="reset" value="Reset" />
				</div>	
				<div class="ad-er-two-content">
					<span>
						<div class="er-user-message">
		
						</div>
					</span>
				</div>
			</form>
	</div>		
</xsl:template>


</xsl:stylesheet>