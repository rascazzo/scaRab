package it.er.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageFile {
	private BufferedImage image;
	private String outFile, inFile, outPath;
	private int active;
	
	public ImageFile() {
		active = 0;
	}
	
	public int getState(){
		return active;
	}
	
	public void setFile(String path, String out, String outPath){
		active = 1;
		this.outPath = outPath;
		try {
			image = ImageIO.read(new File(path+"/"+out));
		} catch (Exception ex) {ex.printStackTrace();}
		outFile = out;
		inFile = out;
	}
	
	public void setFile(String path, String out, String outPath, String outFile){
		active = 1;
		this.outPath = outPath;
		try {
			image = ImageIO.read(new File(path+"/"+out));
		} catch (Exception ex) {ex.printStackTrace();}
		this.outFile = outFile;
		inFile = out;
	}
	
	public void setOut (String out){
		this.outFile = out;
		if (getExtension(out) == null)
		outFile = outFile + "." + getExtension(inFile);
	}
	
	public int getWidth(){
		return image.getWidth();
	}
	
	public int getHeight(){
		return image.getHeight();
	}
	
	public static String getExtension(String s) {
		String ext = null;
		int i = s.lastIndexOf('.');
		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i+1).toLowerCase();
		}
		return ext;
	}
	
	public void resize (int w, int h){
		double scale;
		if (h==0)
			scale = ((double)w / (double)image.getWidth());
		else scale = ((double)h / (double)image.getHeight());
		AffineTransformOp op = new
		AffineTransformOp(AffineTransform.getScaleInstance(scale, scale),
		AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		image = op.filter(image,null);
		String outP = this.outPath+"/"+outFile;
		try {
			ImageIO.write(image, getExtension(inFile), new File(outP));
		}
		catch (Exception ex) {}
		}

}