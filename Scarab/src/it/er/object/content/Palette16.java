package it.er.object.content;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "palette")
public class Palette16 extends ScSvgWrap{
	
	private static final int bits = 8;
	
	private static final int bitBase = 3;
	
	private static final String[] hexColorBit = {"0",
		"1",
		"2",
		"3",
		"4",
		"5",
		"6",
		"7",
		"8",
		"9",
		"A",
		"B",
		"C",
		"D",
		"E",
		"F"};
	
	public Palette16(){
		
	}
	
	public static int getBits() {
		return bits;
	}

	public static int getBitbase() {
		return bitBase;
	}

	@Override
	@XmlElement(name = "rect")
	public PaletteBox getBox() {
		return box;
	}

	public static void makePalette(List<ScSvgWrap> l){
		if (l != null){
			for (int a = 0;a < Palette16.hexColorBit.length; a ++){
				for (int i = 0;i < Palette16.hexColorBit.length; i ++){
					for (int j = 0; j < Palette16.hexColorBit.length; j ++){
						PaletteBox s = new PaletteBox();
						s.setFill("#"+String.format("%s%s%s",String.valueOf(Palette16.hexColorBit[a]),
								String.valueOf(Palette16.hexColorBit[i]) ,
								String.valueOf(Palette16.hexColorBit[j])));
						ScSvgWrap w = new ScSvgWrap();
						w.setBox(s);
						l.add(w);
					}
				}
			} 
			
		} 
		
	}
	
	public static void makePalette16(List<ScSvgWrap> l){
		if (l != null){
			for (int a = 0;a < Palette16.hexColorBit.length; a ++){
					for (int i = 0;i < Palette16.hexColorBit.length; i ++){
						for (int j = 0; j < Palette16.hexColorBit.length; j ++){
							PaletteBox s = new PaletteBox();
							s.setFill("#"+String.format("%s%s%s%s%s%s",String.valueOf(Palette16.hexColorBit[a]),
									String.valueOf(Palette16.hexColorBit[j]) ,
									String.valueOf(Palette16.hexColorBit[i]),
									String.valueOf(Palette16.hexColorBit[j]),
									String.valueOf(Palette16.hexColorBit[i]),
									String.valueOf(Palette16.hexColorBit[j])
									));
							ScSvgWrap w = new ScSvgWrap();
							w.setBox(s);
							l.add(w);
						}
					}
				
			} 
			
		} 
		
	}
	
	public static void makePaletteMono(List<ScSvgWrap> l){
		if (l != null){ 
			for (int i = 0;i < Palette16.hexColorBit.length; i ++){
				for (int j = 0; j < Palette16.hexColorBit.length; j ++){
					PaletteBox s = new PaletteBox();
					s.setFill("#"+String.format("%s%s%s%s%s%s",
							String.valueOf(Palette16.hexColorBit[i]),
							String.valueOf(Palette16.hexColorBit[j]),
							String.valueOf(Palette16.hexColorBit[i]),
							String.valueOf(Palette16.hexColorBit[j]),
							String.valueOf(Palette16.hexColorBit[i]),
							String.valueOf(Palette16.hexColorBit[j])
							));	
					ScSvgWrap w = new ScSvgWrap();
					w.setBox(s);
					l.add(w);
				}
			}
		}
	}
}
