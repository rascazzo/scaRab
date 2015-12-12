<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="*" mode="metainfo">
		<fieldset>
						<div class="input-text-wrap-cl authorinfo">
							<label for="authorinfoId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'author']/value"/>
							</label>
							<input type="text" id="authorinfoId" name="authorinfo" class="sc-input" />
						</div>
						<div class="input-text-wrap-cl titleinfo">
							<label for="titleinfoId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'title']/value"/>
							</label>
							<input type="text" id="titleinfoId" name="titleinfo" class="sc-input" />
						</div>
						<div class="input-text-wrap-cl imageinfo">
							<label for="imageinfoId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'image']/value"/>
							</label>
							<input type="text" id="imageinfoId" name="imageinfo" class="sc-input" />
						</div>
						<div class="input-text-wrap-cl keysinfo">
							<label for="keysinfoId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'keys']/value"/>
							</label>
							<input type="text" id="keysinfoId" name="keysinfo" class="sc-input" />
						</div>
						<div class="input-text-wrap-cl descriptioninfo">
							<div class="panel panel-default">
								<div class="panel-heading">
									<xsl:value-of select="./labels/label[name = 'description']/value"/>	
								</div>
								<div class="panel-body">
									<div class="input-text-wrap-cl descriptioninfo">
										<textarea name="descriptioninfo" rows="5" cols="10"/>
									</div>
								</div>
							</div>
						</div>
					</fieldset>
</xsl:template>

<xsl:template match="*" mode="seriesmetainfo">
		<fieldset>
						<div class="input-text-wrap-cl authorinfo">
							<label for="authorinfoId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'author']/value"/>
							</label>
							<input type="text" id="authorinfoId" name="authorinfo" class="sc-input" />
						</div>
						<div class="input-text-wrap-cl titleinfo">
							<label for="titleinfoId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'title']/value"/>
							</label>
							<input type="text" id="titleinfoId" name="titleinfo" class="sc-input" />
						</div>
						<div class="input-text-wrap-cl imageinfo">
							<label for="imageinfoId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'image']/value"/>
							</label>
							<input type="text" id="imageinfoId" name="imageinfo" class="sc-input" />
						</div>
						<div class="input-text-wrap-cl keysinfo">
							<label for="keysinfoId" class="sc-label">
								<xsl:value-of select="./labels/label[name = 'keys']/value"/>
							</label>
							<input type="text" id="keysinfoId" name="keysinfo" class="sc-input" />
						</div>
						<div class="input-text-wrap-cl descriptioninfo">
							<div class="panel panel-default">
								<div class="panel-heading">
									<xsl:value-of select="./labels/label[name = 'description']/value"/>	
								</div>
								<div class="panel-body">
									<div class="input-text-wrap-cl descriptioninfo">
										<textarea name="descriptioninfo" rows="5" cols="10"/>
									</div>
								</div>
							</div>
						</div>
					</fieldset>
</xsl:template>
</xsl:stylesheet>				