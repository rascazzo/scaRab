package it.er.util;

public class MR1 {

	
	public static String digest(String in){
		String ret = "";
		int c = 0;
		int tmp = 0;
		while (c<in.length()){
	        char a = in.charAt(c);
			tmp = (int) a;
			if (c%2==0){
				tmp -= 3;
				ret += Integer.toHexString(tmp);
			} else {
				tmp += 2;
				ret += Integer.toHexString(tmp);
			}
			c++;
		}
		return ret;
	}
}
