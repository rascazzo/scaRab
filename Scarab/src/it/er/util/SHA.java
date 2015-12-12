package it.er.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {

	public static String getHash1(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException{
				
		byte[] defaultBytes = input.getBytes("UTF-8");
		StringBuffer hexString = new StringBuffer();
		
		MessageDigest algorithm = MessageDigest.getInstance("SHA-1");
	
		algorithm.reset();
		byte[] sha1hash = new byte[40];
		algorithm.update(defaultBytes,0,input.length());
		byte messageDigest[] = algorithm.digest();
		
		String hex = null;
		for (int i = 0; i < messageDigest.length; i++){
			int halfbyte = (messageDigest[i] >>> 4) & 0x0F;
			int two_half = 0;
			do {
				if ((0 <= halfbyte) && (halfbyte <=9))
					hexString.append((char) ('0'+halfbyte));
				else
					hexString.append((char) ('a'+(halfbyte - 10)));
				halfbyte = messageDigest[i] & 0x0F;
			} while (two_half++ < 1);
		}
		return hexString.toString();
	}
}
