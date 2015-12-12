<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:import href="/admin/Xslt/panels/common/commFormComp.xslt"/>


<xsl:template match="panels[../@outContext = 'createsidebar']" >

	<xsl:apply-templates select="panel[@namePanel = 'main']" mode="maincreatesidebar">
	
	</xsl:apply-templates>
</xsl:template>



<xsl:template match="panel" mode="maincreatesidebar">
	<div class="panel panel-default">
	  <div class="panel-heading">
	  	<span class="sc-main-panel-title"><xsl:value-of select="./labels/label[name = 'sidebar']/value"/></span>
	  </div>
	  <div class="panel-body">
		<div class="sc-main-panel manage-sidebar">
			<form method="post" id="reloadsidebarTNForm" class="sc-main-panel" action="{concat('/rest','/sidebarws','/reloadsb')}">
				<div class="manage-sidebar">
					<button type="button" class="btn btn-default glyphicon glyphicon-refresh" onclick="t.reload();">
						
					</button>
					<button type="submit" class="btn btn-default glyphicon glyphicon-road">
						
					</button>
				</div>
			</form>
		</div>
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
	  	<xsl:value-of select="./labels/label[name = 'sidebartagname']/value"/>
	  </div>
	  	<div class="panel-body">
			<form method="post" id="createsidebarTNForm" class="sc-main-panel" action="{concat('/rest','/sidebarws','/inserttgn')}">
			<div class="input-text-wrap-cl">	
				<fieldset>
					<div class="input-text-wrap-cl tagname">
							<label for="tagnameId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'sidebartagname']/value"/>
							</label>
							<input type="text" id="tagnameId" name="tagname" class="sc-input" required="true"/>
					</div>
					<!-- 
					<div class="input-slt-fl-r-wrap-cnt">
						<div class="sc-ol-label-fl">
							<label for="siteId" class="sc-label-fl">
								<xsl:value-of select="./labels/label[name = 'site']/value"/>
							</label>
						</div>
						<div class="input-select-fl-r-wrap">
								
								<select required="true" id="siteId" name="sitename" class="sc-input-no-fl" onchange="fesidebar.allroottagsbysite(this,'{concat('/rest','/sidebarws','/allrtagsbysite')}')" >
									<xsl:apply-templates select="./sites/site" mode="standardSelectItem"/>
								</select>
						</div>
					</div>
					 -->
					<input type="hidden" required="true" id="siteId" name="sitename" />
					<div class="input-text-wrap-cl tagvalue">
							<label for="tagvalueId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'sidebartagvalue']/value"/>
							</label>
							<input required="true" type="text" id="tagvalueId" name="tagvalue" class="sc-input" />
					</div>
					<div class="input-text-wrap-cl numorder">
							<label for="tagnumorderId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'numorder']/value"/>
							</label>
							<input required="true" type="text" id="tagnumorderId" name="tagnumorder" class="sc-input" value="{count(./fathers/father) +1}"/>
					</div>
				</fieldset>
			</div>
			<div class="input-text-wrap-cl">	
				<fieldset>
					<div class="input-slt-fl-r-wrap-cnt tagnamerel">
						<div class="input-rdo-wrap-cl">
							<label for="tagnamerelId" class="sc-label-fl">
								<xsl:value-of select="./labels/label[name = 'firstlevel']/value"/>
							</label>
						</div>
						<div class="input-rdo-wrap-cl">
								
								<select id="tagnamerelId" name="tagnamerel" class="sc-input-no-fl" onchange="fesidebar.alltagSonbysite(this,'{concat('/rest','/sidebarws','/alltagsonbysite')}')" >>
									<xsl:apply-templates select="./fathers/father" mode="standardSelectItem"/>
								</select>
						</div>
					</div>
					<div class="input-rdo-wrap-cl tgtype">
							<div>
								<input type="radio" id="tgtypeIdFirst" name="tgtypeFirst" onchange="fesidebar.hideTagRel(this)"
									onclick="document.getElementById('tgtypeIdSecond').checked = false;document.getElementById('tgtypeIdGrp').checked = false;document.getElementById('tgtypeIdSecond').value = 'off';document.getElementById('tgtypeIdGrp').value = 'off';document.getElementById('tgtypeIdFirst').value = 'on';"></input>
								<label for="tgtypeIdFirst" class="sc-label">
									<xsl:value-of select="./labels/label[name = 'firstlevel']/value"/>
								</label>
							</div>
							<div>
								<input type="radio" id="tgtypeIdSecond" name="tgtypeSecond" onchange="fesidebar.showTagRel(this)" 
									onclick="document.getElementById('tgtypeIdGrp').checked = false;document.getElementById('tgtypeIdFirst').checked = false;document.getElementById('tgtypeIdGrp').value = 'off';document.getElementById('tgtypeIdFirst').value = 'off';document.getElementById('tgtypeIdSecond').value = 'on';"></input>
								<label for="tgtypeIdSecond" class="sc-label">
									<xsl:value-of select="./labels/label[name = 'secondlevel']/value"/>
								</label>
							</div>
							<div>
								<input type="radio" id="tgtypeIdGrp" name="tgtypeGrp" onchange="fesidebar.hideTagRel(this)"
									onclick="document.getElementById('tgtypeIdSecond').checked = false;document.getElementById('tgtypeIdFirst').checked = false;document.getElementById('tgtypeIdSecond').value = 'off';document.getElementById('tgtypeIdFirst').value = 'off';document.getElementById('tgtypeIdGrp').value = 'on';"></input>
								<label for="tgtypeIdGrp" class="sc-label">
									<xsl:value-of select="./labels/label[name = 'grouped']/value"/>
								</label>
							</div>
					</div>
						<div class="input-butt-wrap-cl addtgn">
							<button type="submit" class="btn btn-default">
								<xsl:value-of select="./labels/label[name = 'addTgn']/value"/>
							</button>	
						</div>
				</fieldset>
			</div>	
			</form>
		</div>
	</div>
	<div class="panel panel-default">
	  <div class="panel-heading">
	  	<xsl:value-of select="./labels/label[name = 'attributename']/value"/>
	  </div>
	  	<div class="panel-body">	
			<form method="post" id="createsidebarTNForm" class="sc-main-panel" action="{concat('/rest','/sidebarws','/insertattrs')}">
			<div class="input-text-wrap-cl">
				<fieldset>
				
				</fieldset>
			</div>
			<div class="input-text-wrap-cl">	
				<fieldset>
					<div class="input-text-wrap-cl attrnv">
							<label for="attrnameId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'attributename']/value"/>
							</label>
							<input type="text" id="attrnameId" name="attrname" class="sc-input"/>
							<label for="attrvalueId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'attributevalue']/value"/>
							</label>
							<input type="text" id="attrvalueId" name="attrvalue" class="sc-input"/>
					</div>
						<div class="input-butt-wrap-cl addattr">
							<button type="button" id="addattributeId" onclick="fesidebar.createAttribute(this);" class="btn btn-default">
								<xsl:value-of select="./labels/label[name = 'addattribute']/value"/>
							</button>
						</div>
				</fieldset>
			</div>
			<div class="input-text-wrap-cl">	
				<fieldset>
					<div class="table-wrap-cl">
						<table class="sc-attr-table" id="sd-attr-table">
							<thead>
								<tr>
									<th class="sc-intbl-wdh1">
										<input type="checkbox" name="selallattribute" value="'all'"/>
									</th>
									<th class="sc-intbl-wdh1">Id</th>
									<th class="sc-intbl-wdh3">
										<xsl:value-of select="./labels/label[name = 'attributename']/value"/>
									</th>
									<th class="sc-intbl-wdh3">
										<xsl:value-of select="./labels/label[name = 'attributevalue']/value"/>
									</th>
								</tr>
							</thead>
							<tbody>
								<!-- 
								<xsl:apply-templates select="">
									<xsl:sort></xsl:sort>
								</xsl:apply-templates>
								-->
							</tbody>
						</table>
					</div>
						<div class="input-butt-wrap-cl addattr">
							<button type="button" name="modattribute" class="btn btn-default">
								<xsl:value-of select="./labels/label[name = 'modattribute']/value"/>
							</button>
						</div>
						<div class="input-butt-wrap-cl addattr">
							<button type="button" name="addattribute" class="btn btn-default">
								<xsl:value-of select="./labels/label[name = 'deleteattribute']/value"/>
							</button>
						</div>
						<!-- 
						<div class="input-butt-wrap-cl save">
							<input type="button" name="savetagname" value="{./labels/label[name = 'savetag']/value}"/>
						</div>
						<div class="input-butt-wrap-cl sbm">
							<input type="submit" value="{./labels/label[name = 'saveall']/value}"  />
						</div>-->	
				</fieldset>
			</div>	
			</form>
		</div>
	</div>		
	
	<script type="text/javascript">
		<![CDATA[
		$(document).ready(function(){
			t.init();
			scarabfe.loadjEle('createsidebarTNForm');
			scarabfe.loadjEle('reloadsidebarTNForm');
		});
	]]>
	</script>
</xsl:template>

<!-- 
<xsl:template match="" mode="alltgAttributes">
	<tr>
		<td>
			<input type="checkbox" name="{concat('selattribute_',position()}"/>
		</td>
		<td>
			1
		</td>
		<td>
			name 1
		</td>
		<td>
			value 1
		</td>
	</tr>
</xsl:template>
 -->
</xsl:stylesheet>