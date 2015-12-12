<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:import href="/admin/Xslt/panels/common/metainfoform.xslt"/>

<xsl:template match="panels[../@outContext = 'newtext']" >
	<xsl:apply-templates select="panel[@namePanel = 'main']" mode="mainnewtext">
	
	</xsl:apply-templates>
</xsl:template>	


<xsl:template match="panel" mode="mainnewtext">
	<div class="panel panel-default">
		<div class="panel-heading">
			<span class="sc-main-panel-title"><xsl:value-of select="./labels/label[name = 'sidebar']/value"/></span>
		</div>
		<div class="panel-body">
			<div name="tree" class="sc-tree" data-type="{//adminlayer/restfullpath}">
				<div class="sc-tree-lt">
					<div class="sc-lf-tree" id="lf-tagnamesys-box">
						
					</div>
				</div>
				<div id="t">
					
				</div>
				<div class="sc-tree-rt">
					
					
				</div>
			
			</div>
		</div>
	</div>		
		<div class="panel panel-default">
			<div class="panel-heading">
				<xsl:value-of select="./labels/label[name = 'newtext']/value"/>	
			</div>
			<div class="panel-body">	
			<form method="post" id="inserttextForm" class="sc-main-panel" action="{concat('/rest','/text','/inserttext')}">
			<div class="input-text-wrap-cl">
				<fieldset>
					<div class="input-text-wrap-cl tagname">
							<label for="tagnameId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'sidebartagname']/value"/>
							</label>
							<input type="text" id="tagnameId" name="tagname" class="sc-input" />
					</div>
					<!-- 
					<div class="input-slt-fl-r-wrap-cnt">
						<div class="sc-ol-label-fl">
							<label for="siteId" class="sc-label-fl">
								<xsl:value-of select="./labels/label[name = 'site']/value"/>
							</label>
						</div>
						<div class="input-select-fl-r-wrap">
								
								<select id="siteId" name="sitename" class="sc-input-no-fl"  >
									<xsl:apply-templates select="./sites/site" mode="standardSelectItem"/>
								</select>
						</div>
					</div>
					 -->
					 <input type="hidden" required="true" id="siteId" name="sitename" />
				</fieldset>
			</div>	
				<div class="input-text-wrap-cl">	
					<fieldset>
					<div class="input-text-wrap-cl title">
							<label for="titleId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'title']/value"/>
							</label>
							<input type="text" id="titleId" name="title" class="sc-input" required="true"/>
					</div>
					<div class="input-text-wrap-cl torder">
								<label for="orderId" class="sc-label">
									<xsl:value-of select="./labels/label[name = 'numorder']/value"/>
								</label>
							<input type="text" id="orderId" name="order" class="sc-input" required="true"/>
					</div>
					<div class="input-text-wrap-cl archive">
							<label for="archiveId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'archive']/value"/>
							</label>
							<input type="checkbox" id="archiveId" name="archive" class="sc-input" />
					</div>
					<div class="input-text-wrap-cl">
						<div class="sc-ol-label-fl">
							<label for="langId" class="sc-label-fl">
								<xsl:value-of select="./labels/label[name = 'language']/value"/>
							</label>
						</div>
						<div class="">
								
								<select id="langId" name="lang" class="sc-input-no-fl" required="true">
									<xsl:apply-templates select="./langs/lang" mode="standardSelectItem"/>
								</select>
						</div>
					</div>
					</fieldset>
				</div>
				<div class="input-text-wrap-cl">
					<fieldset>
					<div class="input-text-wrap-cl">
						<div class="panel panel-default">
								<div class="panel-heading"></div>
								<div class="panel-body">
									<div class="input-text-wrap-cl body">
										<textarea name="body" rows="20" cols="50" required="true"/>
									</div>
								</div>
						</div>			
					</div>
					<div class="input-text-wrap-cl">
						<div class="input-butt-wrap-cl addtext">
							<button type="submit" class="btn btn-default">
								<xsl:value-of select="./labels/label[name = 'addtext']/value"/>
							</button>
						</div>
					</div>
					</fieldset>
				</div>
				<div class="input-text-wrap-cl">
					<xsl:apply-templates select="(.)" mode="metainfo"/>
				</div>		
			</form>
		</div>
	</div>	
	<script type="text/javascript">
		<![CDATA[
		$(document).ready(function(){
			t.init();
			scarabfe.loadjEle('inserttextForm');
		});	
		]]>
	</script>
</xsl:template>

</xsl:stylesheet>